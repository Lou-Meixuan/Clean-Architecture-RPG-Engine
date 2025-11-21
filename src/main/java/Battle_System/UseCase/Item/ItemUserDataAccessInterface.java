package Battle_System.UseCase.Item;
import Battle_System.Entity.Item;

public interface ItemUserDataAccessInterface {

    /**
     * @param name name of the item to be fetched
     * @return item
     *
     */
    Item getItemByName(String name);

}
