package Battle_System;

import Battle_System.DataAccess.InMemoryBattleDataAccess;
import Battle_System.Entity.Monster;
import Battle_System.Entity.User;
import Battle_System.Interface_Adapter.Battle.Battle_Controller;
import Battle_System.Interface_Adapter.Battle.Battle_Presenter;
import Battle_System.Interface_Adapter.Battle.Battle_State;
import Battle_System.Interface_Adapter.Battle.Battle_ViewModel;
import Battle_System.Interface_Adapter.ViewManagerModel;
import Battle_System.UseCase.Battle.Battle_Interactor;
import Battle_System.View.Battle_View;
import Battle_System.View.Quiz_View;
import Battle_System.View.Quiz_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        ViewManagerModel viewManagerModel = new ViewManagerModel("Battle");
        Battle_ViewModel battleViewModel = new Battle_ViewModel();
        Quiz_ViewModel quizViewModel = new Quiz_ViewModel();

        // Initialize Data Access
        InMemoryBattleDataAccess battleDataAccess = new InMemoryBattleDataAccess();

        // Initialize Presenter
        Battle_Presenter battlePresenter = new Battle_Presenter(battleViewModel, viewManagerModel);

        // Initialize Interactor
        Battle_Interactor battleInteractor = new Battle_Interactor(battleDataAccess, battlePresenter);

        // Initialize Controller
        Battle_Controller battleController = new Battle_Controller(battleInteractor);

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

        // Initialize battle with test data
        User testUser = new User();
        Monster testMonster = new Monster();

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