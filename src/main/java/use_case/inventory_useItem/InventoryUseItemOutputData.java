package use_case.inventory_useItem;

import java.util.List;

/**
 * Output data for inventory use item use case
 * Contains no entities !!!
 */
public class InventoryUseItemOutputData {
    private final List<ItemDTO> items;
    private final int hpIncrease;
    private final int defIncrease;
    private final int dmgIncrease;
    private final boolean success;
    private final String errorMessage;

    /**
     * Constructor
     */
    public InventoryUseItemOutputData(List<ItemDTO> items, int hpIncrease,
                                      int defIncrease, int dmgIncrease) {
        this.items = items;
        this.hpIncrease = hpIncrease;
        this.defIncrease = defIncrease;
        this.dmgIncrease = dmgIncrease;
        this.success = true;
        this.errorMessage = null;
    }

    /**
     * Constructor for failed issue
     */
    public InventoryUseItemOutputData(String errorMessage) {
        this.items = null;
        this.hpIncrease = 0;
        this.defIncrease = 0;
        this.dmgIncrease = 0;
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public int getHpIncrease() {
        return hpIncrease;
    }

    public int getDefIncrease() {
        return defIncrease;
    }

    public int getDmgIncrease() {
        return dmgIncrease;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Data Transfer Object for Item information
     * Keeps entity details from leaking across boundaries
     */
    public static class ItemDTO {
        private final String name;
        private final String type;
        private final int value;

        public ItemDTO(String name, String type, int value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getValue() {
            return value;
        }
    }
}