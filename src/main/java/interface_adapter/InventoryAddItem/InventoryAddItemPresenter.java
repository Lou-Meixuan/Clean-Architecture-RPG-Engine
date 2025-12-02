package interface_adapter.InventoryAddItem;

import entity.Item;
import interface_adapter.move.MoveState;
import interface_adapter.move.MoveViewModel;
import use_case.InventoryAddItem.InventoryAddItemOutputBoundary;
import use_case.InventoryAddItem.InventoryAddItemOutputData;

/**
 * Presenter for AddItem Usecase
 * updates viewModel state with added item.
 */

public class InventoryAddItemPresenter implements InventoryAddItemOutputBoundary {
    private final InventoryAddItemViewModel viewModel;
    private final MoveViewModel moveViewModel;

    public InventoryAddItemPresenter(InventoryAddItemViewModel viewModel, MoveViewModel moveViewModel) {
        this.viewModel = viewModel;
        this.moveViewModel = moveViewModel;
    }

    @Override
    public void present(InventoryAddItemOutputData outputData) {
        Item item = outputData.getItem();
        viewModel.getState().setAddedItem(item);

        // Also update the move view model to reflect the item has been removed from the map
        MoveState moveState = moveViewModel.getState();
        moveState.setNeedUpdate(true);
        moveViewModel.firePropertyChange();
    }
}
