package Battle_System.User;

import java.util.ArrayList;
import java.util.HashMap;


public class Ability {
    private String type;
    private String description;
    private HashMap<String,Integer> DMG;

    /**
     * This DamageType class contains the type of the damage, the description of the damage type, the 2-3 skills that
     * this monster has, and the map contains those skills and corresponding dmg
     */
    public Ability(String type, String description, HashMap<String,Integer> DMG) {
        this.type = type;
        this.description = description;
        this.DMG = DMG;
    }


    public String getType() {
        return type;
    }
    public String getDescription() {
        return description;
    }

    public HashMap<String,Integer> getDMG() {
        return DMG;
    }


}
