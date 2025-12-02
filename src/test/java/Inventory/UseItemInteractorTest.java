package Inventory;

import use_case.InventoryUseItem.*;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UseItemInteractorTest {

    private InventoryUseItemInteractor interactor;
    private TestOutputBoundary outputBoundary;
    private TestUserDataAccess dataAccess;
    private User testUser;
    private Inventory testInventory;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testInventory = testUser.getInventory();

        dataAccess = new TestUserDataAccess(testInventory);
        outputBoundary = new TestOutputBoundary();

        interactor = new InventoryUseItemInteractor(outputBoundary, dataAccess);
    }

    // ==================== viewInventory Tests ====================

    @Test
    void testViewInventorySuccess() {
        // Arrange
        Item item1 = new Item("Potion", "heal");
        Item item2 = new Item("Sword", "weapon");
        testInventory.addItem(item1);
        testInventory.addItem(item2);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData();

        // Act
        interactor.viewInventory(inputData);

        // Assert
        assertTrue(outputBoundary.viewInventoryCalled, "viewInventory should be called");
        assertNotNull(outputBoundary.lastViewOutputData);
        assertTrue(outputBoundary.lastViewOutputData.isSuccess());
        assertEquals(2, outputBoundary.lastViewOutputData.getItems().size());
        assertEquals("Potion", outputBoundary.lastViewOutputData.getItems().get(0).getName());
        assertEquals("Sword", outputBoundary.lastViewOutputData.getItems().get(1).getName());
    }

    @Test
    void testViewInventoryEmpty() {
        // Arrange
        InventoryUseItemInputData inputData = new InventoryUseItemInputData();

        // Act
        interactor.viewInventory(inputData);

        // Assert
        assertTrue(outputBoundary.viewInventoryCalled);
        assertTrue(outputBoundary.lastViewOutputData.isSuccess());
        assertEquals(0, outputBoundary.lastViewOutputData.getItems().size());
    }

    @Test
    void testViewInventoryNullInputData() {
        // Act
        interactor.viewInventory(null);

        // Assert
        assertTrue(outputBoundary.viewInventoryCalled);
        assertFalse(outputBoundary.lastViewOutputData.isSuccess());
        assertEquals("Invalid input data", outputBoundary.lastViewOutputData.getErrorMessage());
    }

    // ==================== useItem Tests ====================

    @Test
    void testUseHealItem() {
        // Arrange
        Item healItem = new Item("Health Potion", "heal");
        testInventory.addItem(healItem);
        dataAccess.items.add(healItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Health Potion");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.useItemCalled);
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertEquals(healItem.getValue(), outputBoundary.lastUseOutputData.getHpIncrease());
        assertEquals(0, outputBoundary.lastUseOutputData.getDefIncrease());
        assertEquals(0, outputBoundary.lastUseOutputData.getDmgIncrease());
        assertTrue(dataAccess.updateStatsCalled);
        assertTrue(dataAccess.removeItemCalled);
        assertEquals(0, testInventory.getItems().size(), "Item should be removed from inventory");
    }

    @Test
    void testUseWeaponItem() {
        // Arrange - value 10 (even number) should give dmg = 2
        Item weaponItem = new Item("Steel Sword", "weapon");
        testInventory.addItem(weaponItem);
        dataAccess.items.add(weaponItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Steel Sword");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.useItemCalled);
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertEquals(0, outputBoundary.lastUseOutputData.getHpIncrease());
        assertEquals(0, outputBoundary.lastUseOutputData.getDefIncrease());
        assertTrue(outputBoundary.lastUseOutputData.getDmgIncrease() > 0);
    }

    @Test
    void testUseArmourItem() {
        // Arrange
        Item armourItem = new Item("Shield", "shield");
        testInventory.addItem(armourItem);
        dataAccess.items.add(armourItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Shield");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.useItemCalled);
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertEquals(0, outputBoundary.lastUseOutputData.getHpIncrease());
        assertTrue(outputBoundary.lastUseOutputData.getDefIncrease() > 0);
        assertEquals(0, outputBoundary.lastUseOutputData.getDmgIncrease());
    }

    @Test
    void testUseItemNotFound() {
        // Arrange
        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Nonexistent Item");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.useItemCalled);
        assertFalse(outputBoundary.lastUseOutputData.isSuccess());
        assertTrue(outputBoundary.lastUseOutputData.getErrorMessage().contains("not found"));
    }

    @Test
    void testUseItemNullInputData() {
        // Act
        interactor.useItem(null);

        // Assert
        assertTrue(outputBoundary.useItemCalled);
        assertFalse(outputBoundary.lastUseOutputData.isSuccess());
        assertEquals("Invalid input data", outputBoundary.lastUseOutputData.getErrorMessage());
    }

    @Test
    void testUseItemNullItemName() {
        // Arrange
        InventoryUseItemInputData inputData = new InventoryUseItemInputData(null);

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.useItemCalled);
        assertFalse(outputBoundary.lastUseOutputData.isSuccess());
        assertEquals("Item name is required", outputBoundary.lastUseOutputData.getErrorMessage());
    }

    @Test
    void testUseItemEmptyItemName() {
        // Arrange
        InventoryUseItemInputData inputData = new InventoryUseItemInputData("   ");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.useItemCalled);
        assertFalse(outputBoundary.lastUseOutputData.isSuccess());
        assertEquals("Item name is required", outputBoundary.lastUseOutputData.getErrorMessage());
    }

    @Test
    void testUseRodItem() {
        // Arrange - rod should be categorized as weapon
        Item rodItem = new Item("Magic Rod", "rod");
        testInventory.addItem(rodItem);
        dataAccess.items.add(rodItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Magic Rod");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertTrue(outputBoundary.lastUseOutputData.getDmgIncrease() > 0);
    }

    @Test
    void testUseWandItem() {
        // Arrange - wand should be categorized as weapon
        Item wandItem = new Item("Magic Wand", "wand");
        testInventory.addItem(wandItem);
        dataAccess.items.add(wandItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Magic Wand");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertTrue(outputBoundary.lastUseOutputData.getDmgIncrease() > 0);
    }

    @Test
    void testUseStaffItem() {
        // Arrange - staff should be categorized as weapon
        Item staffItem = new Item("Magic Staff", "staff");
        testInventory.addItem(staffItem);
        dataAccess.items.add(staffItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Magic Staff");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertTrue(outputBoundary.lastUseOutputData.getDmgIncrease() > 0);
    }

    @Test
    void testUseArmorItem() {
        // Arrange - armor should be categorized as armour
        Item armorItem = new Item("Plate Armor", "armor");
        testInventory.addItem(armorItem);
        dataAccess.items.add(armorItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Plate Armor");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertTrue(outputBoundary.lastUseOutputData.getDefIncrease() > 0);
    }

    @Test
    void testUseRingItem() {
        // Arrange - ring should be categorized as armour
        Item ringItem = new Item("Protection Ring", "ring");
        testInventory.addItem(ringItem);
        dataAccess.items.add(ringItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Protection Ring");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertTrue(outputBoundary.lastUseOutputData.getDefIncrease() > 0);
    }

    @Test
    void testUseItemWithNullType() {
        // Arrange - null type should default to heal
        Item nullTypeItem = new Item("Mystery Item", null);
        testInventory.addItem(nullTypeItem);
        dataAccess.items.add(nullTypeItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Mystery Item");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertTrue(outputBoundary.lastUseOutputData.getHpIncrease() > 0);
    }

    @Test
    void testUseItemWithUnknownType() {
        // Arrange - unknown type should default to heal
        Item unknownItem = new Item("Strange Item", "unknown");
        testInventory.addItem(unknownItem);
        dataAccess.items.add(unknownItem);

        InventoryUseItemInputData inputData = new InventoryUseItemInputData("Strange Item");

        // Act
        interactor.useItem(inputData);

        // Assert
        assertTrue(outputBoundary.lastUseOutputData.isSuccess());
        assertTrue(outputBoundary.lastUseOutputData.getHpIncrease() > 0);
    }

    // Test helper classes
    private static class TestOutputBoundary implements InventoryUseItemOutputBoundary {
        boolean useItemCalled = false;
        boolean viewInventoryCalled = false;
        InventoryUseItemOutputData lastUseOutputData = null;
        InventoryUseItemOutputData lastViewOutputData = null;

        @Override
        public void useItem(InventoryUseItemOutputData outputData) {
            useItemCalled = true;
            lastUseOutputData = outputData;
        }

        @Override
        public void viewInventory(InventoryUseItemOutputData outputData) {
            viewInventoryCalled = true;
            lastViewOutputData = outputData;
        }
    }

    private static class TestUserDataAccess implements InventoryUseItemUserDataAccessInterface {
        private final Inventory inventory;
        List<Item> items = new ArrayList<>();
        boolean updateStatsCalled = false;
        boolean removeItemCalled = false;
        int lastHpIncrease = 0;
        int lastDefIncrease = 0;
        int lastDmgIncrease = 0;

        TestUserDataAccess(Inventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }

        @Override
        public void removeItem(Item item) {
            removeItemCalled = true;
            items.remove(item);
            inventory.removeItem(item);
        }

        @Override
        public Item getItemByName(String itemName) {
            for (Item item : items) {
                if (item.getName().equals(itemName)) {
                    return item;
                }
            }
            return null;
        }

        @Override
        public void updateUserStats(int hpIncrease, int defIncrease, int dmgIncrease) {
            updateStatsCalled = true;
            lastHpIncrease = hpIncrease;
            lastDefIncrease = defIncrease;
            lastDmgIncrease = dmgIncrease;
        }
    }
}