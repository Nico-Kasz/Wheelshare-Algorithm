package com.backend.Server.service;

import com.backend.Server.dao.GraphDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//if design in OOP->consider each node as a class with accordingly properties
public class Graph {
    // Instance variable
    // number of vertices
    private int V, start, end;
    // adjacency matrix
    private List<List<Integer>> adj;  // of type ArrayList<LinkedList<Integer>>

    // constructor
    public Graph(int V, int start, int end, List<List<Integer>> adj) {
        this.V = V;
        this.start = start;
        this.end = end;
        this.adj = adj;
    }

    public List<Integer> routeAlgo() {
        return new LinkedList<Integer>();
    }

    public static void main(String[] args)
    {
        System.out.println("Main testing");
    }
}


