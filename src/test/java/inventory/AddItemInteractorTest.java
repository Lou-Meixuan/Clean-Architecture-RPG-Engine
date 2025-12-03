package inventory;

import use_case.inventory_addItem.*;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddItemInteractorTest {

    private InventoryAddItemInteractor interactor;
    private TestOutputBoundary outputBoundary;
    private TestUserDataAccess dataAccess;
    private User testUser;
    private AdventureGame testGame;
    private Item testItem;

    @BeforeEach
    void setUp() {
        // Create test user with inventory
        testUser = new User();

        // Create test item
        testItem = new Item("Health Potion", "heal");

        // Create test game with locations
        Location loc1 = new Location("Location 1", 43.0, -79.0, null, testItem);
        Location loc2 = new Location("Location 2", 43.1, -79.1, null, null);
        List<Location> locations = Arrays.asList(loc1, loc2);
        GameMap gameMap = new GameMap(locations, 0);
        testGame = new AdventureGame(testUser, gameMap);

        // Create test data access and output boundary
        dataAccess = new TestUserDataAccess(testUser, testGame);
        outputBoundary = new TestOutputBoundary();

        // Create interactor
        interactor = new InventoryAddItemInteractor(outputBoundary, dataAccess);
    }

    @Test
    void testAddItemSuccess() {
        // Arrange
        InventoryAddItemInputData inputData = new InventoryAddItemInputData(testItem);

        // Act
        interactor.addItem(inputData);

        // Assert
        assertTrue(outputBoundary.wasCalled, "Output boundary should be called");
        assertNotNull(outputBoundary.lastOutputData, "Output data should not be null");
        assertEquals(testItem, outputBoundary.lastOutputData.getItem(), "Item should match");
        assertEquals(1, testUser.getInventory().getItems().size(), "Inventory should have 1 item");
        assertTrue(testUser.getInventory().getItems().contains(testItem), "Inventory should contain the test item");
        assertNull(testGame.getGameMap().getCurrentLocation().getItem(), "Location item should be null after pickup");
        assertTrue(dataAccess.saveGameCalled, "Game should be saved");
    }

    @Test
    void testAddItemNullInputData() {
        // Arrange
        InventoryAddItemInputData inputData = null;

        // Act
        interactor.addItem(inputData);

        // Assert
        assertFalse(outputBoundary.wasCalled, "Output boundary should not be called for null input");
        assertEquals(0, testUser.getInventory().getItems().size(), "Inventory should remain empty");
    }

    @Test
    void testAddItemNullItem() {
        // Arrange
        InventoryAddItemInputData inputData = new InventoryAddItemInputData(null);

        // Act
        interactor.addItem(inputData);

        // Assert
        assertFalse(outputBoundary.wasCalled, "Output boundary should not be called for null item");
        assertEquals(0, testUser.getInventory().getItems().size(), "Inventory should remain empty");
    }

    @Test
    void testAddMultipleItems() {
        // Arrange
        Item item1 = new Item("Potion 1", "heal");
        Item item2 = new Item("Sword", "weapon");

        InventoryAddItemInputData inputData1 = new InventoryAddItemInputData(item1);
        InventoryAddItemInputData inputData2 = new InventoryAddItemInputData(item2);

        // Act
        interactor.addItem(inputData1);
        interactor.addItem(inputData2);

        // Assert
        assertEquals(2, testUser.getInventory().getItems().size(), "Inventory should have 2 items");
        assertTrue(testUser.getInventory().getItems().contains(item1));
        assertTrue(testUser.getInventory().getItems().contains(item2));
    }

    @Test
    void testAddItemUpdatesInventoryInOutputData() {
        // Arrange
        InventoryAddItemInputData inputData = new InventoryAddItemInputData(testItem);

        // Act
        interactor.addItem(inputData);

        // Assert
        Inventory outputInventory = outputBoundary.lastOutputData.getInventory();
        assertNotNull(outputInventory, "Output inventory should not be null");
        assertEquals(1, outputInventory.getItems().size(), "Output inventory should have 1 item");
        assertEquals(testItem, outputInventory.getItems().get(0), "Output inventory should contain the test item");
    }

    // Test helper classes
    private static class TestOutputBoundary implements InventoryAddItemOutputBoundary {
        boolean wasCalled = false;
        InventoryAddItemOutputData lastOutputData = null;

        @Override
        public void present(InventoryAddItemOutputData outputData) {
            wasCalled = true;
            lastOutputData = outputData;
        }
    }

    private static class TestUserDataAccess implements InventoryAddItemUserDataAccessInterface {
        private final User user;
        private final AdventureGame game;
        boolean saveGameCalled = false;

        TestUserDataAccess(User user, AdventureGame game) {
            this.user = user;
            this.game = game;
        }

        @Override
        public AdventureGame getGame() {
            return game;
        }

        @Override
        public void saveGame(AdventureGame game) {
            saveGameCalled = true;
        }

        @Override
        public User getUser() {
            return user;
        }
    }
}