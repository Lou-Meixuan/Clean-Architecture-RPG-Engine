package entity;

import API.SrdMonsterDetail;

import java.util.ArrayList;
import java.util.Random;
/**
 * This Monster class
 */
public class Monster {
    //1. As a user I wa nt those monsters I’ve defeated to be recorded, and to be able to view their status information.
    //2. As a user I want that when I pass through the same area again, the monsters I’ve already defeated will not reappear.
    //3. As a user I want those monsters have name, atk, hp, and damage type
    public String NAME;
    private double HP;
    private Spells[] SPELL;

    /**
     * The constructor of the Monster. It randomly set the value of the HP, select name and generate spells from
     * the api.
     */
    public Monster() {
        Random random = new Random();
        HP = random.nextInt(11) + 20;

        SrdMonsterDetail api = new SrdMonsterDetail();
        setSpells(api);
        setNAME(api);
    }

    // TODO: finish the override
    @Override
    public String toString() {
        return NAME;
    }


    /**
     * The getter for the HP.
     */
    public double getHP() {
        return HP;
    }

    /**
     * The getter for the SPELL.
     */
    public Spells[] getSpells() {
        return SPELL;
    }

    /**
     * Decrease the HP.
     */
    public void HPDecrease(double DMG){
        HP -= DMG;
        if (HP < 0) HP = 0;
    }

    /**
     * Randomly select a name from the api.
     */
    public void setNAME(SrdMonsterDetail api) {
        Random random = new Random();
        String[] nameList = api.generateRaces();
        int size = nameList.length;
        int index = random.nextInt(size);
        NAME = nameList[index];
    }

    /**
     * Randomly select size of the spell list for the monster and randomly select the type of spells.
     */
    public void setSpells(SrdMonsterDetail api) {
        Random random = new Random();
        int size = random.nextInt(3) + 1;
        ArrayList<Spells> spell = api.generateSpells();
        int spellListSize = spell.size();
        SPELL = new Spells[size];
        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(spellListSize);
            SPELL[i] = spell.get(randomIndex);
        }
    }

    /**
     * Randomly select the spell
     */
    public Spells chooseSpell(){
        Random random = new Random();
        int size = SPELL.length;
        int index = random.nextInt(size);
        return SPELL[index];
    }

    /**
     * Return the DMG value of the monster.
     */
    public double attack(Spells spell) {
        return spell.getDMG();
    }

    /**
     * Return true if the HP of the monster is greater than 0, which indicate the monster is still alive.
     */
    public boolean isAlive() {
        return HP > 0;
    }
}
