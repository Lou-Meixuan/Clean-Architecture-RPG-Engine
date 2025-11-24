package interface_adapter.quiz;

import use_case.quiz.SubmitQuizInputBoundary;
import use_case.quiz.SubmitQuizInputData;

public class QuizController {
    private final SubmitQuizInputBoundary submitUseCase;

    public QuizController(SubmitQuizInputBoundary submitUseCase) {
        this.submitUseCase = submitUseCase;
    }

    /**
     * @param selectedOptionIdOrNull  pass null if user didn't pick an option
     */
    public void onSubmit(int quizId, Integer selectedOptionIdOrNull) {
        SubmitQuizInputData input = new SubmitQuizInputData(quizId, selectedOptionIdOrNull);
        submitUseCase.submit(input);
    }
}
