package industries.immington.spellcraft.item;

import industries.immington.spellcraft.Spellcraft;
import industries.immington.spellcraft.Spells;
import industries.immington.spellcraft.spell.PlayerSpellcaster;
import industries.immington.spellcraft.spell.Spell;
import industries.immington.spellcraft.spell.SpellDomain;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Base class for rods extends from sword.
 *
 * Keeps the sword's ability to break cobwebs.
 */
public class ScepterItem extends SwordItem {
    private static final Logger LOGGER = LogManager.getLogger(Spellcraft.MOD_ID);

    private static final String CASTING_TAG = "spellcraft:Casting";

    private final SpellDomain spellDomain;

    public ScepterItem(IItemTier tier, int attackDamage, float attackSpeed, @Nonnull SpellDomain domain, Item.Properties propertiesBuilder) {
        super(tier, attackDamage, attackSpeed, propertiesBuilder);
        this.addPropertyOverride(new ResourceLocation("spellcraft:cast_time"), (stack, world, entity) -> {
            // get stack's casting info
            // calculate potency range
            // negative for chargeup
            return 0F;
        });
        this.spellDomain = domain;
        this.addPropertyOverride(new ResourceLocation("spellcraft:is_casting"),
                (stack, world, entity) ->
                        entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1F : 0F);
    }

    public Optional<SpellDomain> getDomainFocus() {
        return Optional.of(this.spellDomain);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag tooltipFlag) {
        super.addInformation(stack, world, tooltip, tooltipFlag);
        Optional<SpellDomain> domain = this.getDomainFocus();
        if (domain.isPresent()) {
            tooltip.add(new TranslationTextComponent(this.spellDomain.getRegistryName().toString()));
        }
    }

    public static boolean hasSpell(ItemStack stack) {
        CompoundNBT spellTag = stack.getChildTag(CASTING_TAG);
            return spellTag != null &&
                    (spellTag.contains("Spell") || spellTag.contains("SelectedSpell"));
    }

    public static Optional<Spell> getSpell(ItemStack stack) {
        CompoundNBT spellTag = stack.getChildTag(CASTING_TAG);
        if (spellTag == null) { return Optional.empty(); }
        if (spellTag.contains("Spell")) {
            String spellId = spellTag.getString("Spell");
            ResourceLocation spellLocation = ResourceLocation.tryCreate(spellId);
            if (spellLocation != null) {
                return Optional.ofNullable(Spells.registry.getValue(spellLocation));
            }
        }
        else if (spellTag.contains("SelectedSpell")) {
            String spellId = spellTag.getString("SelectedSpell");
            ResourceLocation spellLocation = ResourceLocation.tryCreate(spellId);
            if (spellLocation != null) {
                return Optional.ofNullable(Spells.registry.getValue(spellLocation));
            }
        }
        return Optional.empty();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (ScepterItem.hasSpell(stack)) {
            return stack.getChildTag(CASTING_TAG).getInt("MaxCasts");
        }
        return 0;
    }

    /**
     * One of each type of spellcasting item is added to the creative tabs.
     * @param group
     * @param items
     */
    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (group == ItemGroup.TOOLS || group == ItemGroup.SEARCH) {
            items.add(new ItemStack(this));
            for (Spell spell : Spells.registry.getValues()) {
                if (!spell.getDomain().equals(this.spellDomain)) {
                    continue;
                }
                ItemStack scepter = new ItemStack(this);
                CompoundNBT castingTag = scepter.getOrCreateChildTag(CASTING_TAG);
                castingTag.putString("Spell", spell.getRegistryName().toString());
                items.add(scepter);
            }
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return ScepterItem.hasSpell(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
        /*
        CompoundNBT spellTag = stack.getChildTag("spellcraft:casting");
        if (spellTag != null && !spellTag.isEmpty()) {
            String spellName = spellTag.getString("current_spell");
            if (!spellName.isEmpty()) {
                ResourceLocation spellId = ResourceLocation.tryCreate(spellName);
                if (spellId != null) {
                    Spell spell = Spellcraft.Registries.spells.getValue(ResourceLocation.tryCreate(spellName));
                    if (spell != null) {
                        return spell.getMaxCastTime();
                    }
                }
            }
        }
        return super.getUseDuration(stack);
        */
    }

    @Override
    @Nonnull
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack thisItem = player.getHeldItem(hand);
        player.setActiveHand(hand);
        player.setHeldItem(hand, thisItem);

        if (!world.isRemote) {
            LOGGER.info("Server item right click, checking spell info.");
        }
        Optional<Spell> maybeSpell = ScepterItem.getSpell(thisItem);
        if (maybeSpell.isPresent()) {
            if (!world.isRemote) {
                LOGGER.info("Server found spell {}, preparing to cast", maybeSpell.get());
            }
            Spell spell = maybeSpell.get();
            if (!spell.checkCastingBegin(world, new PlayerSpellcaster(player))) {
                if (!world.isRemote) {
                    LOGGER.info("Cannot cast spell {}!", spell.getRegistryName());
                }
                return new ActionResult<>(ActionResultType.FAIL, thisItem);
            }
            if (maybeSpell.get().onWarmupTick(world, new PlayerSpellcaster(player), 0)) {
                return new ActionResult<>(ActionResultType.SUCCESS, thisItem);
            }
            else {
                return new ActionResult<>(ActionResultType.FAIL, thisItem);
            }
        }
        else {
            return new ActionResult<>(ActionResultType.PASS, thisItem);
        }
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        LOGGER.info("Item use first with: {}", stack);
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int remaining) {
        if (!(player instanceof PlayerEntity)) { return; }
        if (remaining % 5 == 0 && !player.getEntityWorld().isRemote) {
            LOGGER.info("Item is ticking");
        }
        Optional<Spell> storedSpell = ScepterItem.getSpell(stack);
        if (storedSpell.isPresent()) {
            Spell spell = storedSpell.get();
            int ticks = this.getUseDuration(stack) - remaining;
            int castingTicks = spell.getChargeTime() - ticks;
            if (castingTicks > 0) {
                if (!player.getEntityWorld().isRemote) {
                    LOGGER.info("Ticking spell during cast {}", spell);
                }
                spell.onWarmupTick(player.getEntityWorld(), new PlayerSpellcaster((PlayerEntity) player), ticks);
            }
            else {
                LOGGER.info("Ticking spell after cast {}", spell);
                spell.onCastTick(player.getEntityWorld(), new PlayerSpellcaster((PlayerEntity) player), -ticks);
            }
        }
    }



    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entity, int timeLeft) {
        if (!world.isRemote) {
            LOGGER.info("Stopped using, {} left", timeLeft);
        }
        if (entity instanceof PlayerEntity) {
            Optional<Spell> storedSpell = ScepterItem.getSpell(stack);
            if (storedSpell.isPresent()) {
                Spell spell = storedSpell.get();
                int ticks = this.getUseDuration(stack) - timeLeft;
                if (!world.isRemote) {
                    LOGGER.info("Stopped casting {}", spell.getRegistryName());
                }
                spell.onCastComplete(entity.getEntityWorld(), new PlayerSpellcaster((PlayerEntity) entity), ticks);
            }
            ((PlayerEntity)entity).getCooldownTracker().setCooldown(stack.getItem(), 15);
        }
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World world, @Nonnull LivingEntity entity) {
        int timeLeft = 0;
        if (!world.isRemote) {
            LOGGER.info("Stopped using, {} left", timeLeft);
        }
        if (entity instanceof PlayerEntity) {
            Optional<Spell> storedSpell = ScepterItem.getSpell(stack);
            if (storedSpell.isPresent()) {
                Spell spell = storedSpell.get();
                int ticks = this.getUseDuration(stack) - timeLeft;
                if (!world.isRemote) {
                    LOGGER.info("Completing the cast of {}", spell.getRegistryName());
                }
                spell.onCastComplete(entity.getEntityWorld(), new PlayerSpellcaster((PlayerEntity) entity), ticks);
            }
            ((PlayerEntity)entity).getCooldownTracker().setCooldown(stack.getItem(), 15);
        }
        return stack;
    }
}