package use_case.inventory_addItem;

/**
 * Input boundary for Add Item use case
 */

public interface InventoryAddItemInputBoundary {

    /**
     * To be used by Inventory_AddItem_Interactor;
     * @param inputData item to be added to inventory
     */
    void addItem(InventoryAddItemInputData inputData);
}
