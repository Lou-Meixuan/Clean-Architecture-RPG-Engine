package interface_adapter.InventoryAddItem;

import entity.User;
import entity.Item;
import use_case.Inventory_AddItem.Inventory_InputBoundary_AddItem;
import use_case.Inventory_AddItem.Inventory_InputData_AddItem;

/**
 * Controller for AddItem usecase
 * Gets user input
 */
public class InventoryAddItem_Controller {
    private final Inventory_InputBoundary_AddItem addItemBoundary;

    public InventoryAddItem_Controller(Inventory_InputBoundary_AddItem addItemBoundary) {
        this.addItemBoundary = addItemBoundary;
    }

    /**
     * @param user inventory of user to be updated
     * @param item item to be added
     */
    public void addItem(User user, Item item) {
        addItemBoundary.setUser(user);
        Inventory_InputData_AddItem inputData = new Inventory_InputData_AddItem(item);
        addItemBoundary.addItem(inputData);


    }


}
