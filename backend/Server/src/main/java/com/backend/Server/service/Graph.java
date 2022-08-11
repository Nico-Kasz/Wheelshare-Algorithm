package com.backend.Server.service;

import com.backend.Server.model.Poi;
import com.sun.corba.se.impl.oa.poa.POAImpl;

import java.util.*;

//if design in OOP->consider each node as a class with accordingly properties
public class Graph {
    // Inner class of weight objects
    class Edge implements Comparable<Edge> {
        // instance variables
        private final double weight;
        private final int first, last;

        Edge(double weight, int start, int end) {
            this.weight = weight;
            this.first = start;
            this.last = end;
        }

        @Override
        public int compareTo(Edge o) {
            if (o.weight == this.weight) {
                double thisDist = idToPoi.get(this.last).distance(idToPoi.get(end));
                double otherDist = idToPoi.get(o.last).distance(idToPoi.get(end));
                // result
                if (thisDist < otherDist) {
                    return -1;
                }
                else if (thisDist > otherDist) {
                    return 1;
                }
                return 0;
            }
            return (int)(o.weight - this.weight);
        }

        @Override
        public String toString() {
            return String.format("(%f, %d, %d)", weight, first, last);
        }
    }

    // Instance variable
    // number of vertices
    private int start, end;
    // aiding to find closer destination
    private Map<Integer, Poi> idToPoi;
    // adjacency matrix and weight
    private Map<Integer, List<Integer>> adj;  // map of node (integer) to its adjacent nodes
    private Map<Integer, List<Double>> weights;


    // constructor
    public Graph(int start, int end, Map<Integer, Poi> idToPoi,
                 Map<Integer, List<Integer>> adj, Map<Integer, List<Double>> weights) {
        this.start = start;
        this.end = end;
        this.idToPoi = idToPoi;
        this.adj = adj;
        this.weights = weights;
//        System.out.println("Testing new");
//        System.out.println(this.idToPoi.get(this.start));
//        System.out.println(this.idToPoi.get(this.end));
    }

    public List<Integer> routeAlgo() {
        // Prepare
        // keep track of visited nodes and also the predecessor node
        Map<Integer, Integer> preNode = new HashMap<Integer, Integer>();
        PriorityQueue<Edge> maxHeap = new PriorityQueue<>();

        // create a starting virtual edge to start with
        maxHeap.add(new Edge(0, -1, this.start));

        // Let's go
        while (!preNode.containsKey(end) && maxHeap.size() > 0) {
            // get out the max edge
            Edge maxEdge = maxHeap.remove();
//            // test
//            System.out.println(maxEdge);
//            // test
            int oldNode = maxEdge.first; int newNode = maxEdge.last;
            // otherwise, add this new node to hashmap
            preNode.put(newNode, oldNode);
            // explore the adjacency nodes
            for (int i = 0; i < adj.get(newNode).size(); i++) {
                // prepare
                int nextNode = adj.get(newNode).get(i);
                double weight = weights.get(newNode).get(i);
                // only explore unvisited nodes
                if (preNode.containsKey(nextNode)) {
                    continue;
                }
                // add to heap
                maxHeap.add(new Edge(weight, newNode, nextNode));
            }
        }

        // Printing path
        return printRoute(preNode);
    }

    // Print back the route from the end node
    // given the preNode, a mapping of node and preNode
    public List<Integer> printRoute(Map<Integer, Integer> preNode) {
        List<Integer> result = new LinkedList<Integer>();
        int lastNode = end;
        while (preNode.containsKey(lastNode)) {
            // add to result on the left side
            result.add(0, lastNode);
            // update last node
            lastNode = preNode.get(lastNode);
        }
        // result
        return result;
    }

//    public static void main(String[] args)
//    {
//        // mocking data
//        int start = 1, end = 5;
//        // mocking adjacency matrix and weight
//        // adj
//        Map<Integer, List<Integer>> adj = new HashMap<>();
//        adj.put(1, new LinkedList<Integer>(Arrays.asList(2, 3)));
//        adj.put(2, new LinkedList<Integer>(Arrays.asList(1, 4)));
//        adj.put(3, new LinkedList<Integer>(Arrays.asList(1, 4)));
//        adj.put(4, new LinkedList<Integer>(Arrays.asList(2, 3, 5)));
//        adj.put(5, new LinkedList<Integer>(Arrays.asList(4)));
//
//
//        // weight
//        Map<Integer, List<Double>> weight = new HashMap<>();
//        weight.put(1, new LinkedList<Double>(Arrays.asList(4.0, 16.0)));
//        weight.put(2, new LinkedList<Double>(Arrays.asList(4.0, 3.0)));
//        weight.put(3, new LinkedList<Double>(Arrays.asList(16.0, 3.0)));
//        weight.put(4, new LinkedList<Double>(Arrays.asList(3.0, 3.0, 5.0)));
//        weight.put(5, new LinkedList<Double>(Arrays.asList(5.0)));
//
//        // initialize
//        Graph testGraph = new Graph(start, end, adj, weight);
//
//        // run
//        List<Integer> res = testGraph.routeAlgo();
//        for (int ele : res) {
//            System.out.println(ele);
//        }
//    }
}


