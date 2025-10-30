package Battle_System.Entity;

// TODO: this is just a sample battle class for our project, since we are going to use the clean architecture
//  we should change the code based on this class.

import Battle_System.GameAPI.MonsterDetail;

public class Battle {
    //1. As a user I want to against enemies during battles, I can choose from various options such as "Attack", or "Learn" or “Heal”, so that I can determine the fate of the enemy based on my moral judgment.
    //2. As a user I want to advance the plot by solving the quiz, and discover hidden rooms, secret items and side stories during the exploration process.
    //3. As a user I want to avoid the attack during the enemy's offensive phase by answering the quiz correctly.
    //4. As a user I want to successfully attack enemy in my turn by correctly answering the quiz.
    //5. As a user I want to heal myself in my turn by correctly answering the quiz.
    //6. As a user I want to be able to pick up items after a battle so that I can store them in my inventory.
    public Monster monster;
    public User user;

    /**
     * The constructor for the Battle class.
     */
    public Battle(Monster monster, User user) {
        this.monster = monster;
        this.user = user;
    }

    /**
     * Monster's turn to attack the user, monster will randomly choose a spell and then attack.
     */
    private void MonsterTurn() {
        Spells spell = monster.chooseSpell();
        double DMG = monster.attack(spell);
        user.HPDecrease(DMG);
    }

    /**
     * User's turn to attack the monster.
     */
    private void UserTurn() {
        double DMG = user.attack();
        monster.HPDecrease(DMG);
    }

    /**
     * While both the monster and the user are alive, the battle will start. The battle will over once any of the user
     * and the monsters' HP is less than 0. The fight is turn-based, started fron the user turn.
     */
    public void fight() {
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

    /**
     * Ask the user to answer a quiz by calling the successFlee inside the user class. If the user answered the quiz
     * correctly then the user flee and exit the battle system. If not, then the user will force to enter the fight.
     */
    public void flee(){
        if(! user.successFlee()){
            fight();
        }
    }

    /**
     * This is the final method that would be called in ca, if the user choose "FIGHT" then this method will lead to the
     * fight method. If the use choose "FLEE" then it will call flee() and then call the successFlee which asked the
     * user to answer a quiz.
     */
    public void execute(String input){
        if(input.equals("FLEE")){
            flee();
        }
        else if(input.equals("FIGHT")){
            fight();
        }
    }

}
