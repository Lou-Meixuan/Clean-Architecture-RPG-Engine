package data_access;

import entity.AdventureGame;
import entity.Monster;
import entity.User;
import use_case.Battle.BattleUserDataAccessInterface;

import java.lang.reflect.Field;

/**
 * In-Memory implementation of BattleUserDataAccessInterface.
 * Stores battle state in memory during the battle.
 */
public class InMemoryBattleDataAccess implements BattleUserDataAccessInterface {

    // Store battle states (before and after)
    private User userBeforeBattle;
    private User userAfterBattle;
    private Monster monsterBeforeBattle;
    private Monster monsterAfterBattle;

    // Reference to game data access for saving game state
    private FileGameDataAccessObject gameDataAccess;

    public InMemoryBattleDataAccess() {
    }

    public InMemoryBattleDataAccess(FileGameDataAccessObject gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public void setGameDataAccess(FileGameDataAccessObject gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    /**
     * Saves the current state of user and monster using deep copy.
     * This creates a snapshot of the current battle state.
     */
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

    /**
     * Returns the user state before the battle started.
     */
    @Override
    public User getUserBeforeBattle() {
        return userBeforeBattle;
    }

    /**
     * Returns the user state after the battle.
     */
    @Override
    public User getUserAfterBattle() {
        return userAfterBattle;
    }

    /**
     * Returns the monster state before the battle started.
     */
    @Override
    public Monster getMonsterBeforeBattle() {
        return monsterBeforeBattle;
    }

    /**
     * Returns the monster state after the battle.
     */
    @Override
    public Monster getMonsterAfterBattle() {
        return monsterAfterBattle;
    }

    /**
     * Resets the battle state (for starting a new battle).
     */
    @Override
    public void resetBattleState() {
        userBeforeBattle = null;
        userAfterBattle = null;
        monsterBeforeBattle = null;
        monsterAfterBattle = null;
    }

    /**
     * Restores the user to the state before the battle.
     * Called when the user is defeated by a monster.
     */
    @Override
    public void restoreUserToBeforeBattle() {
        if (userBeforeBattle != null && gameDataAccess != null) {
            User currentUser = gameDataAccess.getGame().getUser();
            copyUserFields(userBeforeBattle, currentUser);
        }
    }

    /**
     * Creates a deep clone of the User using reflection.
     */
    private User cloneUser(User user) {
        if (user == null) return null;

        User clone = new User();
        copyUserFields(user, clone);
        return clone;
    }

    /**
     * Copy all fields from source User to target User.
     */
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

    /**
     * Creates a deep clone of the Monster using reflection.
     */
    private Monster cloneMonster(Monster monster) {
        if (monster == null) return null;

        Monster clone = new Monster();
        try {
            // Copy HP
            Field hpField = Monster.class.getDeclaredField("HP");
            hpField.setAccessible(true);
            hpField.set(clone, hpField.get(monster));

            // Copy NAME
            clone.NAME = monster.NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clone;
    }

    @Override
    public AdventureGame getGame() {
        if (gameDataAccess == null) {
            throw new IllegalStateException("Game data access not set. Call setGameDataAccess() first.");
        }
        return gameDataAccess.getGame();
    }

    @Override
    public void saveGame(AdventureGame game) {
        if (gameDataAccess == null) {
            throw new IllegalStateException("Game data access not set. Call setGameDataAccess() first.");
        }
        gameDataAccess.saveGame(game);
    }
}