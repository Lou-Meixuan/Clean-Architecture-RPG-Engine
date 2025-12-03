package view;

import entity.Direction;
import entity.Item;
import interface_adapter.inventory_addItem.InventoryAddItemController;
import interface_adapter.move.MoveController;
import interface_adapter.move.MoveState;
import interface_adapter.move.MoveViewModel;
import interface_adapter.results.ShowResultsController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MoveView extends JPanel implements PropertyChangeListener {
    public static final String LEFT_BUTTON_LABEL = "Go Left";
    public static final String RIGHT_BUTTON_LABEL = "Go Right";
    private final String viewName = "move";
    private final MoveViewModel moveViewModel;
    private MoveController moveController;
    private ShowResultsController resultsController;
    private InventoryAddItemController inventoryAddItemController;

    private final JLabel linearMapLabel;
    private final JLabel staticMapImageLabel;
    private final JLabel currentLocationLabel;
    private final JButton goLeftButton;
    private final JButton goRightButton;
    private final JButton pickUpButton;
    private final JButton endGameButton;

    public MoveView(MoveViewModel moveViewModel) {
        this.moveViewModel = moveViewModel;
        this.moveController = null;
        this.resultsController = null;
        this.inventoryAddItemController = null;

        this.moveViewModel.addPropertyChangeListener(this);

        linearMapLabel = new JLabel("Loading Map...");
        linearMapLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        linearMapLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        staticMapImageLabel = new JLabel();
        staticMapImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        staticMapImageLabel.setMinimumSize(new Dimension(300, 200));
        staticMapImageLabel.setPreferredSize(new Dimension(300, 200));
        staticMapImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        currentLocationLabel = new JLabel("Loading location...");
        currentLocationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();

        goLeftButton = new JButton(LEFT_BUTTON_LABEL);
        goRightButton = new JButton(RIGHT_BUTTON_LABEL);
        pickUpButton = new JButton("Pick Item");
        endGameButton = new JButton("End Game");
        endGameButton.setVisible(false);
        buttonPanel.add(goLeftButton);
        buttonPanel.add(goRightButton);
        buttonPanel.add(pickUpButton);
        buttonPanel.add(endGameButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.add(linearMapLabel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(staticMapImageLabel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(currentLocationLabel);
        this.add(buttonPanel);

        goLeftButton.addActionListener(e -> {
            MoveState state = moveViewModel.getState();
            state.setJustReturnedFromDefeat(false);
            moveController.execute(Direction.LEFT);
        });

        goRightButton.addActionListener(e -> {
            MoveState state = moveViewModel.getState();
            state.setJustReturnedFromDefeat(false);
            moveController.execute(Direction.RIGHT);
        });

        pickUpButton.addActionListener(
                e -> {
                    MoveState state = moveViewModel.getState();
                    Item item = state.getItem();

                    if (item != null && inventoryAddItemController != null) {
                        // 1. Add item to inventory
                        inventoryAddItemController.addItem(item);

                        JOptionPane.showMessageDialog(this, "You picked up: " + item.getName());
                    }
                }
        );

        endGameButton.addActionListener(e -> {
            resultsController.execute();
            moveController.updateGame();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            MoveState state = (MoveState) evt.getNewValue();

            if (state.getNeedUpdate()) {
                state.setNeedUpdate(false);
                moveController.updateGame();
                return;
            }

            linearMapLabel.setText(state.getLinearMap());

            byte[] imageData = state.getStaticMapImageData();
            if (imageData != null) {
                staticMapImageLabel.setIcon(new ImageIcon(imageData));
            }

            currentLocationLabel.setText("Current Location: " + state.getCurrentLocationName());

            goLeftButton.setEnabled(state.isLeftButtonEnabled());
            goRightButton.setEnabled(state.isRightButtonEnabled());

            // Show End Game button only when at the last location
            endGameButton.setVisible(!state.isRightButtonEnabled());

            if (state.getMonster() != null && state.getMonster().isAlive()) {
                if (state.isJustReturnedFromDefeat()) {
                    state.setJustReturnedFromDefeat(false);
                } else {
                    moveController.switchToBattleView(state.getMonster());
                }
            }

            pickUpButton.setVisible(state.isItemPickupable());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setMoveController(MoveController moveController) {
        this.moveController = moveController;
    }

    public void setResultController(ShowResultsController resultsController) {
        this.resultsController = resultsController;
    }

    public void setInventoryAddItemController(InventoryAddItemController inventoryAddItemController) {
        this.inventoryAddItemController = inventoryAddItemController;
    }
}
