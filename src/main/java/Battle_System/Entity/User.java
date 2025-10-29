package Battle_System.Entity;

import Battle_System.GameAPI.SrdMonsterDetail;

import java.util.Random;

public class User {
    //1. As a user I want to have an initial ATK and HP value when the game starts.
    //2. As a user I want to be able to initiate a battle so that I can attack my opponent/monster or choose flee to aviod the fight.
    //3. As a user I want to be able to access my inventory so that I can equip or use items.
    public String NAME;
    private int HP;
    private int DMG = 8;
    private int DEF = 0;

    public User() {
        Random random = new Random();
        HP = random.nextInt(11) + 20;
    }

    // Getters and Setters
    public int getHP() {
        return HP;
    }

    public int getDMG() {
        return DMG;
    }

    public boolean isAlive() {
        return HP > 0;
    }

    public void HPDecrease(int DMG){
        HP -= DMG;
        if (HP < 0) HP = 0;
    }

    public void addDMG(int dmg){
        DMG += dmg;
    }

    public void decreaseDMG(int dmg){
        DMG -= dmg;
    }

    public int getDEF() {
        return DEF;
    }

    public void addDEF(int def){
        DEF += def;
    }

    public void decreaseDEF(int def){
        DEF -= def;
    }

    public Boolean SuccessAttack(){
        return null;
        // TODO : answer the quiz correctly then return true in this method
    }

    public int attack(){
        if(SuccessAttack()){
            return getDMG();
        }
        else{
            return 0;
        }
    }
}
