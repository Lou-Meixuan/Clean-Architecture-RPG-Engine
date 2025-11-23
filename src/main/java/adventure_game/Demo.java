package adventure_game;

import Battle_System.Entity.Monster;
import adventure_game.data_access.InMemoryUserDataAccessObject;
import adventure_game.game_api.GeoapifyStaticMap;
import adventure_game.entity.AdventureGame;
import adventure_game.entity.Direction;
import adventure_game.entity.Location;
import adventure_game.interface_adapter.move.MoveController;
import adventure_game.interface_adapter.move.MovePresenter;
import adventure_game.game_api.MoveStaticMapInterface;
import adventure_game.interface_adapter.move.MoveViewModel;
import adventure_game.use_case.move.MoveGameDataAccessInterface;
import adventure_game.use_case.move.MoveInputBoundary;
import adventure_game.use_case.move.MoveInteractor;
import adventure_game.use_case.move.MoveOutputData;
import adventure_game.view.MoveView;

import javax.swing.*;

public class Demo {
    public static void main(String[] args) {
        JFrame application = new JFrame("My Clean Architecture Adventure Game");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        MoveGameDataAccessInterface moveGameDataAccess = new InMemoryUserDataAccessObject();
        MoveStaticMapInterface mapService = new GeoapifyStaticMap();

        MoveViewModel moveViewModel = new MoveViewModel();
        MovePresenter movePresenter = new MovePresenter(moveViewModel, mapService);

        MoveInputBoundary moveInteractor = new MoveInteractor(
                moveGameDataAccess,
                movePresenter
        );

        MoveController moveController = new MoveController(
                moveInteractor
        );

        MoveView moveView = new MoveView(
                moveViewModel,
                moveController
        );


        try {
            AdventureGame initialGame = moveGameDataAccess.getGame();

            boolean canMoveLeft = initialGame.canMove(Direction.LEFT);
            boolean canMoveRight = initialGame.canMove(Direction.RIGHT);
            Location loc = initialGame.getGameMap().getCurrentLocation();
            Monster mon = loc.getMonster();

            // TODO:
//            Item item = null; // loc.getItem();

            int index = initialGame.getGameMap().getCurrentLocationIndex();
            int size = initialGame.getGameMap().getMapSize();

            MoveOutputData initialOutputData = new MoveOutputData(
                    loc.getName(),
                    loc.getLatitude(),
                    loc.getLongitude(),
                    index,
                    size,
                    canMoveLeft,
                    canMoveRight,
                    mon
//                    item, // TODO
            );

            // Manually use Presenter
            movePresenter.present(initialOutputData);

        } catch (Exception e) {
            System.err.println("Error during initial game load!");
            e.printStackTrace();
        }


        application.add(moveView);
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);

    }
}
