package move;

import API.MonsterDetail;
import API.MoveStaticMapInterface;
import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.move.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MoveInteractorTest {

    private AdventureGame game;
    private FakeMoveGameDataAccess dataAccess;
    private FakeMovePresenter presenter;
    private FakeStaticMapService mapService;
    private MoveInteractor interactor;

    @BeforeEach
    void setUp() {
        User user = new User();
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Start", 43.66, -79.39, null, null));
        locations.add(new Location("Middle", 43.67, -79.40, null, new Item("Shield", "Armour")));
        Location endLocation = new Location("End", 43.68, -79.41, new Monster(), null);
        locations.add(endLocation);
        GameMap map = new GameMap(locations, 0);
        game = new AdventureGame(user, map);

        dataAccess = new FakeMoveGameDataAccess(game);
        presenter = new FakeMovePresenter();
        mapService = new FakeStaticMapService();
        interactor = new MoveInteractor(dataAccess, presenter, mapService);
    }

    @Test
    void testMoveRightSuccess() {
        // Initial position is 0
        assertEquals(0, game.getGameMap().getCurrentLocationIndex());

        MoveInputData inputData = new MoveInputData(Direction.RIGHT);
        interactor.execute(inputData);

        // Position should be 1
        assertEquals(1, game.getGameMap().getCurrentLocationIndex());
        assertTrue(presenter.presentCalled);
        assertNotNull(presenter.outputData);
        assertEquals("Middle", presenter.outputData.getCurrentLocationName());
        assertTrue(presenter.outputData.isCanMoveLeft());
        assertTrue(presenter.outputData.isCanMoveRight()); // Can move to End
        assertNull(presenter.outputData.getMonster());
        assertNotNull(presenter.outputData.getStaticMapImage());
        assertEquals(3, presenter.outputData.getMapSize());
    }

    @Test
    void testMoveToLocationWithItem() {
        // Initial position is 0
        assertEquals(0, game.getGameMap().getCurrentLocationIndex());

        MoveInputData inputData = new MoveInputData(Direction.RIGHT);
        interactor.execute(inputData);

        // Position should be 1
        assertEquals(1, game.getGameMap().getCurrentLocationIndex());
        assertTrue(presenter.presentCalled);
        assertNotNull(presenter.outputData);
        assertNotNull(presenter.outputData.getItem());
        assertEquals("Shield", presenter.outputData.getItem().getName());
    }


    @Test
    void testMoveLeftSuccess() {
        // Set initial position to 1
        game.getGameMap().move(Direction.RIGHT); // Now at Middle
        assertEquals(1, game.getGameMap().getCurrentLocationIndex());

        MoveInputData inputData = new MoveInputData(Direction.LEFT);
        interactor.execute(inputData);

        // Position should be 0
        assertEquals(0, game.getGameMap().getCurrentLocationIndex());
        assertTrue(presenter.presentCalled);
        assertNotNull(presenter.outputData);
        assertEquals("Start", presenter.outputData.getCurrentLocationName());
        assertFalse(presenter.outputData.isCanMoveLeft()); // At the beginning
        assertTrue(presenter.outputData.isCanMoveRight());
        assertNull(presenter.outputData.getMonster());
    }

    @Test
    void testMoveFailAtRightEdge() {
        // Set initial position to 2 (End)
        game.getGameMap().move(Direction.RIGHT);
        game.getGameMap().move(Direction.RIGHT);
        assertEquals(2, game.getGameMap().getCurrentLocationIndex());

        MoveInputData inputData = new MoveInputData(Direction.RIGHT);
        interactor.execute(inputData);

        // Position should still be 2
        assertEquals(2, game.getGameMap().getCurrentLocationIndex());
        assertTrue(presenter.presentCalled);
        assertNotNull(presenter.outputData);
        assertEquals("End", presenter.outputData.getCurrentLocationName());
        assertTrue(presenter.outputData.isCanMoveLeft());
        assertFalse(presenter.outputData.isCanMoveRight()); // At the end
        assertNotNull(presenter.outputData.getMonster());
    }

    @Test
    void testMoveFailAtLeftEdge() {
        // Initial position is 0
        assertEquals(0, game.getGameMap().getCurrentLocationIndex());

        MoveInputData inputData = new MoveInputData(Direction.LEFT);
        interactor.execute(inputData);

        // Position should still be 0
        assertEquals(0, game.getGameMap().getCurrentLocationIndex());
        assertTrue(presenter.presentCalled);
        assertNotNull(presenter.outputData);
        assertEquals("Start", presenter.outputData.getCurrentLocationName());
        assertFalse(presenter.outputData.isCanMoveLeft());
        assertTrue(presenter.outputData.isCanMoveRight());
    }

    @Test
    void testSwitchToBattleView() {
        Monster monster = new Monster();
        interactor.switchToBattleView(monster);

        assertTrue(presenter.switchToBattleCalled);
        assertEquals(game.getUser(), presenter.user);
        assertEquals(monster, presenter.monster);
    }
    
    @Test
    void testUpdateGame() {
        interactor.updateGame();
        assertTrue(presenter.presentCalled);
        assertNotNull(presenter.outputData);
        assertEquals(0, presenter.outputData.getCurrentIndex());
    }

    @Test
    void testLocationWithMonsterAndItemThrowsException() {
        User user = new User();
        Location invalidLocation = new Location("Invalid", 0, 0, new Monster(), new Item("Cursed Sword", "Weapon"));
        GameMap map = new GameMap(Collections.singletonList(invalidLocation), 0);
        AdventureGame specificGame = new AdventureGame(user, map);
        FakeMoveGameDataAccess specificDataAccess = new FakeMoveGameDataAccess(specificGame);
        MoveInteractor specificInteractor = new MoveInteractor(specificDataAccess, presenter, mapService);

        Exception exception = assertThrows(IllegalArgumentException.class, specificInteractor::updateGame);
        String expectedMessage = "Location cannot contain both a Monster and an Item.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

// Fakes for testing
class FakeMoveGameDataAccess implements MoveGameDataAccessInterface {
    private AdventureGame game;

    FakeMoveGameDataAccess(AdventureGame game) {
        this.game = game;
    }

    @Override
    public AdventureGame getGame() {
        return game;
    }

    @Override
    public void saveGame(AdventureGame game) {
        // In a real scenario, this would save the game state.
        // For this fake, we can just update the reference.
        this.game = game;
    }
}

class FakeMovePresenter implements MoveOutputBoundary {
    boolean presentCalled = false;
    boolean switchToBattleCalled = false;
    MoveOutputData outputData;
    User user;
    Monster monster;

    @Override
    public void present(MoveOutputData moveOutputData) {
        this.presentCalled = true;
        this.outputData = moveOutputData;
    }

    @Override
    public void switchToBattleView(User user, Monster monster) {
        this.switchToBattleCalled = true;
        this.user = user;
        this.monster = monster;
    }
}

class FakeStaticMapService implements MoveStaticMapInterface {
    @Override
    public ImageIcon getMapImage(double latitude, double longitude) {
        // Return a dummy ImageIcon for testing purposes.
        return new ImageIcon();
    }
}
