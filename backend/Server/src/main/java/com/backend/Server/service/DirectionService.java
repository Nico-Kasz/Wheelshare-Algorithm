package com.backend.Server.service;

import com.backend.Server.dao.GraphDao;
import com.backend.Server.model.Direction;
import com.backend.Server.model.Poi;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DirectionService {
    // instance variable
    private final GraphDao graphDao;
    // constructor
    public DirectionService(GraphDao graphDao) {
        this.graphDao = graphDao;
    }
    // method
    public List<Poi> findDirection(Direction dir) {
        // get the num of vertices, start and ending point
        int[] metaData = graphDao.metaGraph();
        int V = metaData[0];
        int start = metaData[1];
        int end = metaData[2];
        // adjacency matrix
        List<List<Integer>> adj = graphDao.constructGraph(dir);
        // initialize the graph algorithm to run
        Graph graphAlgo = new Graph(V, start, end, adj);
        List<Integer> result = graphAlgo.routeAlgo();
        // convert back to usable data
        return graphDao.match(result);
    }
}
