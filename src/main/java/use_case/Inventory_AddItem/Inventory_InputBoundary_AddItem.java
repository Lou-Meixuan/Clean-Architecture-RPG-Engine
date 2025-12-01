package use_case.Inventory_AddItem;

import entity.User;

/**
 * Input boundary for Add Item use case
 */

public interface Inventory_InputBoundary_AddItem {

    /**
     * set user
     */
    void setUser(User user);

    /**
     * To be used by Inventory_AddItem_Interactor;
     * @param inputData item to be added to inventory
     */
    void addItem(Inventory_InputData_AddItem inputData);
}
