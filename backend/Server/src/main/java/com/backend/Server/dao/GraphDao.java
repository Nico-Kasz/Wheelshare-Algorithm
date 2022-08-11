package com.backend.Server.dao;

import com.backend.Server.model.Direction;
import com.backend.Server.model.Poi;

import java.util.List;
import java.util.Map;

public interface GraphDao {
    // return the adjacency matrix of the graph
    Map<Integer, List<Integer>> constructGraph();

    // return the weight of the graph
    Map<Integer, List<Double>> constructWeight();

    // return an array of 3 elements:
    // number of vertices, source and destination of the graph
    int[] metaGraph(Direction direction);

    // match the number to the poi
    List<Poi> match(List<Integer> path);

    Map<Integer, Poi> getMapping();

}
