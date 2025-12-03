package use_case.inventory_addItem;


public interface InventoryAddItemOutputBoundary {

    /**
     * @param outputData inventory
     */
    void present( InventoryAddItemOutputData outputData);
}