package app;

import data_access.FileDataAccessFactory;
import data_access.GameDataAccessFactory;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create production data access factory
        GameDataAccessFactory dataAccessFactory = new FileDataAccessFactory();

        // Inject factory into AppBuilder (Dependency Injection)
        AppBuilder appBuilder = new AppBuilder(dataAccessFactory);
        JFrame application = appBuilder
                .addQuizView()
                .addUseInventoryView()
                .addBattleView()
                .addMoveView()
                .addOpenGameView()
                .addResultsView()
                .addAddInventoryView()


                // Use cases
                .addOpenGameUseCase()
                .addUseInventoryUseCase()
                .addMoveUseCase()
                .addBattleUseCase()
                .addQuizUseCase()
                .addResultsUseCase()
                .addAddInventoryUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
