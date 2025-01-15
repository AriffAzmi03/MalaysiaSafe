package com.example.malaysiasafe.model;

public class DisasterData {
    private String location;
    private String info;
    private String center;

    // Default constructor required for Firebase
    public DisasterData() {
    }

    // Constructor
    public DisasterData(String location, String info, String center) {
        this.location = location;
        this.info = info;
        this.center = center;
    }

    // Getter and Setter methods
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
