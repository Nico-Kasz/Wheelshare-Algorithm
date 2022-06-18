package com.backend.Server.dao;

import com.backend.Server.model.Direction;
import com.backend.Server.model.Poi;

import java.util.List;

public interface GraphDao {
    // return the adjacency matrix of the graph
    List<List<Integer>> constructGraph(Direction direction);

    // return an array of 3 elements:
    // number of vertices, source and destination of the graph
    int[] metaGraph();

    // match the number to the poi
    List<Poi> match(List<Integer> path);

}
