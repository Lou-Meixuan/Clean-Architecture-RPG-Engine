
package adventure_game.data_access;

import Battle_System.Entity.User;
import adventure_game.entity.AdventureGame;
import adventure_game.entity.GameMap;
import adventure_game.entity.Location;
import adventure_game.use_case.move.MoveGameDataAccessInterface;
import data_access.FileDataAccess;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class InMemoryUserDataAccessObject implements MoveGameDataAccessInterface {

    private AdventureGame game;
    private final FileDataAccess fileDataAccess;
    private static final String FILE_PATH = "userdata.json";

    public InMemoryUserDataAccessObject() {
        this.fileDataAccess = new FileDataAccess();

        // Check if userdata.json exists and is not empty
        File file = new File(FILE_PATH);
        if (file.exists() && file.length() > 0) {
            // Load existing game
            this.game = fileDataAccess.load(AdventureGame.class);
            if (this.game != null) {
                // System.out.println("Game loaded from userdata.json");
                // System.out.println("Current location: " + game.getGameMap().getCurrentLocation().getName());
            } else {
                // File exists but couldn't be parsed, start new game
                startNewGame();
            }
        } else {
            // File doesn't exist or is empty, start new game
            startNewGame();
        }
    }

    private void startNewGame() {
        User user = new User();
        // System.out.println("Starting new game...");
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
        fileDataAccess.save(game);
    }
}