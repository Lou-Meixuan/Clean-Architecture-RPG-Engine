package data_access;

import entity.*;
import use_case.Battle.BattleUserDataAccessInterface;
import use_case.move.MoveGameDataAccessInterface;
import use_case.show_results.ShowResultsGameDataAccessInterface;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class FileGameDataAccessObject implements MoveGameDataAccessInterface, ShowResultsGameDataAccessInterface, BattleUserDataAccessInterface {

    // Battle state tracking
    private User userBeforeBattle;
    private User userAfterBattle;
    private Monster monsterBeforeBattle;
    private Monster monsterAfterBattle;

    private AdventureGame game;
    private final FileDataAccess fileDataAccess;
    private static final String FILE_PATH = "userdata.json";

    public FileGameDataAccessObject() {
        this.fileDataAccess = new FileDataAccess();

        // Check if userdata.json exists and is not empty
        File file = new File(FILE_PATH);
        if (file.exists() && file.length() > 0) {
            // Attempt to load existing game
            this.game = fileDataAccess.load(AdventureGame.class);
        }

        // If game is still null (file empty, doesn't exist, or couldn't be parsed), start new game
        if (this.game == null) {
            startNewGame();
        }
    }

    private void startNewGame() {
        User user = new User();
        Location loc0 = new Location("Bahen Centre for Information Technology", 43.6594, -79.3981, null);
        Location loc1 = new Location("Myhal Centre For Engineering Innovation & Entrepreneurship", 43.6606, -79.3966, new Monster());
        Location loc2 = new Location("Gerstein Science Information Centre", 43.6624, -79.3940, null);

        List<Location> locations = Arrays.asList(loc0, loc1, loc2);

        GameMap gameMap = new GameMap(locations, 0);
        this.game = new AdventureGame(user, gameMap);
    }

    @Override
    public AdventureGame getGame() {
        return this.game;
    }

    @Override
    public AdventureGame get() {
        return this.game;
    }

    @Override
    public void saveGame(AdventureGame game) {
        this.game = game;
        fileDataAccess.save(game);
    }

    @Override
    public void clearGameData() {
        File file = new File(FILE_PATH);
        System.out.println("Attempting to clear game data...");
        System.out.println("File exists: " + file.exists());
        System.out.println("File path: " + file.getAbsolutePath());
        if (file.exists()) {
            boolean deleted = file.delete();
            System.out.println("File deleted: " + deleted);
            if (!deleted) {
                System.err.println("Failed to delete file!");
            }
        }
        // Reset to a new game in memory
        startNewGame();
    }

    // BattleUserDataAccessInterface implementation

    @Override
    public void save(User user, Monster monster) {
        // If this is the first save (before battle starts)
        if (userBeforeBattle == null) {
            userBeforeBattle = cloneUser(user);
            monsterBeforeBattle = cloneMonster(monster);
            System.out.println("=== BATTLE STATE SAVED ===");
            System.out.println("Saved User HP: " + (userBeforeBattle != null ? userBeforeBattle.getHP() : "null"));
            System.out.println("Saved Monster HP: " + (monsterBeforeBattle != null ? monsterBeforeBattle.getHP() : "null"));
        }
        // Always update the "after" state with deep copy
        userAfterBattle = cloneUser(user);
        monsterAfterBattle = cloneMonster(monster);
    }

    @Override
    public User getUserBeforeBattle() {
        return userBeforeBattle;
    }

    @Override
    public User getUserAfterBattle() {
        return userAfterBattle;
    }

    @Override
    public Monster getMonsterBeforeBattle() {
        return monsterBeforeBattle;
    }

    @Override
    public Monster getMonsterAfterBattle() {
        return monsterAfterBattle;
    }


    public void resetBattleState() {
        userBeforeBattle = null;
        userAfterBattle = null;
        monsterBeforeBattle = null;
        monsterAfterBattle = null;
    }

    @Override
    public void restoreUserToBeforeBattle() {
        System.out.println("=== RESTORING USER ===");
        System.out.println("userBeforeBattle: " + (userBeforeBattle != null ? "exists, HP=" + userBeforeBattle.getHP() : "null"));
        System.out.println("game: " + (game != null ? "exists" : "null"));

        if (userBeforeBattle != null && game != null) {
            User currentUser = game.getUser();
            System.out.println("currentUser before restore: HP=" + currentUser.getHP());
            copyUserFields(userBeforeBattle, currentUser);
            System.out.println("currentUser after restore: HP=" + currentUser.getHP());
        }
    }

    private User cloneUser(User user) {
        if (user == null) return null;

        User clone = new User();
        copyUserFields(user, clone);
        return clone;
    }


    private void copyUserFields(User source, User target) {
        try {
            for (Field field : User.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(source);
                field.set(target, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Monster cloneMonster(Monster monster) {
        if (monster == null) return null;

        Monster clone = new Monster();
        try {
            // clone HP
            Field hpField = Monster.class.getDeclaredField("HP");
            hpField.setAccessible(true);
            hpField.set(clone, hpField.get(monster));

            // clone NAME
            clone.NAME = monster.NAME;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clone;
    }
}
