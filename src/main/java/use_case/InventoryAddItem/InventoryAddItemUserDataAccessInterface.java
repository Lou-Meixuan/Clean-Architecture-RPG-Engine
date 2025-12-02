package use_case.InventoryAddItem;

import entity.AdventureGame;
import entity.Item;
import entity.Inventory;
import entity.User;

public interface InventoryAddItemUserDataAccessInterface {

    AdventureGame getGame();

    void saveGame(AdventureGame game);

    User getUser();
}

