package industries.immington.spellcraft.spell.nether;

import industries.immington.spellcraft.spell.Spell;
import industries.immington.spellcraft.spell.SpellDomain;
import industries.immington.spellcraft.spell.SpellTargeting;

public class FireBoltSpell extends Spell {
    public FireBoltSpell() {
        super(SpellDomain.NETHER, SpellTargeting.PROJECTILE, 60, 120);
    }

    @Override
    public SpellDomain getDomain() {
        return SpellDomain.NETHER;
    }
}
