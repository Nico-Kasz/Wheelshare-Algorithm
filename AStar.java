package ResearchPathPlanAlg;

import ResearchPathPlanAlg.Node.Edge;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

public class AStar {
    
    /*
    In this program you can click on any two nodes and it will highlight the color of the 
    selectedNodeColor variable(currently orange). after two points are selected you can hit 
    enter, then it draws a path between the two points if possible and will print no path if 
    no path exists. If you hold shift and click on a node that node will  be selected as a midpoint.
    when a new path is draw the path will have to run from the starting node to midpoint to target node.  
    */
    
    
    //2d list of nodes list[0] will have the nodes of height 0
    //list[0][0] would be first node that is height 0 and so on
    static ArrayList<ArrayList<Node>> nodes = new ArrayList<ArrayList<Node>>();
    //node containing path 
    static Node path = new Node(0, 0, 0);
    
    static Node path2 = new Node(0, 0, 0);
    
    static Node path3 = new Node(0, 0, 0);
    
    static int costPrint = 0;
    //first node selected to plan new path 
    static Node t1 = new Node(0, 0, 0);
    //second node selected to plan new path 
    static Node t2 = new Node(0, 0, 0);
    //node that can be selected and the path has to go from the starting pont to m to the traget node
    static Node m= new Node(0, 0, 0);
    //height of the tree
    static int height = 15;
    //number of nodes in tree
    static int maxN = 10;
    //size of nodes on the screen
    static int nodeSize = 30;
    //how far each node in a differnt tree height are away horizontally
    static int xDist = 100;
    //how far each node the the same tree height are apart
    static int yDist = 100;
    //how far away each the arrow tip is away from the end of line
    static int distFromTip = 25;
    //width of arrow head
    static int headWidth = 5;
    //length of arrow head
    static int headLength = 10;
    //color of nodes
    static Color nodeColor = Color.black;
    //color of lines
    static Color lineColor = Color.red;
    //color of arrow heads
    static Color arrowColor = Color.red;
    //color of ids
    static Color idColor = Color.white;
    //color of edge costs
    static Color edgeCostColor = Color.black;
    //color of nodes in the path
    static Color pathNodeColor = Color.green;
    //color of nodes in the path
    static Color path2NodeColor = Color.blue;
    //color of nodes in the path
    static Color path3NodeColor = Color.orange;
    //color of ids for the nodes in the path
    static Color pathIDColor = Color.black;
    //color of path arrow head 
    static Color pathArrowColor = Color.cyan;
    //color of path line 
    static Color pathLineColor = Color.green;
    //color of background
    static Color backGroundColor = Color.lightGray;
    //color of node selcted
    static Color selectedNodeColor = Color.orange;
    //color of selected mid point
    static Color midPointNodeColor = Color.red;
    //font size 
    static int fontSize = 12;
    //keeps track of whether the shift key is held down or not
    static boolean shift=false;

