package Battle_System.Entity;

public class Item {

    private String name;
    private String description;
    private String type; // type of item??

    public Item(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }

}
