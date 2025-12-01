import entity.Item;
import entity.User;
import org.junit.Test;
import use_case.Inventory_AddItem.Inventory_AddItem_OutputBoundary;
import use_case.Inventory_AddItem.Inventory_AddItem_OutputData;
import use_case.Inventory_AddItem.Inventory_AddItem_Interactor;
import use_case.Inventory_AddItem.Inventory_InputData_AddItem;

import static org.junit.Assert.*;

public class AddItemInteractorTest {
    // Test with all expected information given/no early return
    // (for standard use)

    @Test
    public void addItemStandardTest() {
        User user = new User();
        Item item = new Item("Blades of Frost", "Weapon");

        Inventory_AddItem_OutputBoundary output = new Inventory_AddItem_OutputBoundary() {
            @Override
            public void present(Inventory_AddItem_OutputData data) {
                assertEquals(item, data.getItem());
                assertTrue(data.getInventory().getItems().contains(item));
            }
        };

        Inventory_AddItem_Interactor interactor = new Inventory_AddItem_Interactor(output);
        interactor.setUser(user);

        Inventory_InputData_AddItem input = new Inventory_InputData_AddItem(item);
        interactor.addItem(input);

        assertTrue(user.getInventory().getItems().contains(item));


    }
    // add item user is null

    @Test
    public void addItemUserNull() {
        Item item = new Item("Blades of Frost", "Weapon");

        Inventory_AddItem_OutputBoundary output = new Inventory_AddItem_OutputBoundary() {
            @Override
            public void present(Inventory_AddItem_OutputData data) {
                fail("Output should not be called when user is null");
            }
        };

        Inventory_AddItem_Interactor interactor = new Inventory_AddItem_Interactor(output);

        interactor.addItem(new Inventory_InputData_AddItem(item));
    }

    //  add item input is null
    @Test
    public void addItemInputNull() {
        User user = new User();

        Inventory_AddItem_OutputBoundary output = new Inventory_AddItem_OutputBoundary() {
            @Override
            public void present(Inventory_AddItem_OutputData data) {
                fail("Output should not be called when inputData is null");
            }
        };

        Inventory_AddItem_Interactor interactor = new Inventory_AddItem_Interactor(output);
        interactor.setUser(user);

        interactor.addItem(null);
    }



    // add item is null test
    @Test
    public void addItemItemNull() {
        User user = new User();

        Inventory_InputData_AddItem input = new Inventory_InputData_AddItem(null);

        Inventory_AddItem_OutputBoundary output = new Inventory_AddItem_OutputBoundary() {
            @Override
            public void present(Inventory_AddItem_OutputData data) {
                fail("Output should not be called when item is null");
            }
        };

        Inventory_AddItem_Interactor interactor = new Inventory_AddItem_Interactor(output);
        interactor.setUser(user);

        interactor.addItem(input);
    }




}
