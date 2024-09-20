package graph.search;

import graph.UndirectedGraph;
import graph.edge.Edge;
import graph.node.UndirectedNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraGraphSearch<N extends UndirectedNode> extends GraphSearch<UndirectedGraph<N>> {
    public DijkstraGraphSearch(UndirectedGraph<N> graph) {
        super(graph);
    }

    @Override
    public List<Integer> search(int source, int destination) {
        Map<Integer, Double> distances = new HashMap<>(); // Stores distances from source
        Map<Integer, Integer> previous = new HashMap<>();  // Stores previous nodes in optimal path
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        // Initialize distances
        for (UndirectedNode node : graph.getNodes().values()) {
            distances.put(node.getId(), Double.MAX_VALUE);
            previous.put(node.getId(), -1);
        }
        distances.put(source, 0d);

        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            int currentNodeId = priorityQueue.poll();
            UndirectedNode currentNode = graph.getNodeById(currentNodeId);

            if (currentNodeId == destination) {
                break;
            }

            for (Map.Entry<Integer, Integer> entry : currentNode.getAdjNodes().entrySet()) {
                int neighborId = entry.getKey();
                if (neighborId == currentNodeId) continue;

                int edgeId = entry.getValue();
                Edge edge = graph.getEdgeById(edgeId);

                double newDist = distances.get(currentNodeId) + edge.getCost();

                if (newDist < distances.get(neighborId)) {
                    distances.put(neighborId, newDist);
                    previous.put(neighborId, currentNodeId);
                    priorityQueue.add(neighborId);
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
}
