package com.example.malaysiasafe;

public class DisasterData {
    private String id;
    private String location;
    private String info;
    private String center;

    public DisasterData() {
        // Default constructor required for Firebase
    }

    public DisasterData(String location, String info, String center) {
        this.location = location;
        this.info = info;
        this.center = center;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "ID: " + id + ", Location: " + location + ", Info: " + info + ", Center: " + center;
    }

}
