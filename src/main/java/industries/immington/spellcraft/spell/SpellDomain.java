package industries.immington.spellcraft.spell;

import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 *
 */
public final class SpellDomain extends ForgeRegistryEntry<SpellDomain> {
    public static final SpellDomain REDSTONE = new SpellDomain("spellcraft:redstone");
    public static final SpellDomain UNDEAD = new SpellDomain("spellcraft:redstone");
    public static final SpellDomain LIFE = new SpellDomain("spellcraft:life");
    public static final SpellDomain ILLAGER = new SpellDomain("spellcraft:illager");
    public static final SpellDomain OCEAN = new SpellDomain("spellcraft:ocean");
    public static final SpellDomain NETHER = new SpellDomain("spellcraft:nether");
    public static final SpellDomain ENDER = new SpellDomain("spellcraft:ender");

    public SpellDomain(String registryName) {
        this.setRegistryName(registryName);
    }
}
