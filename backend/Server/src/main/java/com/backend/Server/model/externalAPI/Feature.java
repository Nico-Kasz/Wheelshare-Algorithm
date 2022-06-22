package com.backend.Server.model.externalAPI;

import com.backend.Server.model.Poi;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {
    // instance variable
    private List<Double> center;

    // constructor
    public Feature() {
    }

    // setter and getter

    public List<Double> getCenter() {
        return center;
    }

    public void setCenter(List<Double> center) {
        this.center = center;
    }
}
