package use_case.Inventory_UseItem;


import entity.User;

/**
 * Input Boundary for item use case
 */

public interface Inventory_InputBoundary_UseItem {

    /**
     * set user
     */
    void setUser(User user);
    /**
     * @param inputData called when user uses item from inventory
     */
    void useItem(Inventory_InputData_UseItem inputData);

    /**
     * no param. called when user wants to view inventory
     */
    void viewInventory();

}


