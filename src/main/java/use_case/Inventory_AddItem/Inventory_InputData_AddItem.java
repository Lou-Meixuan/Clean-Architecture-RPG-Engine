package use_case.Inventory_AddItem;

import entity.Item;

/**
 * When prompted to add an item to inventory,
 * the interactor will receive an item to be added to the inventory
 */
public class Inventory_InputData_AddItem {
    private final Item item;

    public Inventory_InputData_AddItem(Item item)
    {this.item = item;}

    public Item getItem()
    {return item;}
}
