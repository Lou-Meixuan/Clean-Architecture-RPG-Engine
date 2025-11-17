package Battle_System.UseCase.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.User;

public class Battle_OutputData {
    private final User user;
    private final Monster monster;
    private final boolean battleEnded;

    public Battle_OutputData(User user, Monster monster) {
        this.user = user;
        this.monster = monster;
        this.battleEnded = !user.isAlive() || !monster.isAlive();
    }

    public User getUser() {
        return user;
    }

    public Monster getMonster() {
        return monster;
    }

    public Boolean isWin(){
        return user.isAlive();
    }
    public boolean isBattleEnded() {
        return battleEnded;
    }

    public double getUserHP() {
        return user.getHP();
    }

    public double getMonsterHP() {
        return monster.getHP();
    }
}
