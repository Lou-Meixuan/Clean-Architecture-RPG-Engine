package data_access;

import use_case.move.MoveGameDataAccessInterface;

/**
 * Test factory implementation for testing environments.
 * Can be configured to use in-memory DAOs or mock implementations.
 */
public class TestDataAccessFactory implements GameDataAccessFactory {
    private FileGameDataAccessObject gameDataAccess;

    @Override
    public MoveGameDataAccessInterface createGameDataAccess() {
        if (gameDataAccess == null) {
            gameDataAccess = new FileGameDataAccessObject();
        }
        return gameDataAccess;
    }
}
