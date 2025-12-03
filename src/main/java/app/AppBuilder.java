package app;

import api.GeoapifyStaticMap;
import api.MoveStaticMapInterface;
import data_access.FileGameDataAccessObject;
import data_access.GameDataAccessFactory;
import data_access.QuizzesReader;
import interface_adapter.battle.BattleController;
import interface_adapter.battle.BattlePresenter;
import interface_adapter.battle.BattleViewModel;
import interface_adapter.inventory_addItem.InventoryAddItemController;
import interface_adapter.inventory_addItem.InventoryAddItemPresenter;
import interface_adapter.inventory_addItem.InventoryAddItemViewModel;
import interface_adapter.inventory_useItem.InventoryUseItemController;
import interface_adapter.inventory_useItem.InventoryUseItemPresenter;
import interface_adapter.inventory_useItem.InventoryUseItemViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.move.MoveController;
import interface_adapter.move.MovePresenter;
import interface_adapter.move.MoveViewModel;
import interface_adapter.open_game.OpenGameController;
import interface_adapter.open_game.OpenGamePresenter;
import interface_adapter.open_game.OpenGameViewModel;
import interface_adapter.quiz.*;
import interface_adapter.results.ResultsViewModel;
import interface_adapter.results.ShowResultsController;
import interface_adapter.results.ShowResultsPresenter;
import use_case.battle.BattleInputBoundary;
import use_case.battle.BattleInteractor;
import use_case.battle.BattleOutputBoundary;
import use_case.inventory_addItem.InventoryAddItemInputBoundary;
import use_case.inventory_addItem.InventoryAddItemInteractor;
import use_case.inventory_addItem.InventoryAddItemOutputBoundary;
import use_case.inventory_useItem.InventoryUseItemInputBoundary;
import use_case.inventory_useItem.InventoryUseItemInteractor;
import use_case.inventory_useItem.InventoryUseItemOutputBoundary;
import use_case.load_quiz.LoadQuizInputBoundary;
import use_case.load_quiz.LoadQuizInteractor;
import use_case.load_quiz.LoadQuizOutputBoundary;
import use_case.move.MoveInputBoundary;
import use_case.move.MoveInteractor;
import use_case.move.MoveOutputBoundary;
import use_case.open_game.*;
import use_case.submit_quiz.SubmitQuizInputBoundary;
import use_case.submit_quiz.SubmitQuizInteractor;
import use_case.submit_quiz.SubmitQuizOutputBoundary;
import use_case.show_results.ShowResultsInputBoundary;
import use_case.show_results.ShowResultsInteractor;
import use_case.show_results.ShowResultsOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel views = new JPanel(cardLayout);
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Factory for creating data access objects - supports dependency injection
    private final GameDataAccessFactory dataAccessFactory;

    // DAO created by factory
    private final FileGameDataAccessObject gameDataAccess;

    private BattleView battleView;
    private BattleViewModel battleViewModel;
    private MoveView moveView;
    private InventoryView inventoryView;
    private MoveViewModel moveViewModel;
    private OpenGameView openGameView;
    private OpenGameViewModel openGameViewModel;
    private QuizView quizView;
    private QuizViewModel quizViewModel;
    private ResultsView resultsView;
    private ResultsViewModel resultsViewModel;
    private ItemView itemView;
    private InventoryAddItemViewModel inventoryAddItemViewModel;
    private InventoryUseItemViewModel inventoryUseItemViewModel;

    public AppBuilder(GameDataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
        this.gameDataAccess = (FileGameDataAccessObject) dataAccessFactory.createGameDataAccess();
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addBattleView() {
        battleViewModel = new BattleViewModel();
        battleView = new BattleView(battleViewModel, inventoryView);
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
        openGameView = new OpenGameView(openGameViewModel);
        cardPanel.add(openGameView, openGameView.getViewName());
        return this;
    }

    public AppBuilder addQuizView() {
        quizViewModel = new QuizViewModel();
        quizView = new QuizView(quizViewModel);
        cardPanel.add(quizView, quizView.getViewName());
        return this;
    }

    public AppBuilder addResultsView() {
        resultsViewModel = new ResultsViewModel();
        resultsView = new ResultsView(resultsViewModel);
        cardPanel.add(resultsView, resultsView.getViewName());
        return this;
    }

    public AppBuilder addAddInventoryView() {
        inventoryAddItemViewModel = new InventoryAddItemViewModel();
        itemView = new ItemView(inventoryAddItemViewModel);
        cardPanel.add(itemView, itemView.getViewName());
        return this;
    }

    public AppBuilder addUseInventoryView() {
        inventoryUseItemViewModel = new InventoryUseItemViewModel();
        inventoryView = new InventoryView(inventoryUseItemViewModel);
       // cardPanel.add(inventoryView, inventoryView.getViewName());
        return this;
    }

    public AppBuilder addBattleUseCase() {
        final BattleOutputBoundary battleOutputBoundary = new BattlePresenter(battleViewModel, moveViewModel, viewManagerModel, inventoryUseItemViewModel);
        final BattleInputBoundary battleInteractor = new BattleInteractor(
                gameDataAccess, battleOutputBoundary);

        BattleController controller = new BattleController(battleInteractor, quizViewModel);
        battleView.setBattleController(controller);
        return this;
    }

    public AppBuilder addMoveUseCase() {
        MoveStaticMapInterface mapService = new GeoapifyStaticMap();
        final MoveOutputBoundary moveOutputBoundary = new MovePresenter(viewManagerModel, moveViewModel,
                battleViewModel);
        final MoveInputBoundary moveInteractor = new MoveInteractor(
                gameDataAccess, moveOutputBoundary, mapService);

        MoveController controller = new MoveController(moveInteractor);
        moveView.setMoveController(controller);
        return this;
    }

    public AppBuilder addOpenGameUseCase() {
        final OpenGameOutputBoundary openGameOutputBoundary = new OpenGamePresenter(
                openGameViewModel, moveViewModel, viewManagerModel);
        final OpenGameInputBoundary openGameInteractor = new OpenGameInteractor(openGameOutputBoundary, gameDataAccess);

        OpenGameController controller = new OpenGameController(openGameInteractor);
        openGameView.setOpenGameController(controller);
        return this;
    }

    public AppBuilder addQuizUseCase() {
        final LoadQuizOutputBoundary loadQuizOutputBoundary = new LoadQuizPresenter(quizViewModel);
        final LoadQuizInputBoundary loadQuizInteractor = new LoadQuizInteractor(
                gameDataAccess, loadQuizOutputBoundary);
        final SubmitQuizOutputBoundary submitQuizOutputBoundary = new SubmitQuizPresenter(
                quizViewModel, battleViewModel, viewManagerModel);
        final SubmitQuizInputBoundary submitQuizInteractor = new SubmitQuizInteractor(
                gameDataAccess, submitQuizOutputBoundary);

        QuizController controller = new QuizController(submitQuizInteractor, loadQuizInteractor);
        quizView.setQuizController(controller);
        new QuizzesReader().loadQuizzes(gameDataAccess);
        QuizState quizState = new QuizState();
        quizView.loadQuiz(quizState.setQuizId());
        return this;
    }

    public AppBuilder addResultsUseCase() {
        final ShowResultsOutputBoundary showResultsOutputBoundary = new ShowResultsPresenter(resultsViewModel, moveViewModel, viewManagerModel);
        final ShowResultsInputBoundary showResultsInteractor = new ShowResultsInteractor(
                gameDataAccess, showResultsOutputBoundary);

        ShowResultsController controller = new ShowResultsController(showResultsInteractor);
        moveView.setResultController(controller);
        resultsView.setResultController(controller);
        return this;
    }

    public AppBuilder addAddInventoryUseCase() {
        final InventoryAddItemOutputBoundary inventoryAddItemOutputBoundary = new InventoryAddItemPresenter(
                inventoryAddItemViewModel, moveViewModel, inventoryUseItemViewModel);  // ← ADD the third parameter
        final InventoryAddItemInputBoundary inventoryAddItemInteractor = new InventoryAddItemInteractor(inventoryAddItemOutputBoundary, gameDataAccess);

        InventoryAddItemController controller = new InventoryAddItemController(inventoryAddItemInteractor);
        itemView.setController(controller);
        moveView.setInventoryAddItemController(controller);
        return this;
    }


    public AppBuilder addUseInventoryUseCase() {
        final InventoryUseItemOutputBoundary inventoryUseItemPresenter = new InventoryUseItemPresenter(
                inventoryUseItemViewModel, battleViewModel);
        final InventoryUseItemInputBoundary inventoryUseItemInteractor = new InventoryUseItemInteractor(
                inventoryUseItemPresenter, gameDataAccess);

        InventoryUseItemController controller = new InventoryUseItemController(inventoryUseItemInteractor);
        inventoryView.setController(controller);
        return this;
    }


    public JFrame build() {
        final JFrame application = new JFrame("Adventure & Battle Integrated System");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(1024, 768);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(openGameView.getViewName());
        viewManagerModel.firePropertyChange();


        return application;
    }

}
