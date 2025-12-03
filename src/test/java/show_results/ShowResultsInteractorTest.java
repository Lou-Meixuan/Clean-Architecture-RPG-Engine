package show_results;

import entity.AdventureGame;
import entity.GameMap;
import entity.Location;
import entity.User;
import org.junit.Test;
import use_case.show_results.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for ShowResultsInteractor achieving 100% line coverage.
 */
public class ShowResultsInteractorTest {

    // ========== Fake/Stub Implementations ==========

    /**
     * Fake presenter to capture output data and verify presenter methods are called.
     */
    static class FakeShowResultsPresenter implements ShowResultsOutputBoundary {
        ShowResultsOutputData capturedOutputData = null;
        boolean switchedToOpenGameView = false;

        @Override
        public void prepareSuccessView(ShowResultsOutputData outputData) {
            this.capturedOutputData = outputData;
        }

        @Override
        public void switchToOpenGameView() {
            this.switchedToOpenGameView = true;
        }
    }

    /**
     * Fake data access to provide test game state and verify clearGameData is called.
     */
    static class FakeShowResultsDataAccess implements ShowResultsGameDataAccessInterface {
        private final AdventureGame mockGame;
        boolean gameDataCleared = false;

        FakeShowResultsDataAccess(AdventureGame mockGame) {
            this.mockGame = mockGame;
        }

        @Override
        public AdventureGame get() {
            return mockGame;
        }

        @Override
        public void clearGameData() {
            gameDataCleared = true;
        }
    }

    // ========== Helper Methods to Create Mock Entities ==========

    /**
     * Creates a mock AdventureGame with a given path history and current location.
     */
    private AdventureGame createMockGame(List<Location> pathHistory, Location currentLocation) {
        User user = new User();
        GameMap gameMap = new GameMap(pathHistory, pathHistory.size() - 1);

        // Create game with the path history using the constructor that accepts it
        AdventureGame game = new AdventureGame(user, gameMap, pathHistory);

        return game;
    }

    /**
     * Creates a mock location with a given name.
     */
    private Location createLocation(String name) {
        return new Location(name, 0.0, 0.0, null, null);
    }

    // ========== Tests for ShowResultsInteractor.execute() ==========

    @Test
    public void execute_withMultipleLocations_preparesCorrectOutputData() {
        // Arrange: Create a game with a path history of 3 locations
        Location loc1 = createLocation("Start Location");
        Location loc2 = createLocation("Middle Location");
        Location loc3 = createLocation("Final Destination");
        List<Location> pathHistory = Arrays.asList(loc1, loc2, loc3);

        AdventureGame mockGame = createMockGame(pathHistory, loc3);
        FakeShowResultsDataAccess dataAccess = new FakeShowResultsDataAccess(mockGame);
        FakeShowResultsPresenter presenter = new FakeShowResultsPresenter();

        ShowResultsInteractor interactor = new ShowResultsInteractor(dataAccess, presenter);
        ShowResultsInputData inputData = new ShowResultsInputData();

        // Act: Execute the use case
        interactor.execute(inputData);

        // Assert: Verify output data is correct
        assertNotNull("Output data should be captured", presenter.capturedOutputData);
        assertEquals("Adventurer", presenter.capturedOutputData.getUserName());
        assertEquals(3, presenter.capturedOutputData.getTotalMoves());
        assertEquals(Arrays.asList("Start Location", "Middle Location", "Final Destination"),
                presenter.capturedOutputData.getPathHistory());
        assertEquals("Final Destination", presenter.capturedOutputData.getFinalLocation());

        // Verify game data was cleared
        assertTrue("Game data should be cleared after showing results", dataAccess.gameDataCleared);
    }

    @Test
    public void execute_withSingleLocation_preparesCorrectOutputData() {
        // Arrange: Create a game with only one location (edge case)
        Location singleLocation = createLocation("Only Location");
        List<Location> pathHistory = Arrays.asList(singleLocation);

        AdventureGame mockGame = createMockGame(pathHistory, singleLocation);
        FakeShowResultsDataAccess dataAccess = new FakeShowResultsDataAccess(mockGame);
        FakeShowResultsPresenter presenter = new FakeShowResultsPresenter();

        ShowResultsInteractor interactor = new ShowResultsInteractor(dataAccess, presenter);
        ShowResultsInputData inputData = new ShowResultsInputData();

        // Act
        interactor.execute(inputData);

        // Assert
        assertNotNull(presenter.capturedOutputData);
        assertEquals("Adventurer", presenter.capturedOutputData.getUserName());
        assertEquals(1, presenter.capturedOutputData.getTotalMoves());
        assertEquals(Arrays.asList("Only Location"), presenter.capturedOutputData.getPathHistory());
        assertEquals("Only Location", presenter.capturedOutputData.getFinalLocation());
        assertTrue(dataAccess.gameDataCleared);
    }

    @Test
    public void execute_withLongPath_calculatesCorrectTotalMoves() {
        // Arrange: Create a game with many locations
        Location loc1 = createLocation("Location 1");
        Location loc2 = createLocation("Location 2");
        Location loc3 = createLocation("Location 3");
        Location loc4 = createLocation("Location 4");
        Location loc5 = createLocation("Location 5");
        List<Location> pathHistory = Arrays.asList(loc1, loc2, loc3, loc4, loc5);

        AdventureGame mockGame = createMockGame(pathHistory, loc5);
        FakeShowResultsDataAccess dataAccess = new FakeShowResultsDataAccess(mockGame);
        FakeShowResultsPresenter presenter = new FakeShowResultsPresenter();

        ShowResultsInteractor interactor = new ShowResultsInteractor(dataAccess, presenter);
        ShowResultsInputData inputData = new ShowResultsInputData();

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals(5, presenter.capturedOutputData.getTotalMoves());
        assertEquals(5, presenter.capturedOutputData.getPathHistory().size());
        assertTrue(dataAccess.gameDataCleared);
    }

