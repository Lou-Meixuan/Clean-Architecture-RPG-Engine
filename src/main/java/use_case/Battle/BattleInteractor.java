package use_case.Battle;

import entity.*;

import java.util.List;

public class BattleInteractor implements BattleInputBoundary {
    private final BattleUserDataAccessInterface userDataAccessObject;
    private final BattleOutputBoundary battlePresenter;

    public BattleInteractor(BattleUserDataAccessInterface userDataAccessObject, BattleOutputBoundary battleOutputBoundary) {
        this.userDataAccessObject = userDataAccessObject;
        this.battlePresenter = battleOutputBoundary;
    }

    public void switchToQuizView(){
        battlePresenter.prepareQuizView();
    }

    /**
     * This is the final method that would be called in ca, if the user choose "FIGHT" then this method will lead to the
     * fight method.
     */
    @Override
    public void execute(BattleInputData inputData) {
        final User user = inputData.getUser();
        final Monster monster = inputData.getMonster();
        final boolean resultOfQuiz = inputData.getResultOfQuiz();

        if (userDataAccessObject.getUserBeforeBattle() == null) {
            userDataAccessObject.save(user, monster);
        }

        // User's turn
        UserTurn(user, monster, resultOfQuiz);
        // Prepare final output
        BattleOutputData output = new BattleOutputData(user, monster);
        // Check if monster is defeated
        if (!monster.isAlive()) {
            // Remove the defeated monster from the current location
            AdventureGame game = userDataAccessObject.getGame();
            Location currentLocation = game.getGameMap().getCurrentLocation();
            currentLocation.setMonster(null);

            // Save the game state so defeated monsters don't respawn
            userDataAccessObject.saveGame(game);
            userDataAccessObject.resetBattleState();

            battlePresenter.prepareWinView(output);
            return;
        }
        // Monster's turn
        MonsterTurn(user, monster);
        // Present final result
        if (!user.isAlive()) {
//            userDataAccessObject.restoreUserToBeforeBattle();
            // go back the last location
//            AdventureGame game = userDataAccessObject.getGame();
//            List<Location> path = game.getPathHistory();
//            if (path.size() > 1) {
//                path.remove(path.size() - 1); // remove current location
//                int previousIndex = path.size() - 1;
//                try {
//                    java.lang.reflect.Field indexField = GameMap.class.getDeclaredField("currentLocationIndex");
//                    indexField.setAccessible(true);
//                    indexField.setInt(game.getGameMap(), previousIndex);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            // save the game
//            userDataAccessObject.saveGame(game);

            // reset battle
//            userDataAccessObject.resetBattleState();

            // update output
//            output = new BattleOutputData(user, monster);

            userDataAccessObject.loadGameData();
            battlePresenter.prepareLossView(output);
        }
    }

    /**
     * Monster's turn to attack the user, monster will randomly choose a spell and then attack.
     */
    private void MonsterTurn(User user, Monster monster) {
        Spells spell = monster.chooseSpell();
        double DMG = monster.attack(spell);
        user.HPDecrease(DMG);
        // notify presenter to update the view (After the User attack)
        BattleOutputData turnOutput = new BattleOutputData(user, monster);
        battlePresenter.updateUserTurnState(turnOutput);
    }

    /**
     * User's turn to attack the monster.
     */
    private void UserTurn(User user, Monster monster, boolean resultOfQuiz) {
        double DMG = 0;
        if(resultOfQuiz){
            DMG = user.getDMG();
        }
        monster.HPDecrease(DMG);
        // notify presenter to update the view (After the User attack)
        BattleOutputData turnOutput = new BattleOutputData(user, monster);
        battlePresenter.updateUserTurnState(turnOutput);
    }
}
