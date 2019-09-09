package industries.immington.spellcraft.spell.redstone;

import industries.immington.spellcraft.spell.ISpellcaster;
import industries.immington.spellcraft.spell.Spell;
import industries.immington.spellcraft.spell.SpellDomain;
import industries.immington.spellcraft.spell.SpellTargeting;
import net.minecraft.world.World;

public class FlingSpell extends Spell {
    private static final int CHARGE_TIME = 60;
    private static final int MAX_POTENCY = 60;

    public FlingSpell() {
        super(SpellDomain.REDSTONE, SpellTargeting.PROJECTILE, CHARGE_TIME, MAX_POTENCY);
    }

    public void onWarmupBegin(World world, ISpellcaster caster) {

    }

    public void renderCastTick(World world, ISpellcaster caster, int potency) {

    }

    public void onCastTick(World world, ISpellcaster caster, int potency) {

    }

    public void onCastComplete(World world, ISpellcaster caster, int potency) {

    }
}
