package use_case.move;

import API.MoveStaticMapInterface;
import entity.*;
import entity.AdventureGame;
import entity.Direction;
import entity.Location;

import javax.swing.*;

public class MoveInteractor implements MoveInputBoundary {

    private final MoveGameDataAccessInterface moveGameDataAccess;

    private final MoveOutputBoundary movePresenter;
    private final MoveStaticMapInterface mapService;

    public MoveInteractor(MoveGameDataAccessInterface moveGameDataAccess, MoveOutputBoundary movePresenter, MoveStaticMapInterface mapService) {
        this.moveGameDataAccess = moveGameDataAccess;
        this.movePresenter = movePresenter;
        this.mapService = mapService;
    }

    @Override
    public void execute(MoveInputData moveInputData) {
        Direction direction = moveInputData.getDirection();
        AdventureGame game = moveGameDataAccess.getGame();
        this.moveGameDataAccess.saveGame(game);
        boolean success = game.move(direction);
        if (!success) {
            System.out.println("Warning! Move unsuccess!");
        }

        updateGame();
    }

    public void updateGame() {
        AdventureGame game = moveGameDataAccess.getGame();
        boolean canMoveLeft = game.canMove(Direction.LEFT);
        boolean canMoveRight = game.canMove(Direction.RIGHT);

        Location currentLocation = game.getGameMap().getCurrentLocation();
        String locationName = currentLocation.getName();

        ImageIcon mapImage = mapService.getMapImage(
                currentLocation.getLatitude(),
                currentLocation.getLongitude()
        );

        int currentIndex = game.getGameMap().getCurrentLocationIndex();
        int mapSize = game.getGameMap().getMapSize();

        Monster monster = currentLocation.getMonster();
        Item item = currentLocation.getItem();
        MoveOutputData outputData = new MoveOutputData(
                locationName,
                currentIndex,
                mapSize,
                canMoveLeft,
                canMoveRight,
                monster,
                item,
                mapImage
        );

        this.movePresenter.present(outputData);
    }

    @Override
    public void switchToBattleView(Monster monster) {
        movePresenter.switchToBattleView(moveGameDataAccess.getGame().getUser(), monster);
    }
}
