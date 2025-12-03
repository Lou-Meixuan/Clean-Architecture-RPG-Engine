package interface_adapter.inventory_useItem;

import interface_adapter.battle.BattleState;
import interface_adapter.battle.BattleViewModel;
import use_case.inventory_useItem.InventoryUseItemOutputData;
import use_case.inventory_useItem.InventoryUseItemOutputBoundary;
import use_case.inventory_useItem.InventoryUseItemOutputData.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class InventoryUseItemPresenter implements InventoryUseItemOutputBoundary {
    private final InventoryUseItemViewModel viewModel;
    private final BattleViewModel battleViewModel;

    public InventoryUseItemPresenter(InventoryUseItemViewModel viewModel, BattleViewModel battleViewModel) {
        this.viewModel = viewModel;
        this.battleViewModel = battleViewModel;
    }

    @Override
    public void useItem(InventoryUseItemOutputData outputData) {
        InventoryUseItemState state = viewModel.getState();

        if (!outputData.isSuccess()) {
            // Handle error case
            state.setMessage("Error: " + outputData.getErrorMessage());
            state.setHpIncrease(0);
            state.setDefIncrease(0);
            state.setDmgIncrease(0);
            viewModel.firePropertyChange();
            return;
        }

        // Set stat increases
        state.setHpIncrease(outputData.getHpIncrease());
        state.setDefIncrease(outputData.getDefIncrease());
        state.setDmgIncrease(outputData.getDmgIncrease());

        // Convert DTOs to simple lists for the view
        List<String> names = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (ItemDTO item : outputData.getItems()) {
            names.add(item.getName());
            types.add(item.getType());
            values.add(item.getValue());
        }

        state.setItemNames(names);
        state.setItemTypes(types);
        state.setItemValues(values);

        // Build success message
        StringBuilder message = new StringBuilder("Item used!");
        if (outputData.getHpIncrease() > 0) {
            message.append(" +").append(outputData.getHpIncrease()).append(" HP");
        }
        if (outputData.getDefIncrease() > 0) {
            message.append(" +").append(outputData.getDefIncrease()).append(" DEF");
        }
        if (outputData.getDmgIncrease() > 0) {
            message.append(" +").append(outputData.getDmgIncrease()).append(" DMG");
        }

        state.setMessage(message.toString());
        viewModel.firePropertyChange();

        if (battleViewModel != null) {
            BattleState battleState = battleViewModel.getState();
            if (battleState.getUser() != null) {
                double newHp = battleState.getUserHp() + outputData.getHpIncrease();
                battleState.setUserHP(newHp);
                battleState.getUser().setHP(newHp);
                battleViewModel.firePropertyChange();
            }
        }
    }

    @Override
    public void viewInventory(InventoryUseItemOutputData outputData) {
        InventoryUseItemState state = viewModel.getState();

        if (!outputData.isSuccess()) {
            // Handle error case
            state.setMessage("Error: " + outputData.getErrorMessage());
            state.setItemNames(new ArrayList<>());
            state.setItemTypes(new ArrayList<>());
            state.setItemValues(new ArrayList<>());
            viewModel.firePropertyChange();
            return;
        }

        // Convert DTOs to simple lists for the view
        List<String> names = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (ItemDTO item : outputData.getItems()) {
            names.add(item.getName());
            types.add(item.getType());
            values.add(item.getValue());
        }

        state.setItemNames(names);
        state.setItemTypes(types);
        state.setItemValues(values);
        state.setMessage("Inventory loaded");

        viewModel.firePropertyChange();
    }
}