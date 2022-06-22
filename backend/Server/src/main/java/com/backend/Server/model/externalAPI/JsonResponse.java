package com.backend.Server.model.externalAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResponse {
    // instance variables
    private List<Feature> features;

    // constructor
    public JsonResponse() {
    }
    // setter and getter
    public List<Feature> getFeatures() {
        return this.features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
