package use_case.submit_quiz;

public interface SubmitQuizOutputBoundary {
    void present(SubmitQuizOutputData data);

    void switchToBattleView(boolean isCorrect);
}