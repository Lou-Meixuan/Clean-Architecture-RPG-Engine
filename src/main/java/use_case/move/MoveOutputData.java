package use_case.move;

import entity.Item;
import entity.Monster;

public class MoveOutputData {
    private final String currentLocationName;

    private final int currentIndex;
    private final int mapSize;

    private final boolean canMoveLeft;
    private final boolean canMoveRight;

    private final Monster monster;
    private final Item item;
    private final byte[] staticMapImage;

    public MoveOutputData(String currentLocationName, int currentIndex, int mapSize,
                          boolean canMoveLeft, boolean canMoveRight, Monster monster, Item item, byte[] staticMapImage) {
        if (monster != null && item != null) {
            throw new IllegalArgumentException("Location cannot contain both a Monster and an Item.");
        }
        this.currentLocationName = currentLocationName;
        this.currentIndex = currentIndex;
        this.mapSize = mapSize;
        this.canMoveLeft = canMoveLeft;
        this.canMoveRight = canMoveRight;
        this.monster = monster;
        this.item = item;
        this.staticMapImage = staticMapImage;
    }

    public String getCurrentLocationName() {
        return currentLocationName;
    }

    public boolean isCanMoveLeft() {
        return canMoveLeft;
    }

    public boolean isCanMoveRight() {
        return canMoveRight;
    }

    public Monster getMonster() {
        return monster;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getMapSize() {
        return mapSize;
    }

    public Item getItem() {
        return item;
    }

    public byte[] getStaticMapImage() {
        return staticMapImage;
    }
}
