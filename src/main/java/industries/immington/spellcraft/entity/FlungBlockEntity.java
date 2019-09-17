package industries.immington.spellcraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlungBlockEntity extends FallingBlockEntity implements IProjectile {
    private int potency = 0;
    private static final int MAX_SPELL_DAMAGE = 40;

    public FlungBlockEntity(EntityType<? extends FlungBlockEntity> type, World world) {
        super(type, world);
        this.setHurtEntities(true);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        this.setMotion(new Vec3d(x, y, z).scale(velocity));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Potency", this.potency);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.potency = compound.getInt("Potency");
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {

    }

    @Override
    public void applyEntityCollision(Entity entity) {
        LOGGER.info("Flung block: colliding with {}", entity);
    }
}
