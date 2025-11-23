package adventure_game.use_case.move;

import adventure_game.entity.AdventureGame;
import adventure_game.entity.Direction;
import adventure_game.entity.Location;
import Battle_System.Entity.Monster;

public class MoveInteractor implements MoveInputBoundary {

    private final MoveGameDataAccessInterface moveGameDataAccess;

    private final MoveOutputBoundary movePresenter;

    public MoveInteractor(MoveGameDataAccessInterface moveGameDataAccess, MoveOutputBoundary movePresenter) {
        this.moveGameDataAccess = moveGameDataAccess;
        this.movePresenter = movePresenter;
    }

    @Override
    public void execute(MoveInputData moveInputData) {
        Direction direction = moveInputData.getDirection();
        AdventureGame game = moveGameDataAccess.getGame();
        boolean success = game.move(direction);
        if (success) {
            this.moveGameDataAccess.saveGame(game);
        }

        boolean canMoveLeft = game.canMove(Direction.LEFT);
        boolean canMoveRight = game.canMove(Direction.RIGHT);

        Location currentLocation = game.getGameMap().getCurrentLocation();
        String locationName = currentLocation.getName();
        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();

        int currentIndex = game.getGameMap().getCurrentLocationIndex();
        int mapSize = game.getGameMap().getMapSize();

        Monster monster = currentLocation.getMonster();
        // Item item = currentLocation.getItem();
        MoveOutputData outputData = new MoveOutputData(
                locationName,
                latitude,
                longitude,
                currentIndex,
                mapSize,
                canMoveLeft,
                canMoveRight,
                monster
                // , ITEM
        );

        this.movePresenter.present(outputData);
    }
}
