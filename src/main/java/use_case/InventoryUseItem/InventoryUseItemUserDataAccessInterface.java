package use_case.InventoryUseItem;

import entity.Item;
import entity.Inventory;

/**
 * Data access interface for inventory use item use case
 * Simplified for single-user game - works with "the current user"
 */
public interface InventoryUseItemUserDataAccessInterface {
    /**
     * Returns inventory of the current user
     * @return the current user's inventory
     */
    Inventory getInventory();

    /**
     * Remove an item from current user's inventory
     * @param item item to be removed from inventory
     */
    void removeItem(Item item);

    /**
     * Get an item by name from current user's inventory
     * @param itemName name of item to be looked up
     * @return item looked up, or null if not found
     */
    Item getItemByName(String itemName);

    /**
     * Update current user's stats
     * @param hpIncrease HP to add
     * @param defIncrease DEF to add
     * @param dmgIncrease DMG to add
     */
    void updateUserStats(int hpIncrease, int defIncrease, int dmgIncrease);
}