package use_case.Inventory;


/**
 * Input Boundary for item use case
 */

public interface Inventory_InputBoundary {

    /**
     * @param input_data the input inventory data when user adds item to inventory
     */
    void addItem(Inventory_InputData_AddItem input_data);

    /**
     * @param input_data called when user uses item from inventory
     */
    void useItem(Inventory_InputData_AddItem input_data);

    /**
     * no param. called when user wants to view inventory
     */
    void viewInventory();

}


