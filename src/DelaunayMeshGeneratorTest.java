import graph.scene.DelaunayMeshGenerator;
import java.util.*;
import utils.EnhancedMesh;
import utils.Vector3;

public class DelaunayMeshGeneratorTest {

    // Main method for testing
    public static void main(String[] args) {
        // Example usage
        List<Vector3> points = Arrays.asList(
            new Vector3(1, 1, 0), new Vector3(2, 2, 0), new Vector3(3, 1, 0),
            new Vector3(4, 4, 0), new Vector3(5, 2, 0), new Vector3(3, 2, 0)
        );
        // System.out.println(points.getClass().getSimpleName());
        List<Integer> objectIndices = Arrays.asList(
            0, 0, 0, 0, 0, 0
        );

        // float[] vertices = new float[] {1f,1f,2f,2f,3f,1f,4f,4f,5f,2f};

        // Boundary (minX, minY, maxX, maxY)
        float[] boundary = { 0, 0, 6, 6 };

        EnhancedMesh resultMesh = DelaunayMeshGenerator.generateDelaunay(points, objectIndices, boundary);
        resultMesh.removeInternalEdges();

        // Print result
        System.out.println("Vertices: " + Arrays.toString(resultMesh.getVertices()));
        System.out.println("Indices: " + Arrays.toString(resultMesh.getIndices()));
        System.out.println("Object Indices: " + Arrays.toString(resultMesh.getObjectIndices()));
    }
}
