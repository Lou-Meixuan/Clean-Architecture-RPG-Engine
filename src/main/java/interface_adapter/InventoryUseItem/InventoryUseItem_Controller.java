package interface_adapter.InventoryUseItem;

import entity.User;
import use_case.Inventory_UseItem.Inventory_InputData_UseItem;
import use_case.Inventory_UseItem.Inventory_InputBoundary_UseItem;

public class InventoryUseItem_Controller {

private final Inventory_InputBoundary_UseItem useItemBoundary;
private User user;

public InventoryUseItem_Controller(Inventory_InputBoundary_UseItem useItemBoundary, User user) {
    this.useItemBoundary = useItemBoundary;
    this.user = user; }

    // set the user
    public void setUser(User user) {
        this.user = user;
    }
// when user calls to use item
public void useItem(String itemName) {
    useItemBoundary.setUser(user);
    Inventory_InputData_UseItem inputData = new Inventory_InputData_UseItem(itemName);
    useItemBoundary.useItem(inputData); }


// when user calls to view inventory
public void viewInventory() {
    useItemBoundary.setUser(user);
    useItemBoundary.viewInventory(); }
}
