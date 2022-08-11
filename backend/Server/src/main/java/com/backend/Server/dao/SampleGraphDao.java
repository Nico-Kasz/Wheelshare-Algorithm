package com.backend.Server.dao;

import com.backend.Server.model.Direction;
import com.backend.Server.model.Poi;
import lombok.Getter;
import org.springframework.stereotype.Repository;

// use for parsing json
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Repository("GraphData")
public class SampleGraphDao implements GraphDao{
//    // testing
//    Set<String> listing = new HashSet<>();
    // instance variables
    private final Map<Integer, Poi> idToPOI;
    private final Map<Long, Integer> refToId;
    private Map<String, Double> surfaceType;
    // instance variables for graph
    Map<Integer, List<Integer>> adj;
    Map<Integer, List<Double>> weights;
    Set<Integer> listOfNodes;

    // constructor
    public SampleGraphDao() {
        // conversion data
        this.idToPOI = new HashMap<>();
        this.refToId = new HashMap<>();
        this.listOfNodes = new HashSet<>();
        // surface type
        this.surfaceInit();
        // testing on where are we
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        // parse the graph from data
        String path = "./map.json";
        // graph data
        this.readJSON(path);
    }

    public void surfaceInit() {
        this.surfaceType = new HashMap<>();
        this.surfaceType.put("asphalt", 0.9);
        this.surfaceType.put("concrete", 0.8);
        this.surfaceType.put("paved", 0.7);
        this.surfaceType.put("cobblestone", 0.7);
        this.surfaceType.put("paving_stones", 0.6);
        this.surfaceType.put("ground", 0.6);
        this.surfaceType.put("artificial_turf", 0.5);
        this.surfaceType.put("bricks", 0.4);
        this.surfaceType.put("sand", 0.4);
        this.surfaceType.put("grass", 0.3);
        this.surfaceType.put("dirt", 0.3);
    }

    // lists of useful methods
    @Override
    public Map<Integer, List<Integer>> constructGraph() {
        return this.adj;
    }

    @Override
    public Map<Integer, List<Double>> constructWeight() {
        return this.weights;
    }

    @Override
    public int[] metaGraph(Direction direction) {
        // Find the start and end node on the graph
        // based on the user's request of start and end node
        Poi startPoint = direction.getSource(), endPoint = direction.getDestination();
        double startDist = Double.MAX_VALUE, endDist = Double.MAX_VALUE;
        int chosenStart = -1 , chosenEnd = -1;
        for (int node : this.listOfNodes) {
            // get the point
            Poi thisPoint = this.idToPOI.get(node);
            // update start point
            if (this.adj.containsKey(node) && thisPoint.distance(startPoint) < startDist) {
                startDist = thisPoint.distance(startPoint);
                chosenStart = node;
            }
            // update end point
            if (thisPoint.distance(endPoint) < endDist) {
                endDist = thisPoint.distance(endPoint);
                chosenEnd = node;
            }
        }
        // check
        if (chosenStart == -1 || chosenEnd == -1) {
            System.err.println("Error in building the start and end nodes for graph");
        }

        // testing
        for (Map.Entry<Long, Integer> entry : this.refToId.entrySet()) {
            if (entry.getValue().equals(chosenStart)) {
                System.out.println("Start ref: " + entry.getKey());
            }
            if (entry.getValue().equals(chosenEnd)) {
                System.out.println("End ref: " + entry.getKey());
            }
        }
        System.out.println("Start: " + this.idToPOI.get(chosenStart));
        System.out.println("End: " + this.idToPOI.get(chosenEnd));
        // result
        return new int[] {chosenStart, chosenEnd};
    }

    @Override
    public List<Poi> match(List<Integer> path) {
        List<Poi> listOfPoints = new ArrayList<>();
        for (int node : path) {
            listOfPoints.add(this.idToPOI.get(node));
        }
        return listOfPoints;
    }

    @Override
    public Map<Integer, Poi> getMapping() {
        return this.idToPOI;
    }

    public double getScore(double roadDiff, double slope) {
        double safety = roadDiff * 40 + ((20 - Math.abs(slope)) / 20.0 * 40) + 20;
        double difficulty = roadDiff * 40 + ((20 - Math.abs(slope)) / 20.0 * 30) + 30;
        return safety * 0.6 + difficulty * 0.4;
    }

    public void processNodes(JSONArray nodes) {
        // Prepare
        this.adj = new HashMap<>();
        this.weights = new HashMap<>();

        // Process each node
        int id = -1;
        for (Object element : nodes) {
            // Cast
            JSONObject node = (JSONObject) element;

            // Prepare necessary variables
            // equivalent id for this node
            id++;
            // equivalent poi of this node
            Poi point = new Poi(Double.parseDouble((String) node.get("_lon")),
                    Double.parseDouble((String) node.get("_lat")));
            // equivalent reference of this node
            Long reference = Long.parseLong((String) node.get("_id"));

            // Put the info into map
            this.idToPOI.put(id, point);
            this.refToId.put(reference, id);
        }
    }

