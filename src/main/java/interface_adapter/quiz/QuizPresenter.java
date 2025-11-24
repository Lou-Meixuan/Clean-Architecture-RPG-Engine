package interface_adapter.quiz;

import use_case.quiz.SubmitQuizOutputBoundary;
import use_case.quiz.SubmitQuizOutputData;

public class QuizPresenter implements SubmitQuizOutputBoundary {

    private final QuizViewModel viewModel;

    public QuizPresenter(QuizViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SubmitQuizOutputData data) {
        viewModel.quizId = data.getQuizId();
        viewModel.isCompleted = data.isCompleted();
        viewModel.status = data.getStatus();
        viewModel.feedbackMessage = data.getMessage();
    }
}
