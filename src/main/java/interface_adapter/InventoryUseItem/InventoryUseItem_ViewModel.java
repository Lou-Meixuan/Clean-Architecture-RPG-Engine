package interface_adapter.InventoryUseItem;
import interface_adapter.ViewModel;

/**
 * view model sets state of use item
 */

public class InventoryUseItem_ViewModel extends ViewModel<InventoryUseItem_State> {
    public InventoryUseItem_ViewModel( ) {
        super("InventoryUseItemState");
        setState(new InventoryUseItem_State());
    }
}

