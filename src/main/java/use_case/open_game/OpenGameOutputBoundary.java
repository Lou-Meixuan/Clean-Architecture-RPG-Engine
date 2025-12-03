package use_case.open_game;

public interface OpenGameOutputBoundary {
    void prepareSuccessView(OpenGameOutputData outputData);
    void prepareFailView(String errorMessage);
    void switchToMoveScreen();
}