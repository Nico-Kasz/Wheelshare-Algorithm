package com.backend.Server.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class GeoJson {
    private String type;
    private Geometry geometry;

    public GeoJson(List<Poi> way) {
        this.type = "Feature";
        this.geometry = new Geometry(way);
    }
}
