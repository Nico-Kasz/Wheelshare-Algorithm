package com.backend.Server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Direction {
    // instance variable
    private final Poi source;
    private final Poi destination;

    // constructor
    public Direction(@JsonProperty("source") Poi source,
                     @JsonProperty("dest") Poi destination) {
        this.source = source;
        this.destination = destination;
    }

    // getter
    public Poi getSource() {
        return source;
    }

    public Poi getDestination() {
        return destination;
    }
}
