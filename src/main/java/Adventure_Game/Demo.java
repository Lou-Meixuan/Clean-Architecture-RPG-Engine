package Adventure_Game;

import Adventure_Game.data_access.InMemoryUserDataAccessObject;
import Adventure_Game.data_access.StaticMapDataAccessObject;
import Adventure_Game.entity.AdventureGame;
import Adventure_Game.entity.Direction;
import Adventure_Game.entity.Location;
import Adventure_Game.interface_adapter.move.MoveController;
import Adventure_Game.interface_adapter.move.MovePresenter;
import Adventure_Game.interface_adapter.move.MoveStaticMapDataAccessInterface;
import Adventure_Game.interface_adapter.move.MoveViewModel;
import Adventure_Game.use_case.move.MoveGameDataAccessInterface;
import Adventure_Game.use_case.move.MoveInputBoundary;
import Adventure_Game.use_case.move.MoveInteractor;
import Adventure_Game.use_case.move.MoveOutputData;
import Adventure_Game.view.MoveView;
import Battle_System.User.Monster;

import javax.swing.*;

public class Demo {
    public static void main(String[] args) {
        JFrame application = new JFrame("My Clean Architecture Adventure Game");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        MoveGameDataAccessInterface moveGameDataAccess = new InMemoryUserDataAccessObject();
        MoveStaticMapDataAccessInterface mapService = new StaticMapDataAccessObject();

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
