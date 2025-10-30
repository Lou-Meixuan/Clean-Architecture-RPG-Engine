package Battle_System.UseCase.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.User;

/**
 * The Input Data for the Battle Use Case.
 */
public class Battle_InputData {
    private final User user;
    private final Monster monster;

    public Battle_InputData(User user, Monster monster) {
        this.user = user;
        this.monster = monster;
    }

    User getUser() {
        return user;
    }

    Monster getMonster() {
        return monster;
    }
}
