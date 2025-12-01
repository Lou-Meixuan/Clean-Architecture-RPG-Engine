package app;

import API.GeoapifyStaticMap;
import API.MoveStaticMapInterface;
import data_access.*;
import entity.*;
import interface_adapter.Battle.BattleController;
import interface_adapter.Battle.BattlePresenter;
import interface_adapter.Battle.BattleState;
import interface_adapter.Battle.BattleViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.move.MoveController;
import interface_adapter.move.MovePresenter;
import interface_adapter.move.MoveViewModel;
import interface_adapter.opengame.OpenGameController;
import interface_adapter.opengame.OpenGamePresenter;
import interface_adapter.opengame.OpenGameScreenSwitcher;
import interface_adapter.opengame.OpenGameViewModel;
import interface_adapter.quiz.*;
import interface_adapter.results.ResultsViewModel;
import interface_adapter.results.ShowResultsController;
import interface_adapter.results.ShowResultsPresenter;
import use_case.Battle.BattleInteractor;
import use_case.loadQuiz.LoadQuizInputBoundary;
import use_case.loadQuiz.LoadQuizInteractor;
import use_case.loadQuiz.LoadQuizOutputBoundary;
import use_case.move.MoveInputBoundary;
import use_case.move.MoveInteractor;
import use_case.move.MoveOutputData;
import use_case.openGame.*;
import use_case.quiz.SubmitQuizInputBoundary;
import use_case.quiz.SubmitQuizInteractor;
import use_case.quiz.SubmitQuizOutputBoundary;
import use_case.show_results.ShowResultsInputBoundary;
import use_case.show_results.ShowResultsInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MoveTestApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame application = new JFrame("Adventure & Battle Integrated System");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(1024, 768);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);

        ViewManagerModel viewManagerModel = new ViewManagerModel();

        MoveViewModel moveViewModel = new MoveViewModel();

        OpenGameViewModel openGameViewModel = new OpenGameViewModel();
        BattleViewModel battleViewModel = new BattleViewModel();
        QuizViewModel quizViewModel = new QuizViewModel();
        FileGameDataAccessObject gameDataAccess = new FileGameDataAccessObject();
        System.out.println(gameDataAccess.getGame().getUser().getHP());

        ScreenSwitchBoundary openGameScreenSwitcher =
                new OpenGameScreenSwitcher(moveViewModel, viewManagerModel);

        OpenGameOutputBoundary openGamePresenter =
                new OpenGamePresenter(openGameViewModel, viewManagerModel);

        OpenGameInputBoundary openGameInteractor =
                new OpenGameInteractor(openGamePresenter, gameDataAccess, openGameScreenSwitcher);

        OpenGameController openGameController =
                new OpenGameController(openGameInteractor);

        OpenGameView openGameView = new OpenGameView(openGameViewModel);

        openGameView.setOpenGameController(openGameController);

        BattlePresenter battlePresenter = new BattlePresenter(battleViewModel, moveViewModel, viewManagerModel);

        BattleInteractor battleInteractor = new BattleInteractor(gameDataAccess, battlePresenter);

        BattleView battleView = new BattleView(battleViewModel, quizViewModel);

        // Create Presenters
        LoadQuizOutputBoundary loadQuizPresenter = new LoadQuizPresenter(quizViewModel);
        SubmitQuizOutputBoundary submitQuizPresenter = new SubmitQuizPresenter(quizViewModel, battleViewModel, viewManagerModel);

        // Create Interactors (Use Cases)
        LoadQuizInputBoundary loadQuizInteractor = new LoadQuizInteractor(gameDataAccess, loadQuizPresenter);
        SubmitQuizInputBoundary submitQuizInteractor = new SubmitQuizInteractor(gameDataAccess, submitQuizPresenter);

        // Create Controller (inject BOTH interactors)
        QuizController quizController = new QuizController(submitQuizInteractor, loadQuizInteractor);
        BattleController battleController = new BattleController(battleInteractor, quizViewModel);

        battleView.setBattleController(battleController);
        QuizView quizView = new QuizView(quizViewModel);
        quizView.setQuizController(quizController);
        new QuizzesReader().loadQuizzes(gameDataAccess);
        QuizState quizState = new QuizState();
        quizView.loadQuiz(quizState.setQuizId());

        ResultsViewModel resultsViewModel = new ResultsViewModel();
        MoveStaticMapInterface mapService = new GeoapifyStaticMap();

        MovePresenter movePresenter = new MovePresenter(viewManagerModel, moveViewModel, mapService, battleViewModel);

        MoveInputBoundary moveInteractor = new MoveInteractor(gameDataAccess, movePresenter);
        MoveController moveController = new MoveController(moveInteractor);

        MoveView moveView = new MoveView(moveViewModel);
        moveView.setMoveController(moveController);

        ShowResultsPresenter resultsPresenter = new ShowResultsPresenter(resultsViewModel, moveViewModel, viewManagerModel);
        ShowResultsInputBoundary resultsInteractor = new ShowResultsInteractor(gameDataAccess, resultsPresenter);
        ShowResultsController resultsController = new ShowResultsController(resultsInteractor);
        ResultsView resultsView = new ResultsView(resultsViewModel);

        moveView.setResultController(resultsController);
        resultsView.setResultController(resultsController);

        views.add(openGameView, openGameView.getViewName());
        views.add(moveView, moveView.getViewName());
        views.add(resultsView, resultsView.getViewName());
        views.add(battleView, "Battle");
        views.add(quizView, "Quiz");


        viewManagerModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(evt.getPropertyName());
                if ("state".equals(evt.getPropertyName())) {
                    String viewName = (String) evt.getNewValue();
                    System.out.println("Switching view to: " + viewName);
                    cardLayout.show(views, viewName);


                    if ("Battle".equals(viewName)) {

                    }
                }
            }
        });
        viewManagerModel.setState("OpenGame");
        viewManagerModel.firePropertyChange();

        try {
            AdventureGame initialGame = gameDataAccess.getGame();
            boolean canMoveLeft = initialGame.canMove(Direction.LEFT);
            boolean canMoveRight = initialGame.canMove(Direction.RIGHT);
            Location loc = initialGame.getGameMap().getCurrentLocation();
            User user = initialGame.getUser();
            Monster mon = loc.getMonster();
            int index = initialGame.getGameMap().getCurrentLocationIndex();
            int size = initialGame.getGameMap().getMapSize();

            MoveOutputData initialOutputData = new MoveOutputData(
                    loc.getName(), loc.getLatitude(), loc.getLongitude(),
                    index, size, canMoveLeft, canMoveRight, mon
            );
            movePresenter.present(initialOutputData);


            BattleState battleState = battleViewModel.getState();
            battleState.setUser(user);
            battleState.setMonster(mon != null ? mon : new Monster());
            battleState.setBattleMessage("Battle System initialized.");
            battleViewModel.setState(battleState);
            battleViewModel.firePropertyChange();

        } catch (Exception e) {
            System.err.println("Error during initial game load!");
            e.printStackTrace();
        }


        application.add(views);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}