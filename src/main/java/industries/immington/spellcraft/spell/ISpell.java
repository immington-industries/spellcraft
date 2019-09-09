package industries.immington.spellcraft.spell;

public interface ISpell {
    SpellDomain getDomain();
    SpellTargeting getTargeting();
    int getWarmupTime();
    int getMaxPotency();
    Fading getFading(int castTime);
}
