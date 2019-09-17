package industries.immington.spellcraft.spell.nether;

import industries.immington.spellcraft.spell.ISpellcaster;
import industries.immington.spellcraft.spell.Spell;
import industries.immington.spellcraft.spell.SpellDomain;
import industries.immington.spellcraft.spell.SpellTargeting;
import net.minecraft.world.World;

public class FireBoltSpell extends Spell {
    public FireBoltSpell() {
        super("spellcraft:firebolt", SpellDomain.NETHER, SpellTargeting.PROJECTILE, 60, 120);
    }

    @Override
    public SpellDomain getDomain() {
        return SpellDomain.NETHER;
    }

    @Override
    public boolean onCastTick(World world, ISpellcaster caster, int potency) {
        return true;
    }

    @Override
    public boolean onCastComplete(World world, ISpellcaster caster, int potency) {
        return true;
    }
}
