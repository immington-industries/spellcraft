package industries.immington.spellcraft.spell.redstone;

import industries.immington.spellcraft.entity.Entities;
import industries.immington.spellcraft.entity.FlungBlockEntity;
import industries.immington.spellcraft.spell.*;
import net.minecraft.block.BlockState;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlingSpell extends Spell {
    public static final int CHARGE_TIME = 60;
    public static final int MAX_POTENCY = 60;

    public FlingSpell() {
        super("spellcraft:fling", SpellDomain.REDSTONE, SpellTargeting.PROJECTILE, CHARGE_TIME, MAX_POTENCY);
    }

    public boolean checkCastingBegin(World world, ISpellcaster caster) {
        if (caster.getFocusItem().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean onWarmupTick(World world, ISpellcaster caster, int tick) {
        if (tick % 10 != 0) { return true; }
        if (world.isRemote) {
            if (caster instanceof PlayerSpellcaster) {
                PlayerSpellcaster playerCaster = (PlayerSpellcaster)caster;
                ClientPlayerEntity player = (ClientPlayerEntity)playerCaster.getPlayer();
                ItemStack focusItem = caster.getFocusItem();
                if (focusItem.isEmpty()) {
                    return false;
                }
                Vec3d faceCentered = player.getLookVec().add(1, 1, 1);
                if (focusItem.getItem() instanceof BlockItem) {
                    BlockItem focusBlock = (BlockItem)focusItem.getItem();
                    BlockState focusBlockState = focusBlock.getBlock().getDefaultState();

                    world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, focusBlockState),
                            faceCentered.x, faceCentered.y, faceCentered.z, 0, 0,0);
                }
                else {
                    world.addParticle(ParticleTypes.EFFECT, faceCentered.x, faceCentered.y, faceCentered.z, 0, 0, 0);
                }
            }
        }
        else {
            LOGGER.info("Tick for {} warmup", this.getRegistryName());
        }
        return true;
    }

    public boolean onCastTick(World world, ISpellcaster caster, int potency) {
        if (potency % 10 != 0) { return true; }
        if (world.isRemote) {
            if (caster instanceof PlayerSpellcaster) {
                PlayerSpellcaster playerCaster = (PlayerSpellcaster)caster;
                ClientPlayerEntity player = (ClientPlayerEntity)playerCaster.getPlayer();
                ItemStack focusItem = caster.getFocusItem();
                if (focusItem.isEmpty()) {
                    return false;
                }
                Vec3d faceCentered = player.getLookVec().add(1, 1, 1);
                if (focusItem.getItem() instanceof BlockItem) {
                    BlockItem focusBlock = (BlockItem)focusItem.getItem();
                    BlockState focusBlockState = focusBlock.getBlock().getDefaultState();

                    world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, focusBlockState),
                            faceCentered.x, faceCentered.y, faceCentered.z, 0, 0,0);
                }
                else {
                    world.addParticle(ParticleTypes.EFFECT, faceCentered.x, faceCentered.y, faceCentered.z, 0, 0, 0);
                }
            }
        }
        else {
            LOGGER.info("Tick for {} cast", this.getRegistryName());
        }
        return true;
    }

    public boolean onCastComplete(World world, ISpellcaster caster, int potency) {
        if (!world.isRemote) {
            LOGGER.info("Casting of {} complete", this.getRegistryName());
        }
        Vec3d effectPos = caster.getEffectPos();
        FlungBlockEntity flungBlock = new FlungBlockEntity(Entities.FLUNG_BLOCK_ENTITY, world);
        float velocity = (float)potency / (float)MAX_POTENCY;
        float inaccuracy = 1F - (0.1F * velocity);
        flungBlock.shoot(effectPos.x, effectPos.y, effectPos.z, velocity, inaccuracy);
        world.addEntity(flungBlock);
        FallingBlockEntity fallingBlock = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);
        fallingBlock.setHurtEntities(true);
        fallingBlock.setOrigin(caster.getBlockPos());
        world.addEntity(fallingBlock);
        AbstractArrowEntity arrow = new ArrowEntity(world, ((PlayerSpellcaster)caster).getPlayer());
        arrow.shoot(effectPos.x, effectPos.y, effectPos.z, velocity, inaccuracy);
        return true;
    }
}
