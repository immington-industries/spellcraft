package industries.immington.spellcraft.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

/**
 * Base class for rods extends from sword.
 *
 * Keeps the sword's ability to break cobwebs.
 */
public class ScepterItem extends SwordItem {
    public ScepterItem(IItemTier tier, int attackDamage, float attackSpeed, Item.Properties propertiesBuilder) {
        super(tier, attackDamage, attackSpeed, propertiesBuilder);
    }
}
