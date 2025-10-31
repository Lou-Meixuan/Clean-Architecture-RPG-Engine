package Adventure_Game.Entity;

import Battle_System.User.Monster;
import Battle_System.User.User;

import java.util.LinkedList;
import java.util.List;

public class AdventureGame {

    private User user;
    private List<Monster> monsters;

    private GameMap gameMap;
    private final List<Location> locationSequence;

    public AdventureGame(User user, List<Monster> monsters, GameMap gameMap) {
        this.user = user;
        this.monsters = monsters;
        this.gameMap = gameMap;
        this.locationSequence = new LinkedList<>();
    }

    public static AdventureGame newGame(String userName) {
        // TODO: generate new game data
        // AdventureGame game = new AdventureGame(user, monsters, gameMap);
        // return game;

        return null;
    }

    public static AdventureGame loadGame(String saveFilePath) {
        // TODO: load game data
        // AdventureGame game = new AdventureGame(user, monsters, gameMap);
        // return game;

        return null;
    }

    public void writeSaveFile(String saveFilePath) {
        // TODO: write save data
    }

    private void readSaveFile(String saveFilePath) {
        // TODO: read save data
    }

    public List<Location> getLocationSequence() {
        return locationSequence;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public User getUser() {
        return user;
    }

    public GameMap getGameMap() {
        return gameMap;
    }
}