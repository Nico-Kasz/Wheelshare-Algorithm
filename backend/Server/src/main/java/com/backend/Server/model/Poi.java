package com.backend.Server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Poi {
    private double longitude;
    private double latitude;

    // constructor
    public Poi(@JsonProperty("lon") double longitude,
               @JsonProperty("lat") double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // getter and setter

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // to string
    @Override
    public String toString() {
        return String.format("(%.8f, %.8f)", longitude, latitude);
    }

    // distance
    public double distance(Poi other) {
        return Math.pow (this.latitude - other.latitude, 2) + Math.pow(this.longitude - other.longitude, 2);
    }
}
