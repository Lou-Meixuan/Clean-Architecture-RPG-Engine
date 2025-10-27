package Battle_System.User;

/**
 * This Monster class
 */
public class Monster {
    //1. As a user I want those monsters I’ve defeated to be recorded, and to be able to view their status information.
    //2. As a user I want that when I pass through the same area again, the monsters I’ve already defeated will not reappear.
    //3. As a user I want those monsters have name, atk, hp, and damage type
    private static int HP;
    private static int SPD;
    private static Ability ability;
    private String description;

    public String getDescription() {
        return description;
    }

    public int getHP() {
        return HP;
    }

    public int getSPD() {
        return SPD;
    }

    public Ability getAbility() {
        return ability;
    }

    public Monster(Ability dmg) {

    }


}
