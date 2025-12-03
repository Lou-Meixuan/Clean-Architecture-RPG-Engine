package use_case.inventory_useItem;

/**
 * Input data for inventory use item use case
 * Simplified for single-user game (no user ID needed)
 */
public class InventoryUseItemInputData {
    private final String itemName;

    /**
     * Constructor for using an item
     * @param itemName name of the item to use
     */
    public InventoryUseItemInputData(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Constructor for viewing inventory (no item name needed)
     */
    public InventoryUseItemInputData() {
        this.itemName = null;
    }

    public String getItemName() {
        return itemName;
    }
}