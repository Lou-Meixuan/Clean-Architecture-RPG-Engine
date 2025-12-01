package interface_adapter.ShowResults;

import interface_adapter.ViewModel;

/**
 * ViewModel for the results screen.
 */
public class ShowResultsViewModel extends ViewModel<ShowResultsState> {
    public static final String TITLE_LABEL = "Game Results";
    public static final String BACK_BUTTON_LABEL = "Play Again";

    public ShowResultsViewModel() {
        super("results");
        setState(new ShowResultsState());
    }
}
