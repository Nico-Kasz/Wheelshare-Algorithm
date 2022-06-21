package com.backend.Server.service;

import java.util.*;

//if design in OOP->consider each node as a class with accordingly properties
public class Graph {
    // Inner class of weight objects
    class Edge implements Comparable<Edge> {
        // instance variables
        private final double weight;
        private final int start, end;

        Edge(double weight, int start, int end) {
            this.weight = weight;
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Edge o) {
            return (int)(o.weight - this.weight);
        }

        @Override
        public String toString() {
            return String.format("(%f, %d, %d)", weight, start, end);
        }
    }

    // Instance variable
    // number of vertices
    private int V, start, end;
    // adjacency matrix and weight
    private List<List<Integer>> adj, weights;  // of type ArrayList<LinkedList<Integer>>


    // constructor
    public Graph(int V, int start, int end, List<List<Integer>> adj, List<List<Integer>> weight) {
        this.V = V;
        this.start = start;
        this.end = end;
        this.adj = adj;
        this.weights = weight;
    }

    public List<Integer> routeAlgo() {
        // Prepare
        // keep track of visited nodes and also the predecessor node
        Map<Integer, Integer> preNode = new HashMap<Integer, Integer>();
        PriorityQueue<Edge> maxHeap = new PriorityQueue<>();

        // add start point to hashmap
        preNode.put(start, -1);
        // Prepopulate heap with edges starting from the start point
        for (int i = 0; i < adj.get(start).size(); i++) {
            // prepare
            int nextNode = adj.get(start).get(i);
            int weight = weights.get(start).get(i);
            // add to heap
            maxHeap.add(new Edge(weight, start, nextNode));
        }

        // Let's go
        while (!preNode.containsKey(end) && maxHeap.size() > 0) {
            // get out the max edge
            Edge maxEdge = maxHeap.remove();
            // test
            System.out.println(maxEdge);
            // test
            int oldNode = maxEdge.start; int newNode = maxEdge.end;
            // add this new node to hashmap
            preNode.put(newNode, oldNode);
            // explore the adjacency nodes
            for (int i = 0; i < adj.get(newNode).size(); i++) {
                // prepare
                int nextNode = adj.get(newNode).get(i);
                int weight = weights.get(newNode).get(i);
                // only explore unvisited nodes
                if (preNode.containsKey(nextNode)) {
                    continue;
                }
                // add to heap
                maxHeap.add(new Edge(weight, newNode, nextNode));
            }
        }

        // Printing path
        LinkedList<Integer> result = new LinkedList<Integer>();
        int lastNode = end;
        while (preNode.containsKey(lastNode)) {
            // add to result on the left side
            result.addFirst(lastNode);
            // update last node
            lastNode = preNode.get(lastNode);
        }
        // result
        return  result;
    }

    public static void main(String[] args)
    {
        // mocking data
        int V = 5;
        int start = 1, end = 5;
        // mocking adjacency matrix and weight
        // adj
        List<List<Integer>> adj = new ArrayList<>();
        adj.add(new LinkedList<Integer>());  // index 0
        adj.add(new LinkedList<Integer>(Arrays.asList(2, 3)));
        adj.add(new LinkedList<Integer>(Arrays.asList(1, 3, 4)));
        adj.add(new LinkedList<Integer>(Arrays.asList(1, 2, 5, 4)));
        adj.add(new LinkedList<Integer>(Arrays.asList(2, 3, 5)));
        adj.add(new LinkedList<Integer>(Arrays.asList(3, 4)));


        // weight
        List<List<Integer>> weight = new ArrayList<>();
        weight.add(new LinkedList<Integer>());  // index 0
        weight.add(new LinkedList<Integer>(Arrays.asList(3, 10)));
        weight.add(new LinkedList<Integer>(Arrays.asList(3, 5, 4)));
        weight.add(new LinkedList<Integer>(Arrays.asList(10, 5, 1, 1)));
        weight.add(new LinkedList<Integer>(Arrays.asList(3, 1, 4)));
        weight.add(new LinkedList<Integer>(Arrays.asList(1, 4)));

        // initialize
        Graph testGraph = new Graph(V, start, end, adj, weight);

        // run
        List<Integer> res = testGraph.routeAlgo();
        for (int ele : res) {
            System.out.println(ele);
        }
    }
}


