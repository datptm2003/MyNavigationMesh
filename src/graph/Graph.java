package graph;

import graph.edge.Edge;
import graph.node.Node;
import java.util.HashMap;
import java.util.Map;

public abstract class Graph<N extends Node, E extends Edge> {
    private static int NEXT_NODE_ID = 0;
    private static int NEXT_EDGE_ID = 0;

    private final Map<Integer, N> nodes;
    private final Map<Integer, E> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    public static int getNextNodeId() {
        return NEXT_NODE_ID;
    }
    public static int getNextEdgeId() {
        return NEXT_EDGE_ID;
    }

    public Map<Integer, N> getNodes() {
        return nodes;
    }
    public Map<Integer, E> getEdges() {
        return edges;
    }

    public N getNodeById(int id) {
        if (!nodes.containsKey(id)) {
            System.out.println("Certain node ID hasn't been registered yet!");
            return null;
        }
        return nodes.get(id);
    }
    public E getEdgeById(int id) {
        if (!edges.containsKey(id)) {
            System.out.println("Certain edge ID hasn't been registered yet!");
            return null;
        }
        return edges.get(id);
    }

    public void addNode(N node) {
        if (nodes.containsKey(node.getId())) {
            System.out.println("Certain node ID has been registered already!");
            return;
        }
        nodes.put(node.getId(), node);
        NEXT_NODE_ID++;
    }
    public void addEdge(E edge) {
        if (edges.containsKey(edge.getId())) {
            System.out.println("Certain edge ID has been registered already!");
            return;
        }
        edges.put(edge.getId(), edge);
        NEXT_EDGE_ID++;
    }

    public abstract void bindRelation(int outNodeId, int inNodeId, int relationId);
}
