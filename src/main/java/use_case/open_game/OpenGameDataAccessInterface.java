package use_case.open_game;

public interface OpenGameDataAccessInterface {
    //    GameState loadGame();
//    void saveGame(GameState state);
    boolean saveFileExists();
    void deleteSaveFile();

}
