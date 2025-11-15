package Adventure_Game.use_case.move;

import Adventure_Game.entity.AdventureGame;
import Adventure_Game.entity.Direction;
import Adventure_Game.entity.Location;
import Battle_System.User.Monster;

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

        Monster monster = currentLocation.getMonster();
        // Item item = currentLocation.getItem();
        MoveOutputData outputData = new MoveOutputData(
                locationName,
                latitude,
                longitude,
                canMoveLeft,
                canMoveRight,
                monster
                // , ITEM
        );

        this.movePresenter.present(outputData);
    }
}
