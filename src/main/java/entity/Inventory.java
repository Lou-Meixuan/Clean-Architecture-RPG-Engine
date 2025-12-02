package entity;

import java.util.ArrayList;
import java.util.List;
/**
 * Inventory Class
 * Will be used by the user to store items and also be the access point if the user wants to use items
 *
 */


public class Inventory {
    private List<Item> items;

    public Inventory(List<Item> items) {
        this.items = items; }

    // Add or remove item from inventory
    public void addItem(Item item) {
        items.add(item);
    }
    public void removeItem(Item item) {
        items.remove(item); }

    public Item getItemByName (String name){
        for(Item item : items){
            if (item.getName().equals(name)){
                return item;
            }
        }     return null; }
    public List<Item> getItems(){
        return items;
    }

    public List<String> getItemsList() {
        List<String> list = new ArrayList<String>();
        if(!items.isEmpty()){
            for(Item item : items){
                list.add(item.getName());
            } return list;
        } return list;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

