package interface_adapter.opengame;
import interface_adapter.ViewManagerModel;
import interface_adapter.move.MoveState;
import interface_adapter.move.MoveViewModel;
import use_case.openGame.ScreenSwitchBoundary;


public class OpenGameScreenSwitcher implements ScreenSwitchBoundary {
    private final ViewManagerModel viewManager;
    private final MoveViewModel moveViewModel;

    public OpenGameScreenSwitcher(MoveViewModel moveViewModel, ViewManagerModel viewManager) {
        this.viewManager = viewManager;
        this.moveViewModel = moveViewModel;
    }

    @Override
    public void switchToMoveScreen() {
        viewManager.setState("move"); // MUST match MoveView.getViewName()
        viewManager.firePropertyChange();

        MoveState moveState = moveViewModel.getState();
        moveState.setNeedUpdate(true);
        moveViewModel.firePropertyChange();
    }

    @Override
    public void switchToResultScreen() {
        viewManager.setState("Results");
        viewManager.firePropertyChange();
    }

}
