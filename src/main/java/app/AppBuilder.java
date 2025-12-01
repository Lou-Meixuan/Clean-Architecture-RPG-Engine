package app;

import API.GeoapifyStaticMap;
import API.MoveStaticMapInterface;
import data_access.FileGameDataAccessObject;
import data_access.InMemoryBattleDataAccess;
import data_access.InMemoryQuizDataAccessObject;
import interface_adapter.Battle.BattleController;
import interface_adapter.Battle.BattlePresenter;
import interface_adapter.Battle.BattleViewModel;
import interface_adapter.ShowResults.ShowResultsController;
import interface_adapter.ViewManagerModel;
import interface_adapter.move.MoveController;
import interface_adapter.move.MovePresenter;
import interface_adapter.move.MoveViewModel;
import interface_adapter.opengame.OpenGameViewModel;
import interface_adapter.quiz.LoadQuizPresenter;
import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizPresenter;
import interface_adapter.quiz.QuizViewModel;
import interface_adapter.ShowResults.ShowResultsViewModel;
import interface_adapter.ShowResults.ShowResultsPresenter;
import use_case.Battle.BattleInputBoundary;
import use_case.Battle.BattleInteractor;
import use_case.Battle.BattleOutputBoundary;
import use_case.loadQuiz.LoadQuizInputBoundary;
import use_case.loadQuiz.LoadQuizInteractor;
import use_case.loadQuiz.LoadQuizOutputBoundary;
import use_case.move.MoveInputBoundary;
import use_case.move.MoveInteractor;
import use_case.move.MoveOutputBoundary;
import use_case.quiz.SubmitQuizInputBoundary;
import use_case.quiz.SubmitQuizInteractor;
import use_case.quiz.SubmitQuizOutputBoundary;
import use_case.show_results.ShowResultsInputBoundary;
import use_case.show_results.ShowResultsInteractor;
import use_case.show_results.ShowResultsOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // set which data access implementation to use, can be any
    // of the classes from the data_access package

    // DAO version using local file storage
    private final FileGameDataAccessObject gameDataAccess = new FileGameDataAccessObject();
    private final InMemoryBattleDataAccess battleDataAccess = new InMemoryBattleDataAccess(gameDataAccess);
    private final InMemoryQuizDataAccessObject quizDataAccess = new InMemoryQuizDataAccessObject();

    // DAO version using a shared external database
    // final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);

    private BattleView battleView;
    private BattleViewModel battleViewModel;

    private MoveView moveView;
    private MoveViewModel moveViewModel;

    private OpenGameView openGameView;
    private OpenGameViewModel openGameViewModel;

    private QuizView quizView;
    private QuizViewModel quizViewModel;

    private ShowResultsView showResultsView;
    private ShowResultsViewModel showResultsViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addBattleView() {
        battleViewModel = new BattleViewModel();
        battleView = new BattleView(battleViewModel);
        cardPanel.add(battleView, battleView.getViewName());
        return this;
    }

    public AppBuilder addMoveView() {
        moveViewModel = new MoveViewModel();
        moveView = new MoveView(moveViewModel);
        cardPanel.add(moveView, moveView.getViewName());
        return this;
    }

    public AppBuilder addOpenGameView() {
        openGameViewModel = new OpenGameViewModel();
        // openGameView = new OpenGameView(openGameViewModel);
        // cardPanel.add(quizView, quizView.getViewName());
        return this;
    }

    public AppBuilder addQuizView() {
        quizViewModel = new QuizViewModel();
        quizView = new QuizView(quizViewModel);
        cardPanel.add(quizView, quizView.getViewName());
        return this;
    }

    public AppBuilder addShowResultsView() {
        showResultsViewModel = new ShowResultsViewModel();
        showResultsView = new ShowResultsView(showResultsViewModel);
        cardPanel.add(showResultsView, showResultsView.getViewName());
        return this;
    }

    public AppBuilder addBattleUseCase() {
        final BattleOutputBoundary battleOutputBoundary = new BattlePresenter(battleViewModel, viewManagerModel);
        final BattleInputBoundary battleInteractor = new BattleInteractor(
                battleDataAccess, battleOutputBoundary);

        BattleController controller = new BattleController(battleInteractor, quizViewModel);
        battleView.setBattleController(controller);
        return this;
    }

    public AppBuilder addMoveUseCase() {
        MoveStaticMapInterface mapService = new GeoapifyStaticMap();
        final MoveOutputBoundary moveOutputBoundary = new MovePresenter(viewManagerModel, moveViewModel,
                mapService, battleViewModel);
        final MoveInputBoundary moveInteractor = new MoveInteractor(
                gameDataAccess, moveOutputBoundary);

        MoveController controller = new MoveController(moveInteractor);
        moveView.setMoveController(controller);
        return this;
    }

    public AppBuilder addOpenGameUseCase() {
        return this;
    }

    public AppBuilder addQuizUseCase() {
        final LoadQuizOutputBoundary loadQuizOutputBoundary = new LoadQuizPresenter(quizViewModel);
        final SubmitQuizOutputBoundary submitQuizOutputBoundary = new QuizPresenter(
                quizViewModel, battleViewModel, viewManagerModel);
        final LoadQuizInputBoundary loadQuizInteractor = new LoadQuizInteractor(
                quizDataAccess, loadQuizOutputBoundary);
        final SubmitQuizInputBoundary submitQuizInteractor = new SubmitQuizInteractor(
                quizDataAccess, submitQuizOutputBoundary);

        QuizController controller = new QuizController(submitQuizInteractor, loadQuizInteractor);
        quizView.setQuizController(controller);
        return this;
    }

    public AppBuilder addShowResultsUseCase() {
        final ShowResultsOutputBoundary showResultsOutputBoundary = new ShowResultsPresenter(showResultsViewModel);
        final ShowResultsInputBoundary showResultsInteractor = new ShowResultsInteractor(
                gameDataAccess, showResultsOutputBoundary);

        ShowResultsController controller = new ShowResultsController(showResultsInteractor);
        return this;
    }

    public AppBuilder addResultScreenUseCase() {
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        //viewManagerModel.setState(OpenGameView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }

}
