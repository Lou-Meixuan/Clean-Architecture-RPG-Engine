package interface_adapter.opengame;

import interface_adapter.ViewManagerModel;
import interface_adapter.move.MoveState;
import interface_adapter.move.MoveViewModel;
import use_case.openGame.OpenGameOutputBoundary;
import use_case.openGame.OpenGameOutputData;

public class OpenGamePresenter implements OpenGameOutputBoundary {

    private final OpenGameViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final MoveViewModel moveViewModel;

    public OpenGamePresenter(OpenGameViewModel viewModel, MoveViewModel moveViewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.moveViewModel = moveViewModel;
    }

    @Override
    public void prepareSuccessView(OpenGameOutputData outputData) {
        // Update ViewModel
        viewModel.setMessage(outputData.getMessage());
        // viewModel.setState(outputData.getGameState());
        viewModel.firePropertyChange();   // Notify OpenGameView

        // Set up MoveView to update
        MoveState moveState = moveViewModel.getState();
        moveState.setNeedUpdate(true);
        moveViewModel.firePropertyChange();

        // Switch to Move screen
        viewManagerModel.setState("move");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage(errorMessage);
        viewModel.setState(null);
        viewModel.firePropertyChange();   // update UI (stays on same screen)
    }

    @Override
    public void switchToMoveScreen() {
        viewManagerModel.setState("move");
        // MoveView.getViewName()
        viewManagerModel.firePropertyChange();
        // Set up MoveView to update
        MoveState moveState = moveViewModel.getState();
        moveState.setNeedUpdate(true);
        moveViewModel.firePropertyChange();

        moveViewModel.firePropertyChange();
    }

    public OpenGameViewModel getViewModel() {
        return viewModel;
    }
}
