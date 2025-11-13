package Battle_System.Interface_Adapter.Battle;

import Battle_System.Interface_Adapter.ViewManagerModel;
import Battle_System.Interface_Adapter.ViewModel;
import Battle_System.UseCase.Battle.Battle_OutputBoundary;
import Battle_System.UseCase.Battle.Battle_OutputData;

public class Battle_Presenter implements Battle_OutputBoundary {
    private final ViewModel viewModel;
    //private final Moving_ViewModel moving_ViewModel;
    private final ViewManagerModel viewManagerModel ;

    public Battle_Presenter(ViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the Win view for the Battle Use Case.
     *
     * @param outputData the output data
     */
    @Override
    public void prepareWinView(Battle_OutputData outputData) {
    }

    /**
     * Prepares the Loss view for the Battle Use Case.
     *
     * @param outputData the output data
     */
    @Override
    public void prepareLossView(Battle_OutputData outputData) {
    }
}
