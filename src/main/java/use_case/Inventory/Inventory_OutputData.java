package use_case.Inventory;

import entity.Item;

import java.util.List;

public class Inventory_OutputData {
    private final List<Item> items;
    private final int hpIncrease;
    private final int defIncrease;
    private final int dmgIncrease;
    public Inventory_OutputData(List<Item> items, int hpIncrease , int defIncrease , int dmgIncrease ) {
        this.items = items;
        this.hpIncrease = hpIncrease;
        this.defIncrease = defIncrease;
        this.dmgIncrease = dmgIncrease;
    }

    public List<Item> getItems() { return items;}
    public int getHpIncrease() { return hpIncrease;}
    public int getDefIncrease() { return defIncrease;}
    public int getDmgIncrease() { return dmgIncrease;}
}
