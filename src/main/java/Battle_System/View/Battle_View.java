package Battle_System.View;

import Battle_System.Interface_Adapter.Battle.Battle_Controller;
import Battle_System.Interface_Adapter.Battle.Battle_State;
import Battle_System.Interface_Adapter.Battle.Battle_ViewModel;
import Battle_System.Interface_Adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Battle Use Case.
 * Displays the battle interface and handles user interactions.
 */
public class Battle_View extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Battle";
    private final Battle_ViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private Battle_Controller battleController;

    private final Quiz_ViewModel quizViewModel;

    // UI Components
    private final JLabel titleLabel;
    private final JLabel userHpLabel;
    private final JLabel monsterNameLabel;
    private final JLabel monsterHpLabel;
    private final JTextArea battleMessageArea;
    private final JButton attackButton;


    public Battle_View(Battle_ViewModel battleViewModel, ViewManagerModel viewManagerModel, Quiz_ViewModel quizViewModel) {
        this.viewModel = battleViewModel;
        this.viewManagerModel = viewManagerModel;
        this.quizViewModel = quizViewModel;
        this.viewModel.addPropertyChangeListener(this);

        // Initialize UI components
        titleLabel = new JLabel("Battle", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        userHpLabel = new JLabel("HP: N/A");
        monsterNameLabel = new JLabel("Monster: N/A");
        monsterHpLabel = new JLabel("HP: N/A");

        battleMessageArea = new JTextArea(5, 30);
        battleMessageArea.setEditable(false);
        battleMessageArea.setLineWrap(true);
        battleMessageArea.setWrapStyleWord(true);
        battleMessageArea.setText("Battle is ready to begin...");

        attackButton = new JButton("Attack");

        // Add action listeners
        attackButton.addActionListener(this);

        // Layout setup
        setupLayout();
    }

    /**
     * Sets up the layout of the view.
     */
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Center panel with battle info
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // User info panel
        JPanel userPanel = new JPanel(new GridLayout(2, 1));
        userPanel.setBorder(BorderFactory.createTitledBorder("Player"));
        userPanel.add(userHpLabel);

        // Monster info panel
        JPanel monsterPanel = new JPanel(new GridLayout(2, 1));
        monsterPanel.setBorder(BorderFactory.createTitledBorder("Monster"));
        monsterPanel.add(monsterNameLabel);
        monsterPanel.add(monsterHpLabel);

        // Battle message panel
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createTitledBorder("Battle Log"));
        JScrollPane scrollPane = new JScrollPane(battleMessageArea);
        messagePanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(userPanel);
        centerPanel.add(monsterPanel);
        centerPanel.add(messagePanel);

        add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(attackButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the controller for this view.
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
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == attackButton) {
            handleAttackButton();
        }
    }

    /**
     * Handles the attack button click.
     */
    private void handleAttackButton() {
        Battle_State state = viewModel.getState();

        if (state.isBattleEnded()) {
            JOptionPane.showMessageDialog(this,
                    "Battle has already ended!",
                    "Battle Over",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (battleController != null && state.getUser() != null && state.getMonster() != null) {
            // Disable attack button
            attackButton.setEnabled(false);

            // Debug: Check what we're passing
            System.out.println("Battle_View - Switching to Quiz");
            System.out.println("User: " + state.getUser());
            System.out.println("Monster: " + state.getMonster());

            // Pass user and monster to Quiz state
            quizViewModel.getState().setUser(state.getUser());
            quizViewModel.getState().setMonster(state.getMonster());

            // Debug: Verify quiz state
            System.out.println("Quiz State User: " + quizViewModel.getState().getUser());
            System.out.println("Quiz State Monster: " + quizViewModel.getState().getMonster());

            // Switch to Quiz view
            viewManagerModel.setState("Quiz");
            viewManagerModel.firePropertyChange();
            // Buttons will be re-enabled in propertyChange if battle continues
        } else {
            JOptionPane.showMessageDialog(this,
                    "Battle is not properly initialized!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            Battle_State state = viewModel.getState();
            updateDisplay(state);
        }
    }

    /**
     * Updates all display elements based on the current state.
     */
    private void updateDisplay(Battle_State state) {
        // Update user info
        if (state.getUser() != null) {
            userHpLabel.setText(String.format("HP: %.1f", state.getUserHp()));
        }

        // Update monster info
        if (state.getMonster() != null) {
            monsterNameLabel.setText("Monster: " + state.getMonsterName());
            monsterHpLabel.setText(String.format("HP: %.1f", state.getMonsterHP()));
        }

        // Update battle message
        if (!state.getBattleMessage().isEmpty()) {
            battleMessageArea.append("\n" + state.getBattleMessage());

            // Auto-scroll to bottom
            battleMessageArea.setCaretPosition(battleMessageArea.getDocument().getLength());
        }

        // Handle battle end
        if (state.isBattleEnded()) {
            attackButton.setEnabled(false);
            // Show result dialog
            String title = state.isUserWon() ? "Victory!" : "Defeat!";
            int messageType = state.isUserWon() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE;

            JOptionPane.showMessageDialog(this,
                    state.getBattleMessage(),
                    title,
                    messageType);
        } else {
            // Battle continues - re-enable buttons
            attackButton.setEnabled(true);
        }
    }
}



