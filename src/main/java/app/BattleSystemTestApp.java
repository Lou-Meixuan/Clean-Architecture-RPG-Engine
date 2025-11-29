
package app;

import data_access.FileGameDataAccessObject;
import data_access.InMemoryBattleDataAccess;
import entity.*;
import interface_adapter.Battle.Battle_Controller;
import interface_adapter.Battle.Battle_Presenter;
import interface_adapter.Battle.Battle_State;
import interface_adapter.Battle.Battle_ViewModel;
import interface_adapter.ViewManagerModel;
import use_case.Battle.Battle_Interactor;
import view.Battle_View;
import view.Quiz_View;
import interface_adapter.quiz.Quiz_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Main application to test Battle System with view switching.
 */
public class BattleSystemTestApp {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BattleSystemTestApp app = new BattleSystemTestApp();
            app.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        // Create the main frame
        frame = new JFrame("Battle System Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create CardLayout for view switching
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize ViewModels
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        Battle_ViewModel battleViewModel = new Battle_ViewModel();
        Quiz_ViewModel quizViewModel = new Quiz_ViewModel();

        // Initialize Data Access with proper game data
        FileGameDataAccessObject gameDataAccess = new FileGameDataAccessObject();

        // Create or load game data
        AdventureGame game = gameDataAccess.getGame();
        if (game == null) {
            // Create a new game with test data
            User testUser = new User();
            Monster testMonster = new Monster();

            // Create a location with the monster
            Location testLocation = new Location("Test Battle Location", 0.0, 0.0, testMonster);
            List<Location> locations = new ArrayList<>();
            locations.add(testLocation);

            GameMap gameMap = new GameMap(locations, 0);
            List<Location> pathHistory = new LinkedList<>();
            pathHistory.add(testLocation);

            game = new AdventureGame(testUser, gameMap, pathHistory);
            gameDataAccess.saveGame(game);
        }

        InMemoryBattleDataAccess battleDataAccess = new InMemoryBattleDataAccess();
        battleDataAccess.setGameDataAccess(gameDataAccess);

        // Initialize Presenter
        Battle_Presenter battlePresenter = new Battle_Presenter(battleViewModel, viewManagerModel);

        // Initialize Interactor
        Battle_Interactor battleInteractor = new Battle_Interactor(battleDataAccess, battlePresenter);

        // Initialize Controller
        Battle_Controller battleController = new Battle_Controller(battleInteractor, quizViewModel);

        // Create Views
        Battle_View battleView = new Battle_View(battleViewModel, viewManagerModel, quizViewModel);
        battleView.setBattleController(battleController);

        Quiz_View quizView = new Quiz_View(quizViewModel, viewManagerModel);
        quizView.setBattleController(battleController);

        // Add views to CardLayout
        cardPanel.add(battleView, "Battle");
        cardPanel.add(quizView, "Quiz");

        // Add ViewManagerModel listener to switch views
        viewManagerModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())) {
                    String viewName = (String) evt.getNewValue();
                    cardLayout.show(cardPanel, viewName);

                    // Re-enable attack button when returning to battle view
                    if ("Battle".equals(viewName)) {
                        battleView.getComponent(0); // Refresh view
                    }
                }
            }
        });

        // Initialize battle with test data from the game
        User testUser = game.getUser();
        Monster testMonster = game.getGameMap().getCurrentLocation().getMonster();

        // If no monster at current location, create one for testing
        if (testMonster == null) {
            testMonster = new Monster();
            game.getGameMap().getCurrentLocation().setMonster(testMonster);
            gameDataAccess.saveGame(game);
        }

        Battle_State battleState = battleViewModel.getState();
        battleState.setUser(testUser);
        battleState.setMonster(testMonster);
        battleState.setBattleMessage("Battle begins! Click Attack to start!");
        battleViewModel.firePropertyChange();

        // Add card panel to frame
        frame.add(cardPanel);

        // Show the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Show Battle view first
        cardLayout.show(cardPanel, "Battle");
    }
}