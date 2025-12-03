package use_case.open_game;

public class OpenGameInteractor implements OpenGameInputBoundary {

    private final OpenGameOutputBoundary presenter;
    private final OpenGameDataAccessInterface dataAccess;

    public OpenGameInteractor(OpenGameOutputBoundary presenter,
                              OpenGameDataAccessInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }


    @Override
    public void execute(OpenGameInputData inputData) {

        // CASE 1: START NEW GAME
        if (inputData.isNewGame()) {

            if (dataAccess.saveFileExists()) {
                presenter.prepareFailView("A saved game already exists. Please click 'Continue Game'.");
                return;
            }

            presenter.prepareSuccessView(
                    new OpenGameOutputData("New game started!" )
            );

//            screenSwitcher.switchToMoveScreen();
            presenter.switchToMoveScreen();
            return;
        }

        // CASE 2: CONTINUE GAME
        if (!dataAccess.saveFileExists()) {
            presenter.prepareFailView("No saved game found. Please start a new game.");
            return;
        }

        presenter.prepareSuccessView(
                new OpenGameOutputData("Game loaded!")
        );

        //screenSwitcher.switchToMoveScreen();
        presenter.switchToMoveScreen();
    }

    @Override
    public void switchToMoveScreen() {
//        screenSwitcher.switchToMoveScreen();
        presenter.switchToMoveScreen();
    }
}
