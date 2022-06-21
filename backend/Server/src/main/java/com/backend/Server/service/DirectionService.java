package com.backend.Server.service;

import com.backend.Server.dao.GraphDao;
import com.backend.Server.model.Direction;
import com.backend.Server.model.Poi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DirectionService {
    // instance variable
    private final GraphDao graphDao;
    // constructor
    @Autowired
    public DirectionService(@Qualifier("GraphData") GraphDao graphDao) {
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
        List<List<Integer>> weight = graphDao.constructWeight(dir);
        // initialize the graph algorithm to run
        Graph graphAlgo = new Graph(V, start, end, adj, weight);
        List<Integer> result = graphAlgo.routeAlgo();
        // convert back to usable data
        return graphDao.match(result);
    }
}
