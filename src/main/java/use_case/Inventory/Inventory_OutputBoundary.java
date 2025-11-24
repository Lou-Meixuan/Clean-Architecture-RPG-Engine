package use_case.Inventory;

public interface Inventory_OutputBoundary {
    /**
     * Updates inventory when user adds item
     * @param outputData updates inventory
     */
    void addItem(Inventory_OutputData outputData);
    /**
     * @param outputData has stat changes and updates inventory
     */
    void useItem(Inventory_OutputData outputData);
    /**
     * used when User views inventory
     * @param outputData contains list of items (user inventory)
     */
    void presentInventory(Inventory_OutputData outputData);
}
