package utils;

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
}

