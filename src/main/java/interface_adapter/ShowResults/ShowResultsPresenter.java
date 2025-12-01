package interface_adapter.ShowResults;

import use_case.show_results.ShowResultsOutputBoundary;
import use_case.show_results.ShowResultsOutputData;

/**
 * Presenter for showing results screen.
 */
public class ShowResultsPresenter implements ShowResultsOutputBoundary {
    private final ShowResultsViewModel showResultsViewModel;

    public ShowResultsPresenter(ShowResultsViewModel showResultsViewModel) {
        this.showResultsViewModel = showResultsViewModel;
    }

    @Override
    public void prepareSuccessView(ShowResultsOutputData outputData) {
        final ShowResultsState state = showResultsViewModel.getState();
        state.setUserName(outputData.getUserName());
        state.setTotalMoves(outputData.getTotalMoves());
        state.setPathHistory(outputData.getPathHistory());
        state.setFinalLocation(outputData.getFinalLocation());

        showResultsViewModel.firePropertyChange();
    }
}
