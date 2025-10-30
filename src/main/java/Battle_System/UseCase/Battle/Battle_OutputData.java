package Battle_System.UseCase.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.User;

public class Battle_OutputData {
    private final User user;
    private final Monster monster;

    public Battle_OutputData(User user, Monster monster) {
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
