package use_case.InventoryUseItem;

import entity.Item;
import entity.Inventory;
import use_case.InventoryUseItem.InventoryUseItemOutputData.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class InventoryUseItemInteractor implements InventoryUseItemInputBoundary {
    private final InventoryUseItemOutputBoundary outputBoundary;
    private final InventoryUseItemUserDataAccessInterface userDAO;

    public InventoryUseItemInteractor(InventoryUseItemOutputBoundary outputBoundary,
                                      InventoryUseItemUserDataAccessInterface userDAO) {
        this.outputBoundary = outputBoundary;
        this.userDAO = userDAO;
    }

    /**
     * Helper method: map item type to category
     */
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
                return "heal";
        }
    }

    /**
     * Helper method: convert Inventory entity to list of ItemDTOs
     */
    private List<ItemDTO> convertInventoryToDTO(Inventory inventory) {
        List<ItemDTO> itemDTOs = new ArrayList<>();
        if (inventory != null && inventory.getItems() != null) {
            for (Item item : inventory.getItems()) {
                itemDTOs.add(new ItemDTO(
                        item.getName(),
                        item.getType(),
                        item.getValue()
                ));
            }
        }
        return itemDTOs;
    }

    @Override
    public void useItem(InventoryUseItemInputData inputData) {
        if (inputData == null) {
            outputBoundary.useItem(new InventoryUseItemOutputData("Invalid input data"));
            return;
        }

        String itemName = inputData.getItemName();
        if (itemName == null || itemName.trim().isEmpty()) {
            outputBoundary.useItem(new InventoryUseItemOutputData("Item name is required"));
            return;
        }

        Item item = userDAO.getItemByName(itemName);
        if (item == null) {
            outputBoundary.useItem(new InventoryUseItemOutputData("Item '" + itemName + "' not found in inventory"));
            return;
        }

        int hp = 0, def = 0, dmg = 0;

        String category = mapTypeToCategory(item.getType());
        switch (category) {
            case "heal":
                hp = Math.max(1, item.getValue());
                break;
            case "armour":
                def = Math.max(1, Math.min(4, Math.abs(item.getValue()) - 4));
                break;
            case "weapon":
                dmg = (item.getValue() % 2 == 0) ? 2 : 1;
                break;
        }

        // Update user stats through DAO
        userDAO.updateUserStats(hp, def, dmg);

        // Remove item from inventory
        userDAO.removeItem(item);

        // Get updated inventory and convert to DTOs
        Inventory updatedInventory = userDAO.getInventory();
        List<ItemDTO> itemDTOs = convertInventoryToDTO(updatedInventory);

        InventoryUseItemOutputData output = new InventoryUseItemOutputData(
                itemDTOs, hp, def, dmg);
        outputBoundary.useItem(output);
    }

    @Override
    public void viewInventory(InventoryUseItemInputData inputData) {
        if (inputData == null) {
            outputBoundary.viewInventory(new InventoryUseItemOutputData("Invalid input data"));
            return;
        }

        Inventory inventory = userDAO.getInventory();
        List<ItemDTO> itemDTOs = convertInventoryToDTO(inventory);

        InventoryUseItemOutputData output = new InventoryUseItemOutputData(
                itemDTOs, 0, 0, 0);
        outputBoundary.viewInventory(output);
    }
}