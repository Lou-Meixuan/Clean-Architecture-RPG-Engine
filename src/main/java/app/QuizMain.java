package app;

import data_access.InMemoryQuizDataAccessObject;
import data_access.QuizzesReader;
import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizPresenter;
import interface_adapter.quiz.QuizViewModel;
import view.QuizView;
import use_case.quiz.SubmitQuizInputBoundary;
import use_case.quiz.SubmitQuizInteractor;
import use_case.quiz.SubmitQuizOutputBoundary;

import javax.swing.*;

public class QuizMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // load quizzes from quizzes.txt file
            InMemoryQuizDataAccessObject repo = new InMemoryQuizDataAccessObject();
            new QuizzesReader().loadQuizzes(repo);

            // use cases
            QuizViewModel vm = new QuizViewModel();
            SubmitQuizOutputBoundary presenter = new QuizPresenter(vm);
            SubmitQuizInputBoundary interactor = new SubmitQuizInteractor(repo, presenter);
            QuizController controller = new QuizController(interactor);

            // gui
            QuizView view = new QuizView(repo, controller, vm);
            view.loadQuiz(1); // for example, let's start with quiz ID 1
            view.setVisible(true);

        });
    }
}