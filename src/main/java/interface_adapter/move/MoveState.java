package interface_adapter.move;

import entity.Item;
import entity.Monster;

public class MoveState {
    private String linearMap = "";
    private String currentLocationName = "";
    private byte[] staticMapImageData = null;
    private boolean leftButtonEnabled = true;
    private boolean rightButtonEnabled = true;
    private boolean needUpdate = false;

    private Monster monster = null;
    private Item item = null;
    private boolean itemPickupable = false;
    private boolean justReturnedFromDefeat = false;


    public MoveState(MoveState copy) {
        this.linearMap = copy.linearMap;
        this.staticMapImageData = copy.staticMapImageData;
        this.currentLocationName = copy.currentLocationName;
        this.leftButtonEnabled = copy.leftButtonEnabled;
        this.rightButtonEnabled = copy.rightButtonEnabled;
        this.needUpdate = copy.needUpdate;
        this.monster = copy.monster;
        this.item = copy.item;
        this.itemPickupable = copy.itemPickupable;
    }

    public MoveState() {}

    public String getLinearMap() {
        return linearMap;
    }

    public void setLinearMap(String linearMap) {
        this.linearMap = linearMap;
    }

    public byte[] getStaticMapImageData() {
        return staticMapImageData;
    }

    public void setStaticMapImageData(byte[] staticMapImageData) {
        this.staticMapImageData = staticMapImageData;
    }

    public String getCurrentLocationName() {
        return currentLocationName;
    }

    public void setCurrentLocationName(String currentLocationName) {
        this.currentLocationName = currentLocationName;
    }

    public boolean isLeftButtonEnabled() {
        return leftButtonEnabled;
    }

    public void setLeftButtonEnabled(boolean leftButtonEnabled) {
        this.leftButtonEnabled = leftButtonEnabled;
    }

    public boolean isRightButtonEnabled() {
        return rightButtonEnabled;
    }

    public void setRightButtonEnabled(boolean rightButtonEnabled) {
        this.rightButtonEnabled = rightButtonEnabled;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public boolean isItemPickupable() {
        return itemPickupable;
    }

    public void setItemPickupable(boolean itemPickupable) {
        this.itemPickupable = itemPickupable;
    }

    public boolean isJustReturnedFromDefeat() {
        return justReturnedFromDefeat;
    }

    public void setJustReturnedFromDefeat(boolean justReturnedFromDefeat) {
        this.justReturnedFromDefeat = justReturnedFromDefeat;
    }
}
