package use_case.inventory_addItem;

import entity.AdventureGame;
import entity.User;

public interface InventoryAddItemUserDataAccessInterface {

    AdventureGame getGame();

    void saveGame(AdventureGame game);

    User getUser();
}