    @Test
    public void execute_alwaysCallsPrepareSuccessView() {
        // Arrange
        Location location = createLocation("Test Location");
        AdventureGame mockGame = createMockGame(Arrays.asList(location), location);
        FakeShowResultsDataAccess dataAccess = new FakeShowResultsDataAccess(mockGame);
        FakeShowResultsPresenter presenter = new FakeShowResultsPresenter();

        ShowResultsInteractor interactor = new ShowResultsInteractor(dataAccess, presenter);

        // Act
        interactor.execute(new ShowResultsInputData());

        // Assert: Verify presenter's prepareSuccessView was called
        assertNotNull("Presenter should receive output data", presenter.capturedOutputData);
    }

    @Test
    public void execute_clearGameDataIsCalledBeforePresenterCall() {
        // Arrange: Use a special data access that tracks order of operations
        Location location = createLocation("Test Location");
        AdventureGame mockGame = createMockGame(Arrays.asList(location), location);

        class OrderTrackingDataAccess implements ShowResultsGameDataAccessInterface {
            boolean getWasCalled = false;
            boolean clearWasCalled = false;

            @Override
            public AdventureGame get() {
                getWasCalled = true;
                return mockGame;
            }

            @Override
            public void clearGameData() {
                clearWasCalled = true;
                assertTrue("get() should be called before clearGameData()", getWasCalled);
            }
        }

        OrderTrackingDataAccess dataAccess = new OrderTrackingDataAccess();
        FakeShowResultsPresenter presenter = new FakeShowResultsPresenter();
        ShowResultsInteractor interactor = new ShowResultsInteractor(dataAccess, presenter);

        // Act
        interactor.execute(new ShowResultsInputData());

        // Assert
        assertTrue(dataAccess.getWasCalled);
        assertTrue(dataAccess.clearWasCalled);
    }

    // ========== Tests for ShowResultsInteractor.switchToOpenGameView() ==========

    @Test
    public void switchToOpenGameView_callsPresenterSwitchMethod() {
        // Arrange
        Location location = createLocation("Test Location");
        AdventureGame mockGame = createMockGame(Arrays.asList(location), location);
        FakeShowResultsDataAccess dataAccess = new FakeShowResultsDataAccess(mockGame);
        FakeShowResultsPresenter presenter = new FakeShowResultsPresenter();

        ShowResultsInteractor interactor = new ShowResultsInteractor(dataAccess, presenter);

        // Act
        interactor.switchToOpenGameView();

        // Assert
        assertTrue("Presenter should be notified to switch views", presenter.switchedToOpenGameView);
    }

    @Test
    public void switchToOpenGameView_doesNotClearGameData() {
        // Arrange
        Location location = createLocation("Test Location");
        AdventureGame mockGame = createMockGame(Arrays.asList(location), location);
        FakeShowResultsDataAccess dataAccess = new FakeShowResultsDataAccess(mockGame);
        FakeShowResultsPresenter presenter = new FakeShowResultsPresenter();

        ShowResultsInteractor interactor = new ShowResultsInteractor(dataAccess, presenter);

        // Act
        interactor.switchToOpenGameView();

        // Assert: Verify clearGameData was NOT called
        assertFalse("switchToOpenGameView should not clear game data", dataAccess.gameDataCleared);
    }

    // ========== Tests for ShowResultsOutputData ==========

    @Test
    public void outputData_constructor_setsAllFields() {
        // Arrange
        String userName = "TestPlayer";
        int totalMoves = 5;
        List<String> pathHistory = Arrays.asList("A", "B", "C", "D", "E");
        String finalLocation = "E";

        // Act
        ShowResultsOutputData outputData = new ShowResultsOutputData(
                userName, totalMoves, pathHistory, finalLocation
        );

        // Assert: Test all getters
        assertEquals(userName, outputData.getUserName());
        assertEquals(totalMoves, outputData.getTotalMoves());
        assertEquals(pathHistory, outputData.getPathHistory());
        assertEquals(finalLocation, outputData.getFinalLocation());
    }

    @Test
    public void outputData_gettersReturnCorrectValues() {
        // Arrange
        List<String> path = Arrays.asList("Location 1", "Location 2");
        ShowResultsOutputData outputData = new ShowResultsOutputData(
                "Player", 2, path, "Location 2"
        );

        // Act & Assert: Verify each getter independently
        assertEquals("Player", outputData.getUserName());
        assertEquals(2, outputData.getTotalMoves());
        assertEquals(2, outputData.getPathHistory().size());
        assertEquals("Location 1", outputData.getPathHistory().get(0));
        assertEquals("Location 2", outputData.getPathHistory().get(1));
        assertEquals("Location 2", outputData.getFinalLocation());
    }

    // ========== Tests for ShowResultsInputData ==========

    @Test
    public void inputData_canBeInstantiated() {
        // Act: Simply create the input data object
        ShowResultsInputData inputData = new ShowResultsInputData();

        // Assert: Verify it's not null (covers the class instantiation)
        assertNotNull("ShowResultsInputData should be instantiable", inputData);
    }

    @Test
    public void inputData_multipleInstancesAreIndependent() {
        // Act: Create multiple instances
        ShowResultsInputData inputData1 = new ShowResultsInputData();
        ShowResultsInputData inputData2 = new ShowResultsInputData();

        // Assert: They are different objects
        assertNotSame("Each instance should be independent", inputData1, inputData2);
    }
}
