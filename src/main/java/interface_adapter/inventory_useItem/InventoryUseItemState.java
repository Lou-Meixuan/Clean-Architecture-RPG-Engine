package interface_adapter.inventory_useItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryUseItemState {

    private List<String> itemNames;
    private List<String> itemTypes;
    private List<Integer> itemValues;

    private boolean needsRefresh;

    private int hpIncrease;
    private int defIncrease;
    private int dmgIncrease;

    private String message;

    public InventoryUseItemState() {
        this.itemNames = new ArrayList<>();
        this.itemTypes = new ArrayList<>();
        this.itemValues = new ArrayList<>();
        this.hpIncrease = 0;
        this.defIncrease = 0;
        this.dmgIncrease = 0;
        this.message = "";
        this.needsRefresh = false;
    }

    // Getters and Setters
    public List<String> getItemNames() {
        return itemNames;
    }

    public void setItemNames(List<String> itemNames) {
        this.itemNames = itemNames;
    }

    public List<String> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<String> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public List<Integer> getItemValues() {
        return itemValues;
    }

    public void setItemValues(List<Integer> itemValues) {
        this.itemValues = itemValues;
    }

    public int getHpIncrease() {
        return hpIncrease;
    }

    public void setHpIncrease(int hpIncrease) {
        this.hpIncrease = hpIncrease;
    }

    public int getDefIncrease() {
        return defIncrease;
    }

    public void setDefIncrease(int defIncrease) {
        this.defIncrease = defIncrease;
    }

    public int getDmgIncrease() {
        return dmgIncrease;
    }

    public void setDmgIncrease(int dmgIncrease) {
        this.dmgIncrease = dmgIncrease;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getNeedsRefresh() {
        return needsRefresh;
    }

    public void setNeedsRefresh(boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }
}
