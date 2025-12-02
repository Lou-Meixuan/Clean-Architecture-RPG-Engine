package view;

import interface_adapter.InventoryUseItem.InventoryUseItemController;
import interface_adapter.InventoryUseItem.InventoryUseItemState;
import interface_adapter.InventoryUseItem.InventoryUseItemViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class InventoryView extends JPanel implements PropertyChangeListener {
    private final String viewName = "UseItem";
    private final InventoryUseItemViewModel viewModel;
    private InventoryUseItemController controller;

    // UI components
    private final JComboBox<String> inventoryDropdown = new JComboBox<>();
    private final JButton useItemButton = new JButton("Use Item");
    private final JLabel messageLabel = new JLabel(" ");

    public InventoryView(InventoryUseItemViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        // Layout
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Inventory"));
        setPreferredSize(new Dimension(250, 0));

        // Top panel: dropdown + button
        JPanel topPanel = new JPanel(new GridLayout(0, 1));
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Items:"));
        selectionPanel.add(inventoryDropdown);
        topPanel.add(selectionPanel);

        topPanel.add(useItemButton);

        // Use item button initially disabled
        useItemButton.setEnabled(false);

        // Dropdown selection listener
        inventoryDropdown.addActionListener(e -> {
            String selectedItemName = (String) inventoryDropdown.getSelectedItem();
            useItemButton.setEnabled(selectedItemName != null);
        });

        // Use item button action listener
        useItemButton.addActionListener(e -> {
            String selectedItemName = (String) inventoryDropdown.getSelectedItem();
            if (selectedItemName != null && controller != null) {
                controller.useItem(selectedItemName);
            }
        });

        // Message label styling
        messageLabel.setForeground(new Color(0, 128, 0));
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.BOLD));

        // Add to panel
        add(topPanel, BorderLayout.NORTH);
        add(messageLabel, BorderLayout.CENTER);

        if (inventoryDropdown.getItemCount() == 0) {
            messageLabel.setText("No items in inventory");
            messageLabel.setForeground(Color.GRAY);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(InventoryUseItemController controller) {
        this.controller = controller;
        // refresh inventory when controller is set
        if (controller != null) {
            controller.viewInventory();
        }
    }

    /**
     * Called when this view becomes visible
     * Auto-refreshes the inventory
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    /**
     * State updates the inventory (items list dropdown in view panel)
     */
    private void updateDropdown() {
        inventoryDropdown.removeAllItems();

        InventoryUseItemState state = viewModel.getState();
        List<String> itemNames = state.getItemNames();

        if (itemNames != null) {
            for (String itemName : itemNames) {

                inventoryDropdown.addItem(itemName);
            }
        }

        // If no items, disable button
        useItemButton.setEnabled(inventoryDropdown.getItemCount() > 0
                && inventoryDropdown.getSelectedItem() != null);
    }

    /**
     * Update message display (e.g., "Item used! +5 HP")
     */
    private void updateMessage() {
        InventoryUseItemState state = viewModel.getState();
        String message = state.getMessage();

        if (message != null && !message.isEmpty()) {
            // Set color based on message type
            if (message.startsWith("Error")) {
                messageLabel.setForeground(Color.RED);
            } else {
                messageLabel.setForeground(new Color(0, 128, 0));
            }
            messageLabel.setText(message);
        } else {
            messageLabel.setText(" ");  // Keep space
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        InventoryUseItemState state = viewModel.getState();

        if (state.getNeedsRefresh()) {
            state.setNeedsRefresh(false);
            if (controller != null) {
                controller.viewInventory();
            }
            return;
        }

        // Just update the UI from state
        updateDropdown();
        updateMessage();
    }




}