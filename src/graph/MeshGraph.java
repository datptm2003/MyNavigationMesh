package graph;

import graph.edge.Edge;
import graph.node.PolygonNode;
import java.util.ArrayList;
import java.util.List;
import utils.Mesh;
import utils.Polygon;

public class MeshGraph extends UndirectedGraph<PolygonNode> {
    public MeshGraph(Mesh mesh) {
        super();
        generateGraph(mesh);
    }

    private void generateGraph(Mesh mesh) {
        // Extract vertices and indices from the mesh
        float[] vertices = mesh.getVertices();
        int[] indices = mesh.getIndices();

        // Create nodes for each polygon
        List<PolygonNode> nodes = createNodes(vertices, indices);

        // Add nodes to the graph
        for (PolygonNode node : nodes) {
            this.addNode(node);
        }

        // Create edges between nodes sharing common edges and add them to the graph
        createEdges(nodes);
    }

    private List<PolygonNode> createNodes(float[] vertices, int[] indices) {
        List<PolygonNode> nodes = new ArrayList<>();
        int nodeId = 0;

        // Create nodes based on polygons (using sets of indices)
        for (int i = 0; i < indices.length; i += 3) { // Assuming triangles for simplicity
            // float[] center = calculateCenter(vertices, indices, i);
            float[] polygonVertices = new float[3 * 3];
            for (int j = 0; j < 3; ++j) {
                polygonVertices[j * 3] = vertices[indices[i + j] + j * 3];
                polygonVertices[j * 3 + 1] = vertices[indices[i + j] + j * 3 + 1];
                polygonVertices[j * 3 + 2] = vertices[indices[i + j] + j * 3 + 2];
            }
            
            int[] polygonIndices = { indices[i], indices[i + 1], indices[i + 2] };
            PolygonNode node = new PolygonNode(nodeId++, new Polygon(polygonVertices, polygonIndices));
            nodes.add(node);
        }

        return nodes;
    }

    private void createEdges(List<PolygonNode> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                if (sharesCommonEdge(nodes.get(i), nodes.get(j))) {
                    double cost = calculateDistance(nodes.get(i).getCenter(), nodes.get(j).getCenter());
                    Edge edge = new Edge(Graph.getNextEdgeId(), cost);
                    this.addEdge(edge);
                    this.bindRelation(nodes.get(i).getId(), nodes.get(j).getId(), i);
                }
            }
        }
    }

    private boolean sharesCommonEdge(PolygonNode node1, PolygonNode node2) {
        // Check if two polygons share a common edge based on indices
        int[] indices1 = node1.getIndices();
        int[] indices2 = node2.getIndices();

        int sharedVertices = 0;
        for (int i : indices1) {
            for (int j : indices2) {
                if (i == j) {
                    sharedVertices++;
                    if (sharedVertices == 2) { // Two shared vertices mean a common edge
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private double calculateDistance(float[] center1, float[] center2) {
        // Calculate the Euclidean distance between two centers
        double dx = center1[0] - center2[0];
        double dy = center1[1] - center2[1];
        double dz = center1[2] - center2[2];
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
