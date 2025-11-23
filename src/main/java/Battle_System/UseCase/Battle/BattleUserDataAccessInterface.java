package Battle_System.UseCase.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.User;

public interface BattleUserDataAccessInterface {
    /**
     * saved the user and the monster
     */
    void save(User user, Monster monster);

    /**
     * return the User that contains the user information which is before user starting the battle
     */
    User getUserBeforeBattle();

    /**
     * return the User that contains the user information which is after user starting th battle
     */
    User getUserAfterBattle();

    /**
     * return the Monster that contains the monster information which is before user starting the battle
     */
    Monster getMonsterBeforeBattle();

    /**
     * return the Monster that contains the monster information which is after user starting the battle
     */
    Monster getMonsterAfterBattle();


}
