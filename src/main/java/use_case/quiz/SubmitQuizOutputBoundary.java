package use_case.quiz;

import entity.Monster;
import entity.User;

public interface SubmitQuizOutputBoundary {
    void present(SubmitQuizOutputData data);

    void switchToBattleView();
}