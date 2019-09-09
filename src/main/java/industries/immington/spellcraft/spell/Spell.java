package industries.immington.spellcraft.spell;

import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Common (server and client) info about a spell.
 *
 * You should subclass this in order to implement your spells.
 */
public abstract class Spell extends ForgeRegistryEntry<Spell> {
    private SpellDomain domain;
    private SpellTargeting targeting;
    private int maxPotency;
    private int chargeTime;

    public SpellDomain getDomain() {
        return this.domain;
    }

    public Spell(SpellDomain domain, SpellTargeting targeting, int chargeTime, int maxPotency) {
        this.domain = domain;
        this.targeting = targeting;
        this.chargeTime = chargeTime;
        this.maxPotency = maxPotency;
    }

    public int getMaxPotency() { return this.maxPotency; }

    public SpellTargeting getTargeting() { return this.targeting; }
}