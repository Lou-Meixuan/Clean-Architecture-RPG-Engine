package Battle_System.Interface_Adapter.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.User;

public class Battle_State {
    private User user = null;
    private Monster monster = null;
    private double userHp = 0;
    private double monsterHP = 0;

    public Monster getMonster() {return monster;}
    public void setMonster(Monster monster) {this.monster = monster;}

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public double getUserHp() {
        return userHp;
    }
    public void setUserHp() {
        this.userHp = this.getUser().getHP();
    }

    public double getMonsterHP() {
        return monsterHP;
    }
    public void setMonsterHPp() {
        this.monsterHP = this.getMonster().getHP();
    }
}
