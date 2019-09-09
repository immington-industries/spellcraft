package industries.immington.spellcraft.spell;

import javax.annotation.Nonnull;

public class PotencyScale {
    private final int warmup;
    private final int max;

    @Nonnull
    public static PotencyScale floored(int warmup, int max) {
        return new PotencyScale(warmup, max);
    }

    private PotencyScale(int warmup, int max) {
        this.warmup = warmup;
        this.max = max;
    }
}
