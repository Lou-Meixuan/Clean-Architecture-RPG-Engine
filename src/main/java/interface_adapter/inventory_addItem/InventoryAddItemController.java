package interface_adapter.inventory_addItem;

import entity.Item;
import use_case.inventory_addItem.InventoryAddItemInputBoundary;
import use_case.inventory_addItem.InventoryAddItemInputData;

/**
 * Controller for AddItem usecase
 * Gets user input
 */
public class InventoryAddItemController {
    private final InventoryAddItemInputBoundary addItemBoundary;

    public InventoryAddItemController(InventoryAddItemInputBoundary addItemBoundary) {
        this.addItemBoundary = addItemBoundary;
    }

    /**
     * @param item item to be added
     */
    public void addItem(Item item) {
        InventoryAddItemInputData inputData = new InventoryAddItemInputData(item);
        addItemBoundary.addItem(inputData);


    }


}
