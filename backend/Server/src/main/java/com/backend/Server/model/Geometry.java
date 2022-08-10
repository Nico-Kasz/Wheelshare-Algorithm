package com.backend.Server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString
@Getter
public class Geometry {
    private String type;
    private List<List<Double>> coordinates;

    public Geometry(List<Poi> way) {
        this.type = "LineString";
        this.coordinates = new ArrayList<>();
        for (Poi point : way) {
            List<Double> coordinate = new ArrayList<>(Arrays.asList(point.getLongitude(), point.getLatitude()));
            this.coordinates.add(coordinate);
        }
    }
}
