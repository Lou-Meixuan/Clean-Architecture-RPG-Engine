package app;

import data_access.FileGameDataAccessObject;
import API.GeoapifyStaticMap;
import entity.AdventureGame;
import entity.Direction;
import entity.Location;
import interface_adapter.move.MoveController;
import interface_adapter.move.MovePresenter;
import API.MoveStaticMapInterface;
import interface_adapter.move.MoveViewModel;
import interface_adapter.results.ResultsViewModel;
import interface_adapter.results.ShowResultsController;
import interface_adapter.results.ShowResultsPresenter;
import use_case.move.MoveInputBoundary;
import use_case.move.MoveInteractor;
import use_case.move.MoveOutputData;
import use_case.show_results.ShowResultsInputBoundary;
import use_case.show_results.ShowResultsInteractor;
import view.MoveView;
import view.ResultsView;
import entity.Monster;

import javax.swing.*;
import java.awt.*;

public class Demo {
    public static void main(String[] args) {
        JFrame application = new JFrame("My Clean Architecture Adventure Game");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create CardLayout for view switching
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);

        // Data Access
        FileGameDataAccessObject gameDataAccess = new FileGameDataAccessObject();
        MoveStaticMapInterface mapService = new GeoapifyStaticMap();

        // Move components
        MoveViewModel moveViewModel = new MoveViewModel();
        MovePresenter movePresenter = new MovePresenter(moveViewModel, mapService);

        MoveInputBoundary moveInteractor = new MoveInteractor(
                gameDataAccess,
                movePresenter
        );

        MoveController moveController = new MoveController(
                moveInteractor
        );

        MoveView moveView = new MoveView(
                moveViewModel,
                moveController
        );

        // Results components
        ResultsViewModel resultsViewModel = new ResultsViewModel();
        ShowResultsPresenter resultsPresenter = new ShowResultsPresenter(resultsViewModel);

        ShowResultsInputBoundary resultsInteractor = new ShowResultsInteractor(
                gameDataAccess,
                resultsPresenter
        );

        ShowResultsController resultsController = new ShowResultsController(resultsInteractor);

        ResultsView resultsView = new ResultsView(resultsViewModel);


        // Add views to CardLayout
        views.add(moveView, moveView.getViewName());
        views.add(resultsView, resultsView.getViewName());

        // Wire up End Game button to switch to results
        moveView.getEndGameButton().addActionListener(e -> {
            resultsController.execute();
            cardLayout.show(views, resultsView.getViewName());
        });

        // Wire up Play Again button to restart application
        resultsView.getBackButton().addActionListener(e -> {
            System.out.println("Restarting game...");
            application.dispose();
            main(new String[]{});
        });

        try {
            AdventureGame initialGame = gameDataAccess.getGame();

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

        application.add(views);
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);

    }
}
