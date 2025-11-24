package use_case.Inventory;

import entity.Item;
import entity.User;
import use_case.Item.Item_Interactor;

public class Inventory_Interactor  {
    private final InventoryUserDataAccessInterface dataAccess;
    private final Inventory_OutputBoundary outputBoundary;
    private final Item_Interactor itemInteractor;
    private  User user;

    public Inventory_Interactor(InventoryUserDataAccessInterface dataAccess, Inventory_OutputBoundary outputBoundary, Item_Interactor itemInteractor) {
        this.dataAccess = dataAccess;
        this.itemInteractor = itemInteractor;
        this.outputBoundary = outputBoundary;
    }
    /**
     * set user
     * @param user user of the game
     */
    public void setUser(User user) {
        this.user = user; }

    /**
     * add item to user's inventory
     *
     * @param inputDataAddItem item to be added to inventory
     */

    public void addItem(Inventory_InputData_AddItem inputDataAddItem) {
        Item item = itemInteractor.getItemByName(inputDataAddItem.getItem_name());
        dataAccess.addItem(item);
        Inventory_OutputData output = new Inventory_OutputData(dataAccess.getInventory(), 0,0,0);
        outputBoundary.presentInventory(output);
    }

    //Helper method map type to category like in the item interactor (again)
    private String mapTypeToCategory(String type) {
        if (type == null) return "heal";
        switch (type.toLowerCase()) {
            case "weapon":
            case "rod":
            case "wand":
            case "staff":
                return "weapon";
            case "armor":
            case "shield":
                return "armour";
            default:
                return "heal";
        }
    }

    /**
     *
     * @param inputDataUseItem item from inventory to be used
     */
    public void useItem(Inventory_InputData_UseItem inputDataUseItem) {
        Item item = user.getInventory().getItemByName(inputDataUseItem.getItem_name());
        if (item == null) return;
        int hp = 0, def = 0, dmg = 0;
        String category = mapTypeToCategory(item.getType());
        switch (category) {
            case "heal":
                hp = item.getValue();
                user.addBonusHP(hp);
                break;
            case "armour":
                def = Math.max(1, Math.min(4, Math.abs(item.getValue())-4));
                user.addDEF(def);
                break;
            case "weapon":
                int raw_dmg = item.getValue();
                dmg = (raw_dmg % 2 == 0) ? 2 : 1;
                break;
        }
        dataAccess.removeItem(item.getName());

        Inventory_OutputData output = new Inventory_OutputData(dataAccess.getInventory(), hp, def, dmg);
        outputBoundary.useItem(output);
    }
    /**
     * viewing item
     */
    public void viewInventory() {
        Inventory_OutputData output = new Inventory_OutputData(dataAccess.getInventory(), 0, 0, 0);
    outputBoundary.presentInventory(output);
    }


}

