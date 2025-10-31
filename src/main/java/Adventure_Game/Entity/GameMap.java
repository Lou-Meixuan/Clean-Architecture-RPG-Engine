package Adventure_Game.Entity;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private final List<List<Location>> grid;
    private final int width;
    private final int height;

    // 玩家在地图上的当前位置
    private int currentX;
    private int currentY;

    public GameMap(List<List<Location>> gridLocations, int playerX, int playerY) {
        this.grid = gridLocations;
        this.width = gridLocations.get(0).size();
        this.height = gridLocations.size();
        currentX = playerX;
        currentY = playerY;
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
}