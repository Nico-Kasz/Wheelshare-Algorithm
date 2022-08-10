//package com.backend.Server.service;
//
//import java.util.*;
//
////if design in OOP->consider each node as a class with accordingly properties
//public class GraphTest {
//    // Inner class of weight objects
//    class Edge implements Comparable<Edge> {
//        // instance variables
//        private final double weight;
//        private final int start, end;
//
//        Edge(double weight, int start, int end) {
//            this.weight = weight;
//            this.start = start;
//            this.end = end;
//        }
//
//        @Override
//        public int compareTo(Edge o) {
//            return (int)(o.weight - this.weight);
//        }
//
//        @Override
//        public String toString() {
//            return String.format("(%f, %d, %d)", weight, start, end);
//        }
//    }
//
//    // Instance variable
//    // number of vertices
//    private int V, start, end;
//    // adjacency matrix and weight
//    private List<List<Integer>> adj;  // of type ArrayList<LinkedList<Integer>>
//    private List<List<Double>> weights;
//
//
//    // constructor
//    public GraphTest(int V, int start, int end, List<List<Integer>> adj, List<List<Double>> weight) {
//        this.V = V;
//        this.start = start;
//        this.end = end;
//        this.adj = adj;
//        this.weights = weight;
//    }
//
//    public List<Integer> routeAlgo() {
//        // Prepare
//        // keep track of visited nodes and also the predecessor node
//        Map<Integer, List<Integer>> preNode = new HashMap<>();
//        PriorityQueue<Edge> maxHeap = new PriorityQueue<>();
//        List<Double> bottleneck = new ArrayList<>();  // fix null value later
//
//        // add start point to hashmap
//        preNode.put(start, new ArrayList<>());
////        bottleneck.add(start, );
//        // Prepopulate heap with edges starting from the start point
//        for (int i = 0; i < adj.get(start).size(); i++) {
//            // prepare
//            int nextNode = adj.get(start).get(i);
//            double weight = weights.get(start).get(i);
//            // add to heap
//            maxHeap.add(new Edge(weight, start, nextNode));
//        }
//
//        // Let's go
//        while (maxHeap.size() > 0 && (!preNode.containsKey(end) || maxHeap.peek().weight >= bottleneck.get(end))) {
//            // get out the max edge
//            Edge maxEdge = maxHeap.remove();
////            // test
////            System.out.println(maxEdge);
////            // test
//            int oldNode = maxEdge.start; int newNode = maxEdge.end;
//            // add this new node to hashmap
//            if (!preNode.containsKey(newNode)) {
//                preNode.put(newNode, new ArrayList<>());
//            }
//            preNode.get(newNode).add(oldNode);
//            // update bottleneck
//
//            // here
//
//            // explore the adjacency nodes
//            for (int i = 0; i < adj.get(newNode).size(); i++) {
//                // prepare
//                int nextNode = adj.get(newNode).get(i);
//                double weight = weights.get(newNode).get(i);
//                // only explore unvisited nodes
//                if (preNode.containsKey(nextNode)) {
//                    continue;
//                }
//                // add to heap
//                maxHeap.add(new Edge(weight, newNode, nextNode));
//            }
//        }
//
//        // Printing path by backtracking
//        LinkedList<Integer> result = new LinkedList<Integer>();
//        int lastNode = end;
//        while (preNode.containsKey(lastNode)) {
//            // add to result on the left side
//            result.addFirst(lastNode);
//            // update last node
//            lastNode = preNode.get(lastNode);
//        }
//        // result
//        return result;
//    }
//
////    public static void main(String[] args)
////    {
////        // mocking data
////        int V = 5;
////        int start = 1, end = 5;
////        // mocking adjacency matrix and weight
////        // adj
////        List<List<Integer>> adj = new ArrayList<>();
////        adj.add(new LinkedList<Integer>());  // index 0
////        adj.add(new LinkedList<Integer>(Arrays.asList(2, 3)));
////        adj.add(new LinkedList<Integer>(Arrays.asList(1, 3, 4)));
////        adj.add(new LinkedList<Integer>(Arrays.asList(1, 2, 5, 4)));
////        adj.add(new LinkedList<Integer>(Arrays.asList(2, 3, 5)));
////        adj.add(new LinkedList<Integer>(Arrays.asList(3, 4)));
////
////
////        // weight
////        List<List<Double>> weight = new ArrayList<>();
////        weight.add(new LinkedList<Double>());  // index 0
////        weight.add(new LinkedList<Double>(Arrays.asList(3.0, 10.0)));
////        weight.add(new LinkedList<Double>(Arrays.asList(3.0, 5.0, 4.0)));
////        weight.add(new LinkedList<Double>(Arrays.asList(10.0, 5.0, 1.0, 1.0)));
////        weight.add(new LinkedList<Double>(Arrays.asList(3.0, 1.0, 4.0)));
////        weight.add(new LinkedList<Double>(Arrays.asList(1.0, 4.0)));
////
////        // initialize
////        Graph testGraph = new Graph(V, start, end, adj, weight);
////
////        // run
////        List<Integer> res = testGraph.routeAlgo();
////        for (int ele : res) {
////            System.out.println(ele);
////        }
////    }
//}
//
//
