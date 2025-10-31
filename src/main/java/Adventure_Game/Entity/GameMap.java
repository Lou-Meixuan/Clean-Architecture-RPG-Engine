package Adventure_Game.Entity;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    // TODO: use constuctor to init those final variables
    private final List<List<Location>> grid = null;
    private final int width = 1;
    private final int height = 1;

    // 玩家在地图上的当前位置
    private int currentX = 1;
    private int currentY = 1;

    public GameMap(List<Location> allLocations, int maxGridSize) {
        // TODO: use generate function to generate valid GameMap
    }

    public GameMap(List<List<Location>> gridLocations, int playerX, int playerY) {
        // TODO: use load map from existing map
    }

    public void movePlayer(Direction direction) {
        // TODO: move player
    }

    public void setPlayerLocation(int x, int y) {
        // TODO: set player location
    }

    public Location getPlayerLocation() {
        return this.getLocation(currentX, currentY);
    }

    public Location getLocation(int x, int y) {
        if (!isValidPosition(x, y)) {
            return null;
        }
        return grid.get(y).get(x);
    }


    private boolean isValidPosition(int x, int y) {
        return y >= 0 && y < this.height && x >= 0 && x < this.width;
    }


    private void runGridGeneration(List<Location> allLocations, int gridSize) {
        // TODO: generate matrix

    }
}