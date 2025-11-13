package Battle_System.UseCase.Battle;

import Battle_System.Entity.Battle;
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
    }

    /**
     * User's turn to attack the monster.
     */
    private void UserTurn(User user, Monster monster) {
        double DMG = user.attack();
        monster.HPDecrease(DMG);
    }

    /**
     * While both the monster and the user are alive, the battle will start. The battle will over once any of the user
     * and the monsters' HP is less than 0. The fight is turn-based, started fron the user turn.
     */
    public void fight(User user, Monster monster) {
        while (user.isAlive() && monster.isAlive()) {
            UserTurn(user, monster);
            if (monster.isAlive()) {
                break;
            }
            MonsterTurn(user, monster);
        }
        // TODO: if the monster is dead then we need to delete it from map and move it to the defeated monster list.
        //  If the user is dead, then we go to the recent saved data.
    }

    /**
     * This is the final method that would be called in ca, if the user choose "FIGHT" then this method will lead to the
     * fight method.
     */
    @Override
    public void execute(Battle_InputData inputData) {
        final User user = inputData.getUser();
        final Monster monster = inputData.getMonster();
        // TODO: finish the execute
        fight(user, monster);
        Battle_OutputData output = new Battle_OutputData(user, monster);
        if (output.isWin()){
            battlePresenter.prepareWinView(output);
        }
        else{
            battlePresenter.prepareLossView(output);
        }
    }
}
