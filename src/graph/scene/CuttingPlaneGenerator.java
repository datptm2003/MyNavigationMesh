package graph.scene;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.Mesh;
import utils.Vector3;

public class CuttingPlaneGenerator {

    /**
     * Generates a cutting plane mesh at a specified y-axis value that intersects the given list of ObjectInfos.
     * 
     * @param ObjectInfos List of ObjectInfos to intersect with the cutting plane.
     * @param yPlaneValue The y-axis value of the cutting plane.
     * @return A Mesh representing the 2D cutting plane.
     */
    public static Mesh generateCuttingPlane(List<ObjectInfo> ObjectInfos, float yPlaneValue) {
        List<Vector3> intersectionVertices = new ArrayList<>();
        Set<Integer> intersectionIndices = new HashSet<>();
        int indexCounter = 0;

        // Iterate through each ObjectInfo and process their meshes
        for (ObjectInfo ObjectInfo : ObjectInfos) {
            Mesh mesh = ObjectInfo.getMesh();
            float[] vertices = mesh.getVertices();
            int[] indices = mesh.getIndices();

            // Process edges defined by the indices
            for (int i = 0; i < indices.length; i += 3) {
                Vector3 v1 = getVertex(vertices, indices[i]);
                Vector3 v2 = getVertex(vertices, indices[i + 1]);
                Vector3 v3 = getVertex(vertices, indices[i + 2]);

                // Find intersections of edges with the y-plane
                indexCounter = addIntersectionPoint(intersectionVertices, intersectionIndices, v1, v2, yPlaneValue, indexCounter);
                indexCounter = addIntersectionPoint(intersectionVertices, intersectionIndices, v2, v3, yPlaneValue, indexCounter);
                indexCounter = addIntersectionPoint(intersectionVertices, intersectionIndices, v3, v1, yPlaneValue, indexCounter);
            }
        }

        // Convert intersections into a mesh
        return createMesh(intersectionVertices);
    }

    /**
     * Gets a vertex from the vertices array using the index.
     * 
     * @param vertices Array of vertex positions.
     * @param index Index of the vertex.
     * @return Vector3 representing the vertex position.
     */
    private static Vector3 getVertex(float[] vertices, int index) {
        int start = index * 3;
        return new Vector3(vertices[start], vertices[start + 1], vertices[start + 2]);
    }

    /**
     * Adds intersection points between an edge and the y-plane.
     * 
     * @param vertices The list of intersection vertices.
     * @param indices The set of indices to keep track of used indices.
     * @param v1 First vertex of the edge.
     * @param v2 Second vertex of the edge.
     * @param yPlaneValue The y-axis value of the cutting plane.
     * @param indexCounter Current count of vertices added.
     */
    private static int addIntersectionPoint(List<Vector3> vertices, Set<Integer> indices, Vector3 v1, Vector3 v2, float yPlaneValue, int indexCounter) {
        if ((v1.getY() - yPlaneValue) * (v2.getY() - yPlaneValue) < 0) {
            // Calculate intersection point
            float t = (yPlaneValue - v1.getY()) / (v2.getY() - v1.getY());
            Vector3 intersection = v1.add(v2.subtract(v1).scale(t));
            vertices.add(intersection);
            indices.add(indexCounter++);
        }
        return indexCounter;
    }

    /**
     * Creates a Mesh ObjectInfo from a list of vertices representing the cutting plane.
     * 
     * @param vertices List of vertices on the cutting plane.
     * @return A Mesh representing the cutting plane.
     */
    private static Mesh createMesh(List<Vector3> vertices) {
        float[] vertexArray = new float[vertices.size() * 3];
        int[] indexArray = new int[vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            Vector3 vertex = vertices.get(i);
            vertexArray[i * 3] = vertex.getX();
            vertexArray[i * 3 + 1] = vertex.getY();
            vertexArray[i * 3 + 2] = vertex.getZ();
            indexArray[i] = i;
        }

        return new Mesh(vertexArray, indexArray);
    }
}
