package com.example.malaysiasafe;

import android.os.Parcel;

import android.os.Parcelable;

public class Report implements Parcelable {

    private String location;

    private String time;

    private String type;

    private String severity;

    private String description;

    private String imageUrl;


// Default constructor required for Firebase

    public Report() {

    }


// Constructor

    public Report(String location, String time, String type, String severity, String description, String imageUrl) {

        this.location = location;

        this.time = time;

        this.type = type;

        this.severity = severity;

        this.description = description;

        this.imageUrl = imageUrl;

    }


// Parcelable implementation

    protected Report(Parcel in) {

        location = in.readString();

        time = in.readString();

        type = in.readString();

        severity = in.readString();

        description = in.readString();

        imageUrl = in.readString();

    }


    public static final Creator<Report> CREATOR = new Creator<Report>() {

        @Override

        public Report createFromParcel(Parcel in) {

            return new Report(in);

        }


        @Override

        public Report[] newArray(int size) {

            return new Report[size];

        }

    };


    @Override

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(location);

        dest.writeString(time);

        dest.writeString(type);

        dest.writeString(severity);

        dest.writeString(description);

        dest.writeString(imageUrl);

    }


    @Override

    public int describeContents() {

        return 0;

    }


// Getters and setters

    public String getLocation() {

        return location;

    }


    public void setLocation(String location) {

        this.location = location;

    }


    public String getTime() {

        return time;

    }


    public void setTime(String time) {

        this.time = time;

    }


    public String getType() {

        return type;

    }


    public void setType(String type) {

        this.type = type;

    }


    public String getSeverity() {

        return severity;

    }


    public void setSeverity(String severity) {

        this.severity = severity;

    }


    public String getDescription() {

        return description;

    }


    public void setDescription(String description) {

        this.description = description;

    }


    public String getImageUrl() {

        return imageUrl;

    }


    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;

    }


// Validation method

    public boolean isValid() {

        return location != null && !location.isEmpty() &&

                time != null && !time.isEmpty() &&

                type != null && !type.isEmpty() &&

                severity != null && !severity.isEmpty() &&

                description != null && !description.isEmpty();

    }


// toString method for debugging

    @Override

    public String toString() {

        return "Report{" +

                "location='" + location + '\'' +

                ", time='" + time + '\'' +

                ", type='" + type + '\'' +

                ", severity='" + severity + '\'' +

                ", description='" + description + '\'' +

                ", imageUrl='" + imageUrl + '\'' +

                '}';

    }
}
