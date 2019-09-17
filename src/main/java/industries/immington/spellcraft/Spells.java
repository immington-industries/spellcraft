package industries.immington.spellcraft;

import industries.immington.spellcraft.spell.Spell;
import industries.immington.spellcraft.spell.redstone.FlingSpell;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Contains a list of Spellcraft Spells
 */
public class Spells {
    public static IForgeRegistry<Spell> registry;

    /* Life */
    /* Undead */
    /* Illager */
    public static final Spell FLING = new FlingSpell();
    /* Redstone */
    /* Ocean */
    /* Nether */
}
