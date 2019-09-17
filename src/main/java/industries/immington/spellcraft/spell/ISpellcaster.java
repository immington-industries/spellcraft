package industries.immington.spellcraft.spell;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Represents a player, entity, or block which is able to cast spells.
 */
public interface ISpellcaster {
    /**
     * Whether this caster is alive.
     * @return True for i.e. players and (living) entities
     */
    boolean isAlive();
    /**
     * Whether this caster is a player.
     * @return True for PlayerSpellcaster.
     */
    boolean isPlayer();
    /**
     * Whether this caster is a block.
     * @return True for blocks, i.e. totems.
     */
    boolean isBlock();

    /**
     * Get a BlockPos for this caster.
     * @return The position of the caster
     */
    BlockPos getBlockPos();

    Vec3d getEffectPos();

    /**
     * Whether this caster can use the given targeting method.
     * @param targeting Targeting system of the given spell.
     * @return Whether this caster can target a spell with the given targeting.
     */
    boolean canUseTargeting(SpellTargeting targeting);

    /**
     * Gets the focus item being used by the spellcaster.
     * - For player, optional offhand item
     * @return
     */
    ItemStack getFocusItem();
}
