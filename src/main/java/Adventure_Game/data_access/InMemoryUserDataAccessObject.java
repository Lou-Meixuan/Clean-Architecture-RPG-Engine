package Adventure_Game.data_access;

import Adventure_Game.entity.AdventureGame;
import Adventure_Game.entity.GameMap;
import Adventure_Game.entity.Location;
import Adventure_Game.use_case.move.MoveGameDataAccessInterface;
import Battle_System.User.User;

import java.util.Arrays;
import java.util.List;

public class InMemoryUserDataAccessObject implements MoveGameDataAccessInterface {

    private AdventureGame game;

    public InMemoryUserDataAccessObject() {
        User user = new User();

        // TODO: read from json file
        Location loc0 = new Location("Forest", 43.6532, -79.3832, null);
        Location loc1 = new Location("Cave", 43.6540, -79.3840, null);
        Location loc2 = new Location("Mountain", 43.6550, -79.3850, null);

        List<Location> locations = Arrays.asList(loc0, loc1, loc2);

        GameMap gameMap = new GameMap(locations, 0);
        this.game = new AdventureGame(user, gameMap);
    }

    @Override
    public AdventureGame getGame() {
        return this.game;
    }

    @Override
    public void saveGame(AdventureGame game) {
        this.game = game;
    }
}
