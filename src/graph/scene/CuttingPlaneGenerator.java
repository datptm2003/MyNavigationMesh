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
        for (int i = 0; i < vertices.size(); i++) {
            Vector3 vertex = vertices.get(i);
            vertexArray[i * 3] = vertex.getX();
            vertexArray[i * 3 + 1] = vertex.getY();
            vertexArray[i * 3 + 2] = vertex.getZ();
        }

        Set<int[]> edgeList = new HashSet<>();
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < vertices.size() - 2; i++) {
            Vector3 v1 = vertices.get(i);
            
            for (int j = i + 1; j < vertices.size() - 1; j++) {
                Vector3 v2 = vertices.get(j);
                
                Vector3[] e1 = new Vector3[2];
                e1[0] = v1;
                e1[1] = v2;
                
                boolean flag = false;
                for (int[] edgeIndex : edgeList) {
                    Vector3[] edge = new Vector3[2];
                    edge[0] = vertices.get(edgeIndex[0]);
                    edge[1] = vertices.get(edgeIndex[1]);
                    if (checkIntersect2DSkipParallelism(e1, edge)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) break;
                
                for (int k = j + 1; k < vertices.size(); k++) {
                    Vector3 v3 = vertices.get(k);
                    
                    Vector3[] e2 = new Vector3[2];
                    e2[0] = v2;
                    e2[1] = v3;
                    for (int[] edgeIndex : edgeList) {
                        Vector3[] edge = new Vector3[2];
                        edge[0] = vertices.get(edgeIndex[0]);
                        edge[1] = vertices.get(edgeIndex[1]);
                        if (checkIntersect2DSkipParallelism(e2, edge)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) break;

                    Vector3[] e3 = new Vector3[2];
                    e3[0] = v3;
                    e3[1] = v1;
                    for (int[] edgeIndex : edgeList) {
                        Vector3[] edge = new Vector3[2];
                        edge[0] = vertices.get(edgeIndex[0]);
                        edge[1] = vertices.get(edgeIndex[1]);
                        if (checkIntersect2DSkipParallelism(e3, edge)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) break;

                    indexList.add(i);
                    indexList.add(j);
                    indexList.add(k);

                    edgeList.add(new int[]{i, j});
                    edgeList.add(new int[]{j, k});
                    edgeList.add(new int[]{k, i});

                }
            }
        }

        int[] indexArray = new int[indexList.size()];
        for (int i = 0; i < indexList.size(); i++) {
            indexArray[i] = indexList.get(i);
        }

        return new Mesh(vertexArray, indexArray);
    }

    private static boolean checkIntersect2DSkipParallelism(Vector3[] edge1, Vector3[] edge2) {
        Vector3 e1v1 = edge1[0];
        Vector3 e1v2 = edge1[1];
        Vector3 e2v1 = edge2[0];
        Vector3 e2v2 = edge2[1];

        Vector3 dir1 = e1v1.subtract(e1v2);
        Vector3 dir2 = e2v1.subtract(e2v2);

        if (dir1.getX() / dir2.getX() == dir1.getZ() / dir2.getZ()) {
            return false;
        }
        else {
            float denominator = (dir1.getX() * dir2.getZ()) - (dir1.getZ() * dir2.getX());
            if (denominator == 0) {
                return false;  // The lines are parallel (this case is unlikely due to the above check, but it's a safeguard)
            }

            float t = ((e2v1.getX() - e1v1.getX()) * dir2.getZ() - (e2v1.getZ() - e1v1.getZ()) * dir2.getX()) / denominator;
            float u = ((e2v1.getX() - e1v1.getX()) * dir1.getZ() - (e2v1.getZ() - e1v1.getZ()) * dir1.getX()) / denominator;

            // Check if t and u are within the segment range (0 to 1)
            if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                return true;  // The segments intersect
            }
        }

        return false;
    }
}
