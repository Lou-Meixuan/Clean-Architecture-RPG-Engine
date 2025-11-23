package Battle_System.Interface_Adapter.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.User;
import Battle_System.UseCase.Battle.Battle_InputBoundary;
import Battle_System.UseCase.Battle.Battle_InputData;
import Battle_System.View.Quiz_ViewModel;

/**
 * Controller for the Battle Use Case.
 *
 */
public class Battle_Controller {
    private final Battle_InputBoundary battleUseCaseInteractor;
    private final Quiz_ViewModel quizViewModel;


    public Battle_Controller(Battle_InputBoundary battleUseCaseInteractor, Quiz_ViewModel quizViewModel) {
        this.battleUseCaseInteractor = battleUseCaseInteractor;
        this.quizViewModel = quizViewModel;
    }

    public void execute(User user, Monster monster, boolean resultOfQuiz){
        final Battle_InputData battleInputData= new Battle_InputData(user, monster, resultOfQuiz);
        battleUseCaseInteractor.execute(battleInputData);
    }

    public void switchToQuizView(User user, Monster monster){
        quizViewModel.getState().setUser(user);
        quizViewModel.getState().setMonster(monster);
        battleUseCaseInteractor.switchToQuizView();
    }
}
