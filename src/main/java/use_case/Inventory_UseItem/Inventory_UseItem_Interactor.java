package use_case.Inventory_UseItem;

import entity.Item;
import entity.User;

public class Inventory_UseItem_Interactor implements Inventory_InputBoundary_UseItem{
    private final Inventory_UseItem_OutputBoundary outputBoundary;
    private User user;

    public Inventory_UseItem_Interactor(Inventory_UseItem_OutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }
    /**
     * set user
     * @param user user of the game
     */
    @Override
    public void setUser(User user) {
        this.user = user; }


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
            case "ring":
                return "armour";
            default:
                return "heal"; } }

    /**
     * @param inputDataUseItem item from inventory to be used
     */
    @Override
    public void useItem(Inventory_InputData_UseItem inputDataUseItem) {
        if (user == null || inputDataUseItem == null) {return;}
        // Item from data access
        Item item = user.getItemByName(inputDataUseItem.getItemName());
        if (item == null) {return;}
        int hp = 0, def = 0, dmg = 0;

        // updates stats based on hp
        String category = mapTypeToCategory(item.getType());
        switch (category) {
            case "heal":
                hp = Math.max(1,item.getValue());
                user.addBonusHP(hp);
                break;
            case "armour":
                def = Math.max(1, Math.min(4, Math.abs(item.getValue())-4));
                user.addDEF(def);
                break;
            case "weapon":
                dmg = (item.getValue() % 2 == 0) ? 2 : 1;
                user.addDMG(dmg);
                break;
        }

        user.removeItem(item);

        // updated inventory
        Inventory_UseItem_OutputData output = new Inventory_UseItem_OutputData(user.getInventory(), hp, def, dmg);
        outputBoundary.useItem(output);
    }
    /**
     * viewing inventory
     */
    @Override
    public void viewInventory() {
        Inventory_UseItem_OutputData output = new Inventory_UseItem_OutputData(user.getInventory(), 0, 0, 0);
    outputBoundary.viewInventory(output);
    }



}

