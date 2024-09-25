package utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Mesh {
    private float[] vertices; // Vertex positions (x, y, z)
    private int[] indices;    // Indexes for faces

    public Mesh(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    // Getters and setters
    public float[] getVertices() { return vertices; }
    public int[] getIndices() { return indices; }

    public void setVertices(float[] vertices) { this.vertices = vertices; }
    public void setIndices(int[] indices) { this.indices = indices; }

    public void deduplicateVertices() {
        Map<String, Integer> uniqueVertexMap = new LinkedHashMap<>(); // Preserve insertion order
        List<Float> newVertices = new ArrayList<>();
        List<Integer> newIndices = new ArrayList<>();

        // Iterate through the current indices to process vertices
        for (int i = 0; i < indices.length; i++) {
            int vertexIndex = indices[i];
            float x = vertices[vertexIndex * 3];
            float y = vertices[vertexIndex * 3 + 1];
            float z = vertices[vertexIndex * 3 + 2];

            // Create a string key for the current vertex to handle floating-point precision
            String vertexKey = String.format("%.6f,%.6f,%.6f", x, y, z);

            if (!uniqueVertexMap.containsKey(vertexKey)) {
                // Add new vertex to the map and list
                uniqueVertexMap.put(vertexKey, newVertices.size() / 3);
                newVertices.add(x);
                newVertices.add(y);
                newVertices.add(z);
            }

            // Update the indices with the new index of the unique vertex
            newIndices.add(uniqueVertexMap.get(vertexKey));
        }

        // Convert new vertices and indices back to arrays
        vertices = new float[newVertices.size()];
        for (int i = 0; i < newVertices.size(); i++) {
            vertices[i] = newVertices.get(i);
        }

        indices = new int[newIndices.size()];
        for (int i = 0; i < newIndices.size(); i++) {
            indices[i] = newIndices.get(i);
        }
    }
}

