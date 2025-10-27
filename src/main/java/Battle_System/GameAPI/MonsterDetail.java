package Battle_System.GameAPI;

import java.util.HashMap;

public interface MonsterDetail {
    /**
     * this method will get the urls from the api
     * @throws MonsterNotFoundException throws the exception if nothing is founded
     */
    HashMap<String,String> getAllResourcesURL() throws MonsterNotFoundException;

    /**
     * this method will get the HashMap for spells, where the keys are name of the spells and the values are the dmg
     * @throws MonsterNotFoundException throws the exception if nothing is founded
     */
    HashMap<String,Integer> generateSpells() throws MonsterNotFoundException;

    /**
     * this method will get an Array for races
     * @throws MonsterNotFoundException throws the exception if nothing is founded
     */
    String[] generateRaces() throws MonsterNotFoundException;


    class MonsterNotFoundException extends Exception {
        public MonsterNotFoundException() {
            super("Monster not found");
        }
    }
}
