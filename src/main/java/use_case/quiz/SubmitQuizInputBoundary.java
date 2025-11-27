package use_case.quiz;

import entity.Monster;
import entity.User;

public interface SubmitQuizInputBoundary {
    void submit(SubmitQuizInputData data);

    void switchToBattleView();
}
