package Battle_System.UseCase.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.Spells;
import Battle_System.Entity.User;

public class Battle_Interactor implements Battle_InputBoundary {
    private final BattleUserDataAccessInterface userDataAccessObject;
    private final Battle_OutputBoundary battlePresenter;

    public Battle_Interactor(BattleUserDataAccessInterface userDataAccessObject, Battle_OutputBoundary battleOutputBoundary) {
        this.userDataAccessObject = userDataAccessObject;
        this.battlePresenter = battleOutputBoundary;
    }
    /**
     * Monster's turn to attack the user, monster will randomly choose a spell and then attack.
     */
    private void MonsterTurn(User user, Monster monster) {
        Spells spell = monster.chooseSpell();
        double DMG = monster.attack(spell);
        user.HPDecrease(DMG);

        // notify presenter to update the view (After the Monster attack)
        Battle_OutputData turnOutput = new Battle_OutputData(user, monster);
        battlePresenter.updateMonsterTurnState(turnOutput);
    }

    /**
     * User's turn to attack the monster.
     */
    private void UserTurn(User user, Monster monster) {
        double DMG = user.attack();
        monster.HPDecrease(DMG);
        // notify presenter to update the view (After the User attack)
        Battle_OutputData turnOutput = new Battle_OutputData(user, monster);
        battlePresenter.updateUserTurnState(turnOutput);
    }

    /**
     * While both the monster and the user are alive, the battle will start. The battle will over once any of the user
     * and the monsters' HP is less than 0. The fight is turn-based, started fron the user turn.
     */
    public void fight(User user, Monster monster) {
        // Save the status before the fight
        userDataAccessObject.save(user, monster);
        while (user.isAlive() && monster.isAlive()) {
            // User's turn
            UserTurn(user, monster);
            // Check if monster is defeated
            if (!monster.isAlive()) {
                break;
            }
            // Monster's turn
            MonsterTurn(user, monster);
            // save the status after the fight
            userDataAccessObject.save(user, monster);
        }
    }

    /**
     * This is the final method that would be called in ca, if the user choose "FIGHT" then this method will lead to the
     * fight method.
     */
    @Override
    public void execute(Battle_InputData inputData) {
        final User user = inputData.getUser();
        final Monster monster = inputData.getMonster();

        // Execute battle
        fight(user, monster);
        // Prepare final output
        Battle_OutputData output = new Battle_OutputData(user, monster);
        // Present final result
        if (output.isWin()){
            battlePresenter.prepareWinView(output);
        }
        else{
            battlePresenter.prepareLossView(output);
        }
    }
}
