package Battle_System;

import Battle_System.Entity.Monster;
import Battle_System.Entity.Spells;
import Battle_System.Entity.User;

// TODO: this is just a sample battle class for our project, since we are going to use the clean architecture
//  we should change the code based on this class.

public class Battle {
    //1. As a user I want to against enemies during battles, I can choose from various options such as "Attack", or "Learn" or “Heal”, so that I can determine the fate of the enemy based on my moral judgment.
    //2. As a user I want to advance the plot by solving the quiz, and discover hidden rooms, secret items and side stories during the exploration process.
    //3. As a user I want to avoid the attack during the enemy's offensive phase by answering the quiz correctly.
    //4. As a user I want to successfully attack enemy in my turn by correctly answering the quiz.
    //5. As a user I want to heal myself in my turn by correctly answering the quiz.
    //6. As a user I want to be able to pick up items after a battle so that I can store them in my inventory.
    public Monster monster;
    public User user;

    public Battle(Monster monster, User user) {
        this.monster = monster;
        this.user = user;
    }

    private void MonsterTurn() {
        Spells spell = monster.chooseSpell();
        int DMG = monster.attack(spell);
        user.HPDecrease(DMG);
    }

    private void UserTurn() {
        int DMG = user.attack();
        monster.HPDecrease(DMG);
    }

    public void execute() {
        while (user.isAlive() && monster.isAlive()) {
                UserTurn();
                if (monster.isAlive()) {
                    break;
                }
                MonsterTurn();
        }
        // TODO: if the monster is dead then we need to delete it from map and move it to the defeated monster list.
        //  If the user is dead, then we go to the recent saved place.
    }

}
