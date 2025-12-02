package data_access;

import use_case.move.MoveGameDataAccessInterface;

/**
 * Production factory implementation using file-based storage.
 * Creates FileGameDataAccessObject instances for data persistence.
 */
public class FileDataAccessFactory implements GameDataAccessFactory {
    private FileGameDataAccessObject gameDataAccess;

    @Override
    public MoveGameDataAccessInterface createGameDataAccess() {
        if (gameDataAccess == null) {
            gameDataAccess = new FileGameDataAccessObject();
        }
        return gameDataAccess;
    }
}
