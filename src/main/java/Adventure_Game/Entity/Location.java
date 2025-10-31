package Adventure_Game.Entity;

import Battle_System.User.User;

public class Location {

    private final String name;
    private final double latitude;
    private final double longitude;

    private final Event event;

    public Location(String name, double latitude, double longitude, Event event) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.event = event;
    }

    public Event getEvent(User user) {
        return event;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}