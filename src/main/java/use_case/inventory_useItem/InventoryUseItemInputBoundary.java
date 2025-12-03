package use_case.inventory_useItem;

/**
 * Input Boundary for inventory use item use case
 */
public interface InventoryUseItemInputBoundary {

    /**
     * Called when user uses an item from inventory
     * @param inputData contains item name and user
     */
    void useItem(InventoryUseItemInputData inputData);

    /**
     * Called when user wants to view inventory
     * @param inputData contains user
     */
    void viewInventory(InventoryUseItemInputData inputData);
}