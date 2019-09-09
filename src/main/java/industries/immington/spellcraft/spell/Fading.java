package industries.immington.spellcraft.spell;

import net.minecraft.entity.player.PlayerEntity;

/**
 * A Fading object represents the amount of fading is taken
 */
public class Fading {
    private final int hunger;
    private final int damage;
    private final int fatigue;

    public enum Level {
        HUNGER,
        DAMAGE,
        FATIGUE
    }

    private Fading(int hunger, int damage, int fatigue) {
        this.hunger = hunger;
        this.damage = damage;
        this.fatigue = fatigue;
    }

    public Level getLevel() {
        if (this.fatigue > 0) {
            return Level.FATIGUE;
        }
        else if (this.damage > 0) {
            return Level.DAMAGE;
        }
        else {
            return Level.HUNGER;
        }
    }

    public int getHunger() { return this.hunger; }

    public int getDamage() { return this.damage; }

    public int getFatigue() { return this.fatigue; }

    public static Fading hunger(int hunger) {
        return new Fading(hunger, 0, 0);
    }

    public static Fading damage(int hunger, int damage) {
        return new Fading(hunger, damage, 0);
    }

    public static Fading fatigue(int fatigue) {
        return new Fading(0, 0, fatigue);
    }

    public void applyToPlayer(PlayerEntity player) {
        if (player.isCreative()) {
            return;
        }
    }
}
