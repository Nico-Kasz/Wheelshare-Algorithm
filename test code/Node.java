package ResearchPathPlanAlg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Node implements Comparable<Node> , Cloneable{
@Override
    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
    public static boolean twoWayNode=false;
    //percent chance to add branch beteen nodes in same level
    private static double neighborPathChance = .3;
    //chance to branch nodes diagonal
    private static double crossChance = .25;
    //increment after each node is made so each node has a unique id
    //starts at -2 because we created 2 node in AStar class before creating list of nodes
    private static int idCounter = -3;
    //used to have a way to identify a node
    public int id;
    
    // when creating path nodes will have a parent assigned
    public Node parent = null;
    //list of edges from current node, this holds a node and the weight(or cost it takes to get to that node)
    public List<Edge> neighbors;
    //estiamted total distance from start to target or g + h
    public double f = Double.MAX_VALUE;
    //total distance so far from start to current node
    public double g = 0;
    // estimated distance to the target from current node calculated heuristic 
    public double h;
    
     public double v;
    // estimated distance to the target from current node calculated heuristic 
    public static double inclinePreference=5;
    // estimated distance to the target from current node calculated heuristic 
    public static double roughnessPreference= 12;
    
    public static double num= 10;
    
    public boolean Astar=false;

    //x and y coordinates are the position of the nodes on the screen
    public double x;
    public double y;

    //node object has a huersitic value, x and y coordinate, unique id, and empty list of neighbor to start
    Node(double h, double x, double y) {
        this.h = h;
        this.id = idCounter++;
        this.neighbors = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    //when using the priorty queue the 
    @Override
    public int compareTo(Node n) {
        if(Astar){
         return Double.compare(this.f, n.f);
        }else{
         return Double.compare(this.g, n.g);
        }
        
        
    }

    //edge object holds a node and a weigth 
    // the cost to get from the current node to the node in the edge is the wieght value
    public static class Edge {

        Edge( Node node, int weight, double incline, double roughness) {
           
            this.weight = weight;
            this.node = node;
            this.incline=incline;
            this.roughness=roughness;
            
        }
         Edge( Node node, int weight) {
           
            this.weight = weight;
            this.node = node;
           
            
        }

        public int weight;
        public Node node;
        public double incline;
        public double roughness;
    }

    //used to add edge from current node to passed in node with a wiegth of passed in wieght
    public void addBranch(  int distance, Node node, double incline, double roughness) {
        Edge newEdge = new Edge( node, distance, incline, roughness);
        neighbors.add(newEdge);
    }

    public void addBranch( int distance, Node node) {
        Edge newEdge = new Edge( node, distance);
        neighbors.add(newEdge);
    }
    //calculates hueristic- for our case the huerisitic is hardcoded at the beginning
    public double calculateHeuristic(Node target) {
        return this.h;
    }
    
    public double calculateG(Edge road) {
       double cost=Double.MAX_VALUE;
       
       if(road.incline<inclinePreference && road.roughness<roughnessPreference){
           cost=this.g;
           cost+=road.weight*(Math.pow(road.incline, 2) + road.roughness);
       }
//       cost=num;
//        System.out.println(cost);
//        this.v=cost;
    return cost;
    }
    //function that finds path between two nodes
    public static Node aStar(Node start, Node target) {
        //two list used to find path
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();
        //starting fridge cost
        start.f = start.g + start.calculateHeuristic(target);
        //adds starting node to open list
        openList.add(start);

        //cycles through each node of open list till 
        //target is found or no target possible
        while (!openList.isEmpty()) {
            //this does alot in this line get ready
            //this peek method returns the element that has the lowest priority
            //to see each nodes priority the compareTo(Node n) mehtod is called
            //it checks the f value of each node as its priortity
            //f value is estimated distance so the node with the closest 
            //estimated distance is returned by peek
            Node n = openList.peek();

            if (n == target) {
                return n;
            }

            //cycles through each edge in the neighbors list of the last node of openlist 
            for (Node.Edge edge : n.neighbors) {
                //m==node connected to n through the edge
                Node m = edge.node;
                //total cost of path = currnet total cost + edge cost from n to m
                //double totalWeight = calculateG(edge, n);
                double totalWeight = n.g+edge.weight;
                //if openlist doesn't already contain m and closed list doesn't contain m
                // the set m's parent to node n the total cost of m=totalwieght
                //and estiamted total distance = total current distance + estiamted from m->target
                //add to open list
                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                } else {
                    //if totalWeight < current distance to m
                    //set m's parent to node n, distance from start to current node = total weight
                    //m's estimated total distand is = m.g+ h
                    if (totalWeight < m.g) {
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.g + m.calculateHeuristic(target);
                        //if m is in closed list add it to open list and remove it from closed list
                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }
            //after each loop remove n from open list and add it to closed list
            openList.remove(n);
            closedList.add(n);
        }
        //if list is empty return null
        return null;
    }

    public static Node dijkstras(Node start, Node target) {
        
        //two list used to find path
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();
        //starting fridge cost
         start.g = 0;
        //adds starting node to open list
        openList.add(start);

        //cycles through each node of open list till 
        //target is found or no target possible
        while (!openList.isEmpty()) {
            //this does alot in this line get ready
            //this peek method returns the element that has the lowest priority
            //to see each nodes priority the compareTo(Node n) mehtod is called
            //it checks the f value of each node as its priortity
            //f value is estimated distance so the node with the closest 
            //estimated distance is returned by peek
            Node n = openList.peek();

            if (n == target) {
                return n;
            }

            //cycles through each edge in the neighbors list of the last node of openlist 
            for (Node.Edge edge : n.neighbors) {
                //m==node connected to n through the edge
                Node m = edge.node;
                //total cost of path = currnet total cost + edge cost from n to m
                //double totalWeight = calculateG(edge, n);
                double totalWeight = n.calculateG(edge);
                //if openlist doesn't already contain m and closed list doesn't contain m
                // the set m's parent to node n the total cost of m=totalwieght
                //and estiamted total distance = total current distance + estiamted from m->target
                //add to open list
                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.parent = n;
                    m.g = totalWeight;
                   // m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                } else {
                    //if totalWeight < current distance to m
                    //set m's parent to node n, distance from start to current node = total weight
                    //m's estimated total distand is = m.g+ h
                    if (totalWeight < m.g) {
                        m.parent = n;
                        m.g = totalWeight;
                        //m.f = m.g + m.calculateHeuristic(target);
                        //if m is in closed list add it to open list and remove it from closed list
                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }
            //after each loop remove n from open list and add it to closed list
            openList.remove(n);
            closedList.add(n);
        }
        //if list is empty return null
        return null;
    }
    //prints path to command line
    public static List<Integer> printPath(Node target) {
        Node n = target;
        List<Integer> ids = new ArrayList<>();
        if (n == null) {
            return ids;
        }

        while (n.parent != null) {
            ids.add(n.id);
            n = n.parent;
        }
        ids.add(n.id);
        Collections.reverse(ids);

        for (int id : ids) {
            System.out.print(id + " -> ");
        }
        System.out.println("");
        return ids;
    }

    //checks if two nodes are neighbors
    public static boolean areNeighbors(Node n1, Node n2) {
        for (int i = 0; i < n1.neighbors.size(); i++) {
            if (n1.neighbors.get(i).node.id == n2.id) {
                return true;
            }

        }
        for (int i = 0; i < n2.neighbors.size(); i++) {
            if (n2.neighbors.get(i).node.id == n1.id) {
                return true;
            }
        }

        return false;
    }

    //creates random nodes and branches between them
    //we won't need this for project just useful for testing
    public static ArrayList<ArrayList<Node>> createRandomNodes(int height, int maxNodesPerLev) {
        ArrayList<ArrayList<Node>> nodes = new ArrayList<ArrayList<Node>>();

        Node head = new Node(height, 0, (double) (maxNodesPerLev - 1) / 2);
        head.g = 0;

        ArrayList<Node> headNode = new ArrayList<Node>();
        headNode.add(head);
        nodes.add(headNode);

        for (int i = 1; i < height; i++) {

            ArrayList<Node> tempNodes = new ArrayList<Node>();
            int nodeNum = 0;
            nodeNum = maxNodesPerLev;
            Random r = new Random();
            Random r2 = new Random();
            int numNodesAdded = maxNodesPerLev;
            //this can make the number of nodes per height vary some
//            if(r.nextDouble() < 0.4){
//            nodeNum--;
//            if(r2.nextDouble() < 0.4){
//            nodeNum--;
//            }
//            }

            for (int j = 0; j < nodeNum; j++) {
                Node n1 = new Node(height - i, i, j);//(double)j* (double)maxNodesPerLev/(double)nodeNum);
                tempNodes.add(n1);
            }

            // this adds branches between nodes in the same height
            for (int j = 0; j < nodeNum - 1; j++) {
                Random rand = new Random();
                Random rand2 = new Random();
                if (rand.nextDouble() < neighborPathChance && !areNeighbors(tempNodes.get(j), tempNodes.get(j + 1))) {
                    tempNodes.get(j).addBranch(rand2.nextInt(4) + 1, tempNodes.get(j + 1));
                }
                if (rand.nextDouble() > 1 - neighborPathChance && !areNeighbors(tempNodes.get(j), tempNodes.get(j + 1))) {
                    tempNodes.get(j + 1).addBranch(rand2.nextInt(4) + 1, tempNodes.get(j));
                }
            }
            //this adds branches diagonally between node in iffernet height and level
            for (int j = 0; j < nodeNum - 1; j++) {
                Random rand = new Random();
                Random rand2 = new Random();
                if (i != 1 && j < nodeNum && j < nodes.get(i - 1).size() - 1) {
                    if (rand.nextDouble() < crossChance && !areNeighbors(tempNodes.get(j), nodes.get(i - 1).get(j + 1))
                            && !areNeighbors(tempNodes.get(j + 1), nodes.get(i - 1).get(j))) {
                        nodes.get(i - 1).get(j + 1).addBranch(rand2.nextInt(4) + 1, tempNodes.get(j));
                    }
                    if (rand.nextDouble() < crossChance && !areNeighbors(tempNodes.get(j + 1), nodes.get(i - 1).get(j))
                            && !areNeighbors(tempNodes.get(j), nodes.get(i - 1).get(j + 1))) {
                        nodes.get(i - 1).get(j).addBranch(rand2.nextInt(4) + 1, tempNodes.get(j + 1));
                    }
                }

            }
            if (i > 1) {
                for (int j = 0; j < Math.max(nodes.get(i - 1).size(), nodeNum); j++) {
                    Random rand = new Random();
                    if (j >= nodeNum && j < nodes.get(i - 1).size()) {
                        nodes.get(i - 1).get(j).addBranch(rand.nextInt(10) + 1, tempNodes.get(j - j % nodeNum - 1));
                    } else if (j < nodeNum && j >= nodes.get(i - 1).size()) {
                        nodes.get(i - 1).get(j - j % nodes.get(i - 1).size() - 1).addBranch(rand.nextInt(10) + 1, tempNodes.get(j));
                    } else {
                        nodes.get(i - 1).get(j % nodes.get(i - 1).size()).addBranch(rand.nextInt(10) + 1, tempNodes.get(j % nodeNum));
                    }
                }
            } else {
                for (int j = 0; j < nodeNum; j++) {
                    Random rand = new Random();
                    nodes.get(i - 1).get(0).addBranch(rand.nextInt(10) + 1, tempNodes.get(j));

                }
            }
            nodes.add(tempNodes);

        }

        Random rand = new Random();
        Node target = new Node(0, nodes.size(), (double) (maxNodesPerLev - 1) / 2);
        for (int i = 0; i < nodes.get(nodes.size() - 1).size(); i++) {
            nodes.get(nodes.size() - 1).get(i).addBranch(rand.nextInt(10) + 1, target);
        }
        ArrayList<Node> targ = new ArrayList<Node>();
        targ.add(target);
        nodes.add(targ);

        return nodes;
    }
    
    
    public static ArrayList<ArrayList<Node>> createRandomNodesAndEdges(int height, int maxNodesPerLev) {
        ArrayList<ArrayList<Node>> nodes = new ArrayList<ArrayList<Node>>();

        Node head = new Node(height, 0, (double) (maxNodesPerLev - 1) / 2);
        head.g = 0;

        ArrayList<Node> headNode = new ArrayList<Node>();
        headNode.add(head);
        nodes.add(headNode);

        for (int i = 1; i < height; i++) {

            ArrayList<Node> tempNodes = new ArrayList<Node>();
            int nodeNum = 0;
            nodeNum = maxNodesPerLev;
            Random r = new Random();
            Random r2 = new Random();
            int numNodesAdded = maxNodesPerLev;
            //this can make the number of nodes per height vary some
//            if(r.nextDouble() < 0.4){
//            nodeNum--;
//            if(r2.nextDouble() < 0.4){
//            nodeNum--;
//            }
//            }

            for (int j = 0; j < nodeNum; j++) {
                Node n1 = new Node(height - i, i, j);//(double)j* (double)maxNodesPerLev/(double)nodeNum);
                tempNodes.add(n1);
            }

            // this adds branches between nodes in the same height
            for (int j = 0; j < nodeNum - 1; j++) {
                Random rand = new Random();
                Random rand2 = new Random();
                 Random rand3 = new Random();
                Random rand4 = new Random();
                if (rand.nextDouble() < neighborPathChance && !areNeighbors(tempNodes.get(j), tempNodes.get(j + 1))) {
                    tempNodes.get(j).addBranch(rand2.nextInt(4) + 1, tempNodes.get(j + 1), rand3.nextInt(7) , rand4.nextInt(15));
                }
                if (rand.nextDouble() > 1 - neighborPathChance && !areNeighbors(tempNodes.get(j), tempNodes.get(j + 1))) {
                    tempNodes.get(j + 1).addBranch(rand2.nextInt(4) + 1, tempNodes.get(j), rand3.nextInt(7) , rand4.nextInt(15));
                }
            }
            //this adds branches diagonally between node in iffernet height and level
            for (int j = 0; j < nodeNum - 1; j++) {
                Random rand = new Random();
                Random rand2 = new Random();
                 Random rand3 = new Random();
                Random rand4 = new Random();
                if (i != 1 && j < nodeNum && j < nodes.get(i - 1).size() - 1) {
                    if (rand.nextDouble() < crossChance && !areNeighbors(tempNodes.get(j), nodes.get(i - 1).get(j + 1))
                            && !areNeighbors(tempNodes.get(j + 1), nodes.get(i - 1).get(j))) {
                        nodes.get(i - 1).get(j + 1).addBranch(rand2.nextInt(4) + 1, tempNodes.get(j), rand3.nextInt(7) , rand4.nextInt(15));
                    }
                    if (rand.nextDouble() < crossChance && !areNeighbors(tempNodes.get(j + 1), nodes.get(i - 1).get(j))
                            && !areNeighbors(tempNodes.get(j), nodes.get(i - 1).get(j + 1))) {
                        nodes.get(i - 1).get(j).addBranch(rand2.nextInt(4) + 1, tempNodes.get(j + 1), rand3.nextInt(7) , rand4.nextInt(15));
                    }
                }

            }
            if (i > 1) {
                for (int j = 0; j < Math.max(nodes.get(i - 1).size(), nodeNum); j++) {
                    Random rand = new Random();
                     Random rand3 = new Random();
                Random rand4 = new Random();
                    if (j >= nodeNum && j < nodes.get(i - 1).size()) {
                        nodes.get(i - 1).get(j).addBranch(rand.nextInt(10) + 1, tempNodes.get(j - j % nodeNum - 1), rand3.nextInt(7) , rand4.nextInt(15));
                    } else if (j < nodeNum && j >= nodes.get(i - 1).size()) {
                        nodes.get(i - 1).get(j - j % nodes.get(i - 1).size() - 1).addBranch(rand.nextInt(10) + 1, tempNodes.get(j), rand3.nextInt(7) , rand4.nextInt(15));
                    } else {
                        nodes.get(i - 1).get(j % nodes.get(i - 1).size()).addBranch(rand.nextInt(10) + 1, tempNodes.get(j % nodeNum), rand3.nextInt(7) , rand4.nextInt(15));
                    }
                }
            } else {
                for (int j = 0; j < nodeNum; j++) {
                    Random rand = new Random();
                     Random rand3 = new Random();
                Random rand4 = new Random();
                    nodes.get(i - 1).get(0).addBranch(rand.nextInt(10) + 1, tempNodes.get(j), rand3.nextInt(7) , rand4.nextInt(15));

                }
            }
            nodes.add(tempNodes);

        }

        Random rand = new Random();
         Random rand3 = new Random();
                Random rand4 = new Random();
        Node target = new Node(0, nodes.size(), (double) (maxNodesPerLev - 1) / 2);
        for (int i = 0; i < nodes.get(nodes.size() - 1).size(); i++) {
            nodes.get(nodes.size() - 1).get(i).addBranch(rand.nextInt(10) + 1, target, rand3.nextInt(7) , rand4.nextInt(15));
        }
        ArrayList<Node> targ = new ArrayList<Node>();
        targ.add(target);
        nodes.add(targ);

        return nodes;
    }
    
}
