package industries.immington.spellcraft.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class PlayerSpellcaster implements ISpellcaster {
    private PlayerEntity player;

    public PlayerSpellcaster(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    @Override
    public boolean isAlive() { return true; }

    @Override
    public boolean isPlayer() { return true; }

    @Override
    public boolean isBlock() { return false; }

    @Override
    public BlockPos getBlockPos() {
        return new BlockPos(this.player);
    }

    @Override
    public boolean canUseTargeting(SpellTargeting targeting) {
        return true;
    }
}
