package industries.immington.spellcraft.spell;

import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Common (server and client) info about a spell.
 *
 * You should subclass this in order to implement your spells.
 */
public abstract class Spell extends ForgeRegistryEntry<Spell> {
    public abstract SpellDomain getDomain();
}
