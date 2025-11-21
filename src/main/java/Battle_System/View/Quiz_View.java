package Battle_System.View;

import Battle_System.Interface_Adapter.Battle.Battle_Controller;
import Battle_System.Interface_Adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Quiz Use Case.
 * Displays a simple quiz with True and False buttons.
 */
public class Quiz_View extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Quiz";
    private final Quiz_ViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private Battle_Controller battleController;

    // UI Components
    private final JLabel titleLabel;
    private final JLabel questionLabel;
    private final JButton trueButton;
    private final JButton falseButton;

    public Quiz_View(Quiz_ViewModel quizViewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = quizViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);

        // Initialize UI components
        titleLabel = new JLabel("Quiz Time!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        questionLabel = new JLabel("Answer the question:", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        trueButton = new JButton("TRUE");
        trueButton.setFont(new Font("Arial", Font.BOLD, 16));
        trueButton.setPreferredSize(new Dimension(150, 50));

        falseButton = new JButton("FALSE");
        falseButton.setFont(new Font("Arial", Font.BOLD, 16));
        falseButton.setPreferredSize(new Dimension(150, 50));

        // Add action listeners
        trueButton.addActionListener(this);
        falseButton.addActionListener(this);

        // Layout setup
        setupLayout();
    }

    /**
     * Sets up the layout of the view.
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Question panel
        JPanel questionPanel = new JPanel();
        questionPanel.add(questionLabel);
        add(questionPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.add(trueButton);
        buttonPanel.add(falseButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the battle controller for this view.
     */
    public void setBattleController(Battle_Controller controller) {
        this.battleController = controller;
    }

    /**
     * Returns the view name.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Quiz_State state = viewModel.getState();

        if (e.getSource() == trueButton) {
            handleAnswer(true, state);
        } else if (e.getSource() == falseButton) {
            handleAnswer(false, state);
        }
    }

    /**
     * Handles the answer button click.
     */
    private void handleAnswer(boolean answer, Quiz_State state) {
        // Disable buttons after selection
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);

        // Update state with quiz result
        state.setQuizResult(answer);

        // Debug: Check what we have
        System.out.println("Quiz_View - handleAnswer called");
        System.out.println("battleController: " + battleController);
        System.out.println("user: " + state.getUser());
        System.out.println("monster: " + state.getMonster());

        // Call battle controller to execute battle with quiz result
        if (battleController == null) {
            JOptionPane.showMessageDialog(this,
                    "Battle controller is not set!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            trueButton.setEnabled(true);
            falseButton.setEnabled(true);
            return;
        }

        if (state.getUser() == null || state.getMonster() == null) {
            JOptionPane.showMessageDialog(this,
                    "User or Monster is null!\nUser: " + state.getUser() + "\nMonster: " + state.getMonster(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            trueButton.setEnabled(true);
            falseButton.setEnabled(true);
            return;
        }

        // Execute battle with quiz result
        battleController.execute(state.getUser(), state.getMonster(), answer);

        // Switch back to Battle view
        viewManagerModel.setState("Battle");
        viewManagerModel.firePropertyChange();

        // Re-enable buttons for next quiz
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
    }

    /**
     * This method gets called when a bound property is changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            Quiz_State state = viewModel.getState();
            // Can update UI based on state if needed
        }
    }
}