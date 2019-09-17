package industries.immington.spellcraft.spell;

import industries.immington.spellcraft.Spellcraft;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Common (server and client) info about a spell.
 *
 * You should subclass this in order to implement your spells.
 */
public abstract class Spell extends ForgeRegistryEntry<Spell> {
    protected final static Logger LOGGER = LogManager.getLogger(Spellcraft.MOD_ID);
    private SpellDomain domain;
    private SpellTargeting targeting;
    private int maxPotency;
    private int chargeTime;

    public static DamageSource FADING_DAMAGE = new DamageSource("spellcraft:fading")
            .setMagicDamage().setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled();
    public static DamageSource SPELL_FIRE_DAMAGE = new DamageSource("spellcraft:fireSpell")
            .setMagicDamage().setFireDamage();
    public static DamageSource SPELL_DAMAGE = new DamageSource("spellcraft:spell")
            .setMagicDamage();

    public SpellDomain getDomain() {
        return this.domain;
    }

    public Spell(String registryName, SpellDomain domain, SpellTargeting targeting, int chargeTime, int maxPotency) {
        this.domain = domain;
        this.targeting = targeting;
        this.chargeTime = chargeTime;
        this.maxPotency = maxPotency;
        this.setRegistryName(registryName);
    }

    public int getMaxPotency() { return this.maxPotency; }

    public int getChargeTime() { return this.chargeTime; }

    public SpellTargeting getTargeting() { return this.targeting; }

    public boolean checkCastingBegin(World world, ISpellcaster caster) {
        return true;
    }

    public boolean onWarmupTick(World world, ISpellcaster caster, int tick) {
        return true;
    }

    public abstract boolean onCastTick(World world, ISpellcaster caster, int potency);

    public abstract boolean onCastComplete(World world, ISpellcaster caster, int potency);
}