package industries.immington.spellcraft.item;

import industries.immington.spellcraft.Spellcraft;
import industries.immington.spellcraft.spell.ISpellItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * Base class for rods extends from sword.
 *
 * Keeps the sword's ability to break cobwebs.
 */
public class ScepterItem extends SwordItem implements ISpellItem {

    private static final Logger LOGGER = LogManager.getLogger(Spellcraft.MOD_ID);

    public ScepterItem(IItemTier tier, int attackDamage, float attackSpeed, Item.Properties propertiesBuilder) {
        super(tier, attackDamage, attackSpeed, propertiesBuilder);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        CompoundNBT spellTag = stack.getChildTag("spellcraft:casting");
        if (spellTag != null && !spellTag.isEmpty()) {
            return spellTag.getInt("max_damage");
        }
        return 0;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
        /* should check if it has a spellcraft:casting tag */
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 256;
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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack thisItem = player.getHeldItem(hand);
        ItemStack offhandItem = hand == Hand.MAIN_HAND ? player.getHeldItemOffhand() : player.getHeldItemMainhand();

        if (world.isRemote) {
            world.addParticle(ParticleTypes.FLASH, player.posX, player.posY + player.getEyeHeight(), player.posZ, 0, 0, 0);
        }
        else {
            LOGGER.info("Begin casting with {}", thisItem);
        }

        player.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, thisItem);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        LOGGER.info("Item use first with: {}", stack);
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (count % 10 == 0) {
            if (player.world.isRemote()) {
                player.world.addParticle(ParticleTypes.EFFECT, player.posX, player.posY + player.getEyeHeight(), player.posZ, 0, 0, 0);
            }
            else {
                LOGGER.info("- casting tick {} ({})", count / 10, stack);
            }
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entity, int timeLeft) {
        if (!world.isRemote) {
            LOGGER.info("Stopped using, {} left", timeLeft);
        }
        else {
            world.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, 0, 0 ,0);
        }
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World worldIn, @Nonnull LivingEntity entityLiving) {
        if (!worldIn.isRemote) {
            LOGGER.info("Item use finished");
        }
        else {
            worldIn.addParticle(ParticleTypes.ANGRY_VILLAGER, entityLiving.posX, entityLiving.posY + entityLiving.getEyeHeight(), entityLiving.posZ, 0, 0,0);
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}