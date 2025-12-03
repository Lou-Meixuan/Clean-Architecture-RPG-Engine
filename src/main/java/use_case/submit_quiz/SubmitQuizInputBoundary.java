package use_case.submit_quiz;

public interface SubmitQuizInputBoundary {
    void submit(SubmitQuizInputData data);

    void switchToBattleView(boolean isCorrect);
}
