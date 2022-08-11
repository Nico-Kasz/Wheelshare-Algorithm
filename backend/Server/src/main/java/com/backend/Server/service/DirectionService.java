package com.backend.Server.service;

import com.backend.Server.dao.GraphDao;
import com.backend.Server.dao.SampleGraphDao;
import com.backend.Server.model.Direction;
import com.backend.Server.model.GeoJson;
import com.backend.Server.model.Poi;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public GeoJson findDirection(Direction dir) {
        // get the start and ending point of the graph
        // based on requested direction
        int[] metaData = graphDao.metaGraph(dir);
        int start = metaData[0];
        int end = metaData[1];
        System.out.println("finish meta");
        // get the adjacency matrix
        Map<Integer, List<Integer>> adj = graphDao.constructGraph();
        Map<Integer, List<Double>> weights = graphDao.constructWeight();
//        // some calculation
//        System.out.println("Number of nodes: " + adj.size());
//        int summ = 0;
//        for (List<Integer> nei : adj.values()) {
//            summ += nei.size();
//        }
//        System.out.println("Number of edges: " + summ);
//        // end calculation
        System.out.println("finished construct adjacency and weights");
        // initialize the graph algorithm to run
        Graph graphAlgo = new Graph(start, end, graphDao.getMapping(), adj, weights);
        List<Integer> result = graphAlgo.routeAlgo();
        System.out.println(result);
        System.out.println("Finished graph result");
        System.out.println();
        // convert back to usable data
        List<Poi> route =  graphDao.match(result);
        // adding the actual start and ending point of the user
        route.add(0, dir.getSource());
        route.add(dir.getDestination());
        return new GeoJson(route);
    }

//    public static void main(String[] args) {
//        /**
//         *(-84.7344511, 39.5096585)
//         * (-84.7339319, 39.5100048)
//         * */
//        Direction dir = new Direction(new Poi( -84.7344511, 39.5096585),
//                                    new Poi(-84.7339319, 39.5100048));
//        GraphDao graphData = new SampleGraphDao();
//        DirectionService serv = new DirectionService(graphData);
//        System.out.println(serv.findDirection(dir));
//    }
}
