package use_case.Inventory;

import entity.Item;

import java.util.List;

public interface InventoryUserDataAccessInterface {
    /**
     * returns inventory of user
     */
    List<Item> getInventory();

    /**
     * add item to inventory
     * @param item item to be added to inventory
     */
    void addItem(Item item);

    /**
     * remove an item by name
     * @param name item to be removed from inventory
     */
    void removeItem(String name);

}