    public static void main(String[] a) {
        //initializes selected points index to -1 which means no index
        t1.id = -1;
        t2.id = -1;
        m.id=-1;
        distFromTip=nodeSize/2;
        //sets nodes var equal to nodes
        nodes = Node.createRandomNodesAndEdges(height, maxN);
        //sets path equal to path
        //path = Node.aStar(nodes.get(0).get(0), nodes.get(nodes.size() - 1).get(0));
        
         
        //Node.num=6;
        Node.inclinePreference=6;
        path = Node.dijkstras(nodes.get(0).get(0), nodes.get(nodes.size() - 1).get(0));
        
//        Node.inclinePreference=6;
//        Node.num=6;
//        System.out.println("change");
//        path2 = Node.dijkstras(nodes.get(0).get(0), nodes.get(nodes.size() - 1).get(0));
//        Node.inclinePreference=4;
//         Node.num=20;
//         System.out.println("change2");
//        path3 = Node.dijkstras(nodes.get(0).get(0), nodes.get(nodes.size() - 1).get(0));
//        System.out.println("change 3");
//        Node.num=20;
        //prints path to commandline
        //Node.printPath(path);
        //displays path
        MyJFrame f = new MyJFrame();
        f.setBounds(0, 0, height * xDist + 200, maxN * yDist + 200);
        f.setDefaultCloseOperation(MyJFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }

    //class that draws on scren
    static class MyJFrame extends JFrame implements KeyListener, MouseListener {

        //intializes mouse and key listener
        public MyJFrame() {
            this.addKeyListener((KeyListener) this);
            this.addMouseListener((MouseListener) this);
        }

        //paints screen
        public void paint(Graphics g) {
            g.setColor(backGroundColor);
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            g.fillRect(0, 0, 1800, 1100);
            drawLines(g, xDist, yDist, lineColor, arrowColor, edgeCostColor);
            drawNodes(g, xDist, yDist, nodeColor, idColor);
            drawPath(g, xDist, yDist, pathNodeColor, pathLineColor, pathArrowColor, pathIDColor,path,1);
           /// drawPath(g, xDist, yDist, path2NodeColor, pathLineColor, pathArrowColor, pathIDColor,path2,2);
            //drawPath(g, xDist, yDist, path3NodeColor, pathLineColor, pathArrowColor, pathIDColor,path3,3);
     
//drawKey(g,200,800);
        }

        //paints nodes on screen
        public void drawNodes(Graphics g, int distX, int distY, Color nodeColor, Color textColor) {
            for (int i = 0; i < AStar.nodes.size(); i++) {
                for (Node n : AStar.nodes.get(i)) {
                    g.setColor(nodeColor);
                    g.fillOval((int) (n.x * distX), (int) (n.y * distY + 100), nodeSize, nodeSize);
                    g.setColor(textColor);
                    g.drawString(String.valueOf(n.id), (int) (distX * n.x + nodeSize/3+1), (int) (n.y * distY + 105+nodeSize/2));
                }
            }
        }

        //paints lines between nodes with cost
        public void drawLines(Graphics g, int distX, int distY, Color lineColor, Color arrowColor, Color textColor) {
            for (int i = 0; i < AStar.nodes.size(); i++) {
                for (int j = 0; j < AStar.nodes.get(i).size(); j++) {
                    Node p = AStar.nodes.get(i).get(j);
                    for (Edge n : AStar.nodes.get(i).get(j).neighbors) {

                       // g.setColor(Color.red);
                        drawArrow(g, arrowColor, lineColor, (int) (p.x * distX + nodeSize/2), (int) (p.y * distY + 100+nodeSize/2), (int) (n.node.x * distX + nodeSize/2), (int) (n.node.y * distY + 100+nodeSize/2), headWidth, headLength, distFromTip);
                        // g.drawLine((int)(p.x*distX+25), (int)(p.y*distY+125),(int)( n.node.x*distX+25), (int)(n.node.y*distY+125));
                        g.setColor(textColor);
                        if(costPrint==0){
                        g.drawString(String.valueOf((int)p.v)+","+String.valueOf(n.weight)+","+String.valueOf(n.incline)+","+String.valueOf(n.roughness)
                               , (int) (distX * (n.node.x + p.x) / 2 + 16), (int) ((n.node.y + p.y) / 2 * distY + 124));                 
                        }else if(costPrint==1){
                        g.drawString(String.valueOf((int)n.weight)
                                , (int) (distX * (n.node.x + p.x) / 2 + 16), (int) ((n.node.y + p.y) / 2 * distY + 124));
                      
                        }else if(costPrint==2){
                        g.drawString(String.valueOf((int)n.incline)
                                , (int) (distX * (n.node.x + p.x) / 2 + 16), (int) ((n.node.y + p.y) / 2 * distY + 124));
                       
                        }else if(costPrint==3){
                        g.drawString(String.valueOf((int)n.roughness)
                                , (int) (distX * (n.node.x + p.x) / 2 + 16), (int) ((n.node.y + p.y) / 2 * distY + 124));
                        }else if(costPrint==4){
                        g.drawString(String.valueOf((int)p.v)
                                , (int) (distX * (n.node.x + p.x) / 2 + 16), (int) ((n.node.y + p.y) / 2 * distY + 124));
                        }
//                        g.drawString(String.valueOf((int)p.calculateG(n))+","+String.valueOf(n.weight)+","+String.valueOf(n.incline)+","+String.valueOf(n.roughness)
//                                , (int) (distX * (n.node.x + p.x) / 2 + 16), (int) ((n.node.y + p.y) / 2 * distY + 124));
//                         g.drawString(String.valueOf((int)p.calculateG(n))
//                                , (int) (distX * (n.node.x + p.x) / 2 + 16), (int) ((n.node.y + p.y) / 2 * distY + 124));
                    }

                }
            }
        }

        //colors the path 
        public void drawPath(Graphics g, int distX, int distY, Color nodeColor, Color pathLineineColor, Color pathArowColor, Color textColor, Node path, int num) {
            if (AStar.path == null && t2.id != -1 && t1.id != -1) {
                g.setColor(selectedNodeColor);
                g.fillOval((int) (t1.x * distX), (int) (t1.y * distY + 100), nodeSize, nodeSize);
                g.fillOval((int) (t2.x * distX), (int) (t2.y * distY + 100), nodeSize, nodeSize);
                g.setColor(textColor);
                g.drawString(String.valueOf(t1.id), (int) (distX * t1.x + 20), (int) (t1.y * distY + 130));
                g.drawString(String.valueOf(t2.id), (int) (distX * t2.x + 20), (int) (t2.y * distY + 130));
            } else if (AStar.path == null && t1.id != -1 && t2.id == -1) {
                g.setColor(selectedNodeColor);
                g.fillOval((int) (t1.x * distX), (int) (t1.y * distY + 100), nodeSize, nodeSize);
                g.setColor(textColor);
                g.drawString(String.valueOf(t1.id), (int) (distX * t1.x + 20), (int) (t1.y * distY + 130));
            } else if (AStar.path != null) {
                int totalDist = 0;
                int totalCost=0;
                int avgIncline=0;
                int avgxDist=0;
                int avgRoughness=0;
                        
                Node n1 = AStar.path;
                Node n2 = AStar.path.parent;
                
                while (n1.parent != null) {
                    n2 = n1.parent;
                    for (int i = 0; i < n2.neighbors.size(); i++) {
                        if (n2.neighbors.get(i).node.id == n1.id) {
                            totalDist += n2.neighbors.get(i).weight;
                            totalCost+=n2.v;
                            avgxDist+=n2.neighbors.get(i).weight*n2.neighbors.get(i).incline;
                            break;
                        }
                    }
                    g.setColor(lineColor);
                    //g.drawLine((int)(n1.x*distX+25), (int)(n1.y*distY+125),(int)( n2.x*distX+25), (int)(n2.y*distY+125)); 
                    drawArrow(g, pathArrowColor, pathLineColor, (int) (n2.x * distX + nodeSize/2), (int) (n2.y * distY + 100+nodeSize/2), (int) (n1.x * distX + nodeSize/2), (int) (n1.y * distY + 100+nodeSize/2), headWidth, headLength, distFromTip);
                    //drawArrow(g, pathArrowColor, pathLineColor, (int) (n2.x * distX + 25), (int) (n2.y * distY + 125), (int) (n1.x * distX + 25), (int) (n1.y * distY + 125), headWidth, headLength, distFromTip);
                    g.setColor(nodeColor);
                    g.fillOval((int) (n1.x * distX), (int) (n1.y * distY + 100), nodeSize, nodeSize);
                    g.fillOval((int) (n2.x * distX), (int) (n2.y * distY + 100), nodeSize, nodeSize);
                    g.setColor(textColor);
                    g.drawString(String.valueOf(n1.id), (int) (distX * n1.x + nodeSize/3), (int) (n1.y * distY + 103+nodeSize/2));
                    g.drawString(String.valueOf(n2.id), (int) (distX * n2.x + nodeSize/3), (int) (n2.y * distY + 103+nodeSize/2));
                    n1 = n1.parent;
                }
                g.setColor(textColor);
                g.drawString("Total Path"+num+" Cost: " + totalCost, 10+200*num, 40);
                g.drawString("Total Path"+num+" Dist: " + totalDist, 10+200*num, 60);
                g.drawString("avg Path"+num+" Incline: " + 0, 10+200*num, 80);
                g.drawString("Total Path"+num+" Incline x Dist: " + avgxDist, 10+200*num, 100);
//                g.drawString("Total Path2 Cost: " + totalDist, 100, 70);
//                g.drawString("Total Path3 Cost: " + totalDist, 100, 70);
                if (t1.id != -1) {
                    g.setColor(selectedNodeColor);
                    g.fillOval((int) (t1.x * distX), (int) (t1.y * distY + 100), 50, 50);
                    g.setColor(textColor);
                    g.drawString(String.valueOf(t1.id), (int) (distX * t1.x + 20), (int) (t1.y * distY + 130));
                }
                if (t2.id != -1) {
                    g.setColor(selectedNodeColor);
                    g.fillOval((int) (t2.x * distX), (int) (t2.y * distY + 100), 50, 50);
                    g.setColor(textColor);
                    g.drawString(String.valueOf(t2.id), (int) (distX * t2.x + 20), (int) (t2.y * distY + 130));
                }
            } else {
                g.setColor(textColor);
                g.drawString("No Path Exists", 100, 70);
            }
            drawMidPoint(g);
        }
       
        //draws the midpoint
        public void drawMidPoint(Graphics g){
            if(m.id!=-1){
        g.setColor(midPointNodeColor);
        g.fillOval((int) (m.x * xDist), (int) (m.y * yDist + 100), 50, 50);
        g.setColor(idColor);
        g.drawString(String.valueOf(m.id), (int) (xDist * m.x + 20), (int) (m.y * yDist + 130));
            }
        }
        
        //draws arrow
        public void drawArrow(Graphics g, Color arrowHead, Color line, double x1, double y1, double x2, double y2, int headWidth, int headLength, int distFromTip) {
            g.setColor(line);
            headLength = headLength + distFromTip;
            int[] xPoints = new int[3];
            int[] yPoints = new int[3];
            double slope;
            if (x1 - x2 != 0) {
                slope = Math.atan((y1 - y2) / (x1 - x2));
            } else if (y1 > y2) {
                slope = -Math.PI / 2;
            } else {
                slope = Math.PI / 2;
            }

            double x = x2;
            double y = y2;
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            xPoints[0] = (int) (x2 - Math.cos(slope) * distFromTip);
            yPoints[0] = (int) (y2 - Math.sin(slope) * distFromTip);

            x = x - Math.cos(slope) * headLength;
            y = y - Math.sin(slope) * headLength;

            if (slope < Math.PI / 2 && slope > -Math.PI / 2) {
                yPoints[1] = (int) (y + Math.sin(slope + Math.PI / 2) * headWidth);
                yPoints[2] = (int) (y + Math.sin(slope - Math.PI / 2) * headWidth);
            } else {
                yPoints[1] = (int) (y - Math.sin(slope + Math.PI / 2) * headWidth);
                yPoints[2] = (int) (y - Math.sin(slope - Math.PI / 2) * headWidth);
            }
            if (slope > 0 && slope < Math.PI) {
                xPoints[1] = (int) (x + Math.cos(slope + Math.PI / 2) * headWidth);
                xPoints[2] = (int) (x + Math.cos(slope - Math.PI / 2) * headWidth);
            } else {
                xPoints[1] = (int) (x + Math.cos(slope + Math.PI / 2) * headWidth);
                xPoints[2] = (int) (x + Math.cos(slope - Math.PI / 2) * headWidth);
            }

            g.setColor(arrowHead);
            g.fillPolygon(xPoints, yPoints, 3);
        }
        
        //draws key of what colors and symbols mean
        public void drawKey(Graphics g, int x , int y){
        int distApart=200;
        g.setColor(nodeColor);
        g.fillOval(x, y, 50, 50);
        g.setColor(selectedNodeColor);
        g.fillOval(x+distApart, y, 50, 50);
        g.setColor(midPointNodeColor);
        g.fillOval(x+distApart*2, y, 50, 50);
        g.setColor(pathNodeColor);
        g.fillOval(x+distApart*3, y, 50, 50);
        
        drawArrow(g, arrowColor, lineColor, x+distApart*4, (int) y+40, x+distApart*4+70, y+40, headWidth, headLength, 0);
        drawArrow(g, arrowColor, lineColor, x+distApart*5, (int) y+40, x+distApart*5+70, y+40, headWidth, headLength, 0);
        drawArrow(g, arrowColor, lineColor, x+distApart*5+70, (int) y+40, x+distApart*5, y+40, headWidth, headLength, 0);

        
        g.setColor(Color.black);
        g.drawString("Node Color", x, y+80);
        g.drawString("Selected Node Color", x+distApart-50, y+80);
        g.drawString("Mid Point Node Color", x+distApart*2-50, y+80);
        g.drawString("Path Node Color", x+distApart*3-40, y+80);
        g.drawString("One Way Branch", x+distApart*4-30, y+80);
        g.drawString("Two Way Branch", x+distApart*5-40, y+80);
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
        }
        
        @Override// when enter pressed and two nodes are selected tries to find path between the two
        public void keyPressed(KeyEvent key) {

            if (key.getKeyCode() == KeyEvent.VK_ENTER && t1.id != -1 && t2.id != -1) {
                Node p1 = null;
                Node p2 = null;
                Node mid= null;
                for (int i = 0; i < nodes.size(); i++) {
                    for (int j = 0; j < nodes.get(i).size(); j++) {

                        if (nodes.get(i).get(j).id == t1.id) {
                            p1 = nodes.get(i).get(j);
                            p1.parent = null;
                        }
                        if (nodes.get(i).get(j).id == t2.id) {
                            p2 = nodes.get(i).get(j);
                        }
                        if(m.id == nodes.get(i).get(j).id){
                        mid=nodes.get(i).get(j);
                        mid.parent=null;
                        }
                    }
                }
                
//                if(m.id!=-1){
//                //Node temp= Node.aStar(p1, mid);
//                //path = Node.aStar(mid, p2);
//                if(temp!=null && path!=null){
//                Node n= path.parent;
//                while(n!=null){
//                n=n.parent;
//                }
//                n=temp;
//                }else{
//                path=null;
//                }
//                }else{
//                //path = Node.aStar(p1, p2);
//                }
                
                t1.id = -1;
                t2.id = -1;
                m.id=-1;
                repaint();

            }else if(key.getKeyCode() == KeyEvent.VK_A){
                costPrint=0;
                repaint();
            }else if(key.getKeyCode() == KeyEvent.VK_D){
                costPrint=1;
                repaint();
            }else if(key.getKeyCode() == KeyEvent.VK_I){
                costPrint=2;
                repaint();
            }else if(key.getKeyCode() == KeyEvent.VK_R){
                costPrint=3;
                repaint();
            }else if(key.getKeyCode() == KeyEvent.VK_C){
                costPrint=4;
                repaint();
            }
            if(key.getKeyCode()==KeyEvent.VK_SHIFT){
            shift=true;
            }
            
        }

        @Override
        public void keyReleased(KeyEvent key) {
            if(key.getKeyCode()==KeyEvent.VK_SHIFT){
            shift=false;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override// left click selected node right click deselects nodes
        //left click while 
        public void mousePressed(MouseEvent e) {
           
            
            
            for (int i = 0; i < nodes.size(); i++) {
                for (int j = 0; j < nodes.get(i).size(); j++) {
                    Node n = nodes.get(i).get(j);
                    if ( Math.abs(e.getPoint().x - (n.x * xDist + 25)) < 25 && Math.abs(e.getPoint().y - (n.y * yDist + 125)) < 25) {
                        if(SwingUtilities.isLeftMouseButton(e) ){
                        if (t1.id == -1 && !shift && m.id!=n.id) {
                            t1.x = n.x;
                            t1.y = n.y;
                            t1.id = n.id;

                        } else if (t2.id == -1 && !shift && m.id!=n.id) {
                            t2.x = n.x;
                            t2.y = n.y;
                            t2.id = n.id;

                        }else if (m.id == -1 && shift && n.id!=t1.id && n.id!=t2.id) {
                            m.x = n.x;
                            m.y = n.y;
                            m.id = n.id;
                        }
                        }else if(t1.id==n.id){
                        t1.id=-1;
                        }else if(t2.id==n.id){
                        t2.id=-1;
                        }else if(m.id==n.id){
                        m.id=-1;
                        }
                    }     
                }
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}
