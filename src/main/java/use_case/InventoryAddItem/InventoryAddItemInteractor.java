package use_case.InventoryAddItem;

import entity.AdventureGame;
import entity.Item;
import entity.Location;
import entity.User;

public class InventoryAddItemInteractor implements InventoryAddItemInputBoundary{

private final InventoryAddItemOutputBoundary outputBoundary;
private final InventoryAddItemUserDataAccessInterface addItemUserDataAccessInterface;


public InventoryAddItemInteractor(InventoryAddItemOutputBoundary outputBoundary,
                                  InventoryAddItemUserDataAccessInterface addItemUserDataAccessInterface) {
    this.outputBoundary = outputBoundary;
    this.addItemUserDataAccessInterface = addItemUserDataAccessInterface;
}
/**
 * Adds item to user's inventory and updates output
 * @param inputData input data is the information of the item to be added
 */

@Override
public void addItem(InventoryAddItemInputData inputData){
    if (inputData == null|| inputData.getItem() == null){return;}

    User user = addItemUserDataAccessInterface.getUser();
    Item item = inputData.getItem();
    user.addItem(item);

    AdventureGame game = addItemUserDataAccessInterface.getGame();
    Location currentLocation = game.getGameMap().getCurrentLocation();
    currentLocation.setItem(null);
    addItemUserDataAccessInterface.saveGame(game);

    InventoryAddItemOutputData output = new InventoryAddItemOutputData(user.getInventory(), item);
    outputBoundary.present(output);}

}
