package use_case.Inventory_AddItem;

import entity.Item;
import entity.User;


public class Inventory_AddItem_Interactor implements Inventory_InputBoundary_AddItem{
private final Inventory_AddItem_OutputBoundary outputBoundary;
private  User user;

public Inventory_AddItem_Interactor(Inventory_AddItem_OutputBoundary outputBoundary) {
    this.outputBoundary = outputBoundary; }

/**
 * set user
 */
@Override
public void setUser(User user){
    this.user = user;
}
/**
 * Adds item to user's inventory and updates output
 * @param inputData input data is the information of the item to be added
 */

@Override
public void addItem(Inventory_InputData_AddItem inputData){
    if (user == null||inputData == null|| inputData.getItem() == null){return;}

    Item item = inputData.getItem();
    user.addItem(item);

    Inventory_AddItem_OutputData output = new Inventory_AddItem_OutputData(user.getInventory(), item);
    outputBoundary.present(output);}

}
