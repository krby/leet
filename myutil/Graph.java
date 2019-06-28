package myutil;

import java.util.*;

public class Graph {

    public class Node {
        public int data;
        public ArrayList<Node> children;

        Node (int n) {
            data = n;
            children = new ArrayList<Node>();
        }
    }

    public Node[] nodes;

    public Graph(int vertices) {
        nodes = new Node[vertices];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i);
        }
    }

    public void addEdge(int src, int dest) {
        nodes[src].children.add(nodes[dest]); // gotta add nodes!
    }

}

