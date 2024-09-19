package graph.search;

import graph.UndirectedGraph;
import graph.edge.Edge;
import graph.node.Node;
import graph.node.UndirectedNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import utils.Vector3;

public class GraphSearch_AStar extends GraphSearch<UndirectedGraph> {
    public GraphSearch_AStar(UndirectedGraph graph) {
        super(graph);
    }

    @Override
    public List<Integer> search(int source, int destination) {
        Map<Integer, Integer> gScore = new HashMap<>(); // Cost from start to the current node
        Map<Integer, Integer> fScore = new HashMap<>(); // Estimated cost from start to goal through the current node
        Map<Integer, Integer> previous = new HashMap<>();  // To reconstruct the path
        PriorityQueue<Integer> openSet = new PriorityQueue<>(Comparator.comparingInt(fScore::get));

        // Initialize scores
        for (UndirectedNode node : graph.getNodes().values()) {
            gScore.put(node.getId(), Integer.MAX_VALUE);
            fScore.put(node.getId(), Integer.MAX_VALUE);
            previous.put(node.getId(), -1);
        }
        gScore.put(source, 0);
        fScore.put(source, heuristic(graph.getNodeById(source), graph.getNodeById(destination)));

        openSet.add(source);

        while (!openSet.isEmpty()) {
            int currentNodeId = openSet.poll();
            UndirectedNode currentNode = graph.getNodeById(currentNodeId);

            if (currentNodeId == destination) {
                break;
            }

            for (Map.Entry<Integer, Integer> entry : currentNode.getAdjNodes().entrySet()) {
                int neighborId = entry.getKey();
                int edgeId = entry.getValue();
                Edge edge = graph.getEdgeById(edgeId);

                int tentativeGScore = gScore.get(currentNodeId) + edge.getCost();

                if (tentativeGScore < gScore.get(neighborId)) {
                    gScore.put(neighborId, tentativeGScore);
                    previous.put(neighborId, currentNodeId);
                    fScore.put(neighborId, gScore.get(neighborId) + heuristic(graph.getNodeById(neighborId), graph.getNodeById(destination)));

                    if (!openSet.contains(neighborId)) {
                        openSet.add(neighborId);
                    }
                }
            }
        }

        // Reconstruct path
        List<Integer> path = new ArrayList<>();
        for (Integer at = destination; at != -1; at = previous.get(at)) {
            path.add(at);
        }
        // path.add(source);
        Collections.reverse(path);
        return path;
    }

    // Heuristic function, estimates the cost from node to the goal (Euclidean distance)
    private int heuristic(Node node, Node goal) {
        Vector3 pos1 = node.getPosition();
        Vector3 pos2 = goal.getPosition();
        return (int) Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) +
                               Math.pow(pos1.getY() - pos2.getY(), 2) +
                               Math.pow(pos1.getZ() - pos2.getZ(), 2));
    }
}
