package interface_adapter.InventoryUseItem;

import use_case.InventoryUseItem.InventoryUseItemInputData;
import use_case.InventoryUseItem.InventoryUseItemInputBoundary;

/**
 * Controller for inventory use item feature
 * Simplified for single-user game
 */
public class InventoryUseItemController {

    private final InventoryUseItemInputBoundary useItemInteractor;

    public InventoryUseItemController(InventoryUseItemInputBoundary useItemInteractor) {
        this.useItemInteractor = useItemInteractor;
    }

    /**
     * Called when user clicks "Use Item" in the dropdown
     * @param itemName name of the item to use
     */
    public void useItem(String itemName) {
        if (itemName == null) {
            return;
        }
        InventoryUseItemInputData inputData = new InventoryUseItemInputData(itemName);
        useItemInteractor.useItem(inputData);
    }

    /**
     * Called when user wants to view their inventory
     */
    public void viewInventory() {
        InventoryUseItemInputData inputData = new InventoryUseItemInputData();
        useItemInteractor.viewInventory(inputData);
    }
}