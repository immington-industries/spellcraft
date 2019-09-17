package industries.immington.spellcraft.spell;

import industries.immington.spellcraft.Spellcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Represents a domain from which spells arise.
 *
 * Adding spells: either add new spells to an existing domain, or register a
 * mod-specific domain and add them there.
 */
public final class SpellDomain extends ForgeRegistryEntry<SpellDomain> {
    public static IForgeRegistry<SpellDomain> registry;

    /**
     * Contains spells which are related to redstone effects, transformation, and strongholds.
     */
    public static final SpellDomain REDSTONE = new SpellDomain("redstone");
    /**
     * Contains spells which are related to undead monsters and effects.
     */
    public static final SpellDomain UNDEAD = new SpellDomain("undead");
    /**
     * Contains spells relating to plants, living entities, and the overworld.
     */
    public static final SpellDomain LIFE = new SpellDomain("life");
    /**
     * Contains spells relating to illagers, villagers, and witches.
     *
     * Non-elemental "combat" spells should go here.
     */
    public static final SpellDomain ILLAGER = new SpellDomain("illager");
    /**
     * Contains spells relating to the ocean, guardians, and temples.
     */
    public static final SpellDomain OCEAN = new SpellDomain("ocean");
    /**
     * Contains spells relating to fire and the nether.
     */
    public static final SpellDomain NETHER = new SpellDomain("nether");
    /**
     * Contains spells relating to the end and conjuration
     */
    public static final SpellDomain ENDER = new SpellDomain("ender");

    private SpellDomain(String name) {
        this.setRegistryName(Spellcraft.MOD_ID, name);
    }

    public SpellDomain(ResourceLocation name) {
        this.setRegistryName(name);
    }
}
