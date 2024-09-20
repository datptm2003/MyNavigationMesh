package utils;

public class Polygon {
    private float[] vertices; // Vertex positions (x, y, z)
    private int[] indices;

    public Polygon(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }
    // public Polygon(float[] vertices) {
    //     this.vertices = vertices;
    // }

    // Getters and setters
    public float[] getVertices() { return vertices; }
    public int[] getIndices() { return indices; }

    public void setVertices(float[] vertices) { this.vertices = vertices; }
    public void setIndices(int[] indices) { this.indices = indices; }

    public float[] calculateCenter() {
        // Calculate the center of the polygon (assuming triangles for simplicity)
        float[] center = new float[3];
        for (int i = 0; i < 3; i++) {
            center[0] += vertices[i * 3]; // X
            center[1] += vertices[i * 3 + 1]; // Y
            center[2] += vertices[i * 3 + 2]; // Z
        }
        center[0] /= 3;
        center[1] /= 3;
        center[2] /= 3;

        return center;
    }
}

