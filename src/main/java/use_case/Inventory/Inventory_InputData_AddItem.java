package use_case.Inventory;

public class Inventory_InputData_AddItem {
    private final String item_name;
    public Inventory_InputData_AddItem(String item_name)
    {this.item_name=item_name;}
    public String getItem_name()
    {return item_name;}
}