    public double getWeight(JSONObject way) {
        // Prepare the statistics
        Map<String, String> info = new HashMap<>();
        info.put("incline", "0");
        info.put("surface", "#");
        info.put("access", "#");
        info.put("wheelchair", "#");

        // Get the information to calculate the weight
        JSONArray tags = (JSONArray) way.get("tag");
        if (tags != null) {
            for (Object ele : tags) {
                if (ele == null) {
                    continue;
                }
                JSONObject tag = (JSONObject) ele;
                String key = (String) tag.get("_k");
                String value = (String) tag.get("_v");
                // some check
                if (key.equals("incline")) {
                    try {
                        Double.parseDouble(value);
                    } catch(NumberFormatException e) {
                        value = "0";
                    }
                }
                // grass check - now, we don't allow grass cross out
                if (key.equals("landuse") && value.equals("grass")) {
                    // mark this as not accessible for wheelchair
                    info.put("wheelchair", "no");
                }
                // high way check cross out
                if (key.equals("highway")
                        && (value.equals("tertiary")
                        || value.equals("primary")
                        || value.equals("secondary"))) {
                    // mark this as not accessible for wheelchair
                    info.put("wheelchair", "no");
                }
                // building check cross out
                if (key.equals("building") && value.equals("university")) {
                    info.put("wheelchair", "no");
                }
                // under construction cross out
//                if (key.equals("construction") && value.equals("footway")) {
//                    info.put("wheelchair", "no");
//                }
                // add to the hashmap
                if (info.containsKey(key)) {
                    info.put(key, value);
                }
            }
        }
        // Calculate the weight
        // if the whole way is not accessible for wheelchair user, then return -1
        if (info.get("access").equals("private") || info.get("wheelchair").equals("no")) {
            return -1;
        }
        // otherwise, start the calculation
        // slope score
        double slope = Double.parseDouble(info.get("incline"));
        // road score based on quality of the road
        String surface = info.get("surface");
        double roadDiff = 0.5;
        if (!surface.equals("#")) {
//            // testing
//            this.listing.add(surface);
            roadDiff = this.surfaceType.get(surface);
        }
        return this.getScore(roadDiff, slope);
    }

    public void processWays(JSONArray ways) {
        for (Object element : ways) {
            // Cast
            JSONObject way = (JSONObject) element;

            // Get the weight of this whole way
            double weight = this.getWeight(way);
            // if the whole way is not accessible for wheelchair
            if (weight == -1) {
                continue;
            }

            // Building the edge and add the weight
            JSONArray connectingNodes = (JSONArray) way.get("nd");
            for (int i = 0; i < connectingNodes.size() - 1; i++) {
                // Get out the necessary info of each node
                // node 1
                JSONObject node1 = (JSONObject) connectingNodes.get(i);
                Long reference1 = Long.parseLong((String) node1.get("_ref"));
                int id1 = this.refToId.get(reference1);
                // node 2
                JSONObject node2 = (JSONObject) connectingNodes.get(i + 1);
                Long reference2 = Long.parseLong((String) node2.get("_ref"));
                int id2 = this.refToId.get(reference2);

                // System.out.println(id1 + ", " + id2);

                // Add to the pool of used nodes
                this.listOfNodes.add(id1);
                if (i == connectingNodes.size() - 2) {  // if this is the last pair of nodes
                    this.listOfNodes.add(id2);
                }

                // Connect edge and weight
                adj.computeIfAbsent(id1, k -> new ArrayList<>()).add(id2);
                weights.computeIfAbsent(id1, k -> new ArrayList<>()).add(weight);
                adj.computeIfAbsent(id2, k -> new ArrayList<>()).add(id1);
                weights.computeIfAbsent(id2, k -> new ArrayList<>()).add(weight);
            }
        }
//        // testing
//        System.out.println(this.listing);
    }

    // Reading data from JSON file
    public void readJSON(String fileName) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName))
        {
            // Parse java file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONObject data = (JSONObject) obj.get("osm");

            // Go to the nodes world
            JSONArray nodes = (JSONArray) data.get("node");
            this.processNodes(nodes);

            // Go to the way
            JSONArray ways = (JSONArray) data.get("way");
            this.processWays(ways);
        } catch (ParseException e) {
            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public static void main(String[] args) {
//        SampleGraphDao testing = new SampleGraphDao();
//    }
}
