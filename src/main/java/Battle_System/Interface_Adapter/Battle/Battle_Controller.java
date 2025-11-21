package Battle_System.Interface_Adapter.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.User;
import Battle_System.UseCase.Battle.Battle_InputBoundary;
import Battle_System.UseCase.Battle.Battle_InputData;

/**
 * Controller for the Battle Use Case.
 *
 */
public class Battle_Controller {
    private final Battle_InputBoundary battleUseCaseInteractor;


    public Battle_Controller(Battle_InputBoundary battleUseCaseInteractor) {
        this.battleUseCaseInteractor = battleUseCaseInteractor;
    }

    public void execute(User user, Monster monster, boolean resultOfQuiz){
        final Battle_InputData battleInputData= new Battle_InputData(user, monster, resultOfQuiz);
        battleUseCaseInteractor.execute(battleInputData);
    }
}
