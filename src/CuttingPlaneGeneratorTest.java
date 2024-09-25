import graph.scene.CuttingPlaneGenerator;
import graph.scene.ObjectInfo;
import java.util.ArrayList;
import java.util.List;
import utils.Mesh;
import utils.Pose;
import utils.Quaternion;
import utils.Vector3;

public class CuttingPlaneGeneratorTest {

    public static void main(String[] args) {
        // Step 1: Create sample objects with complex meshes
        List<ObjectInfo> objects = createSampleObjectInfos();

        // Step 2: Define a y-plane value for cutting
        float yPlaneValue = 0.5f;

        // Step 3: Generate the cutting plane mesh
        Mesh cuttingPlaneMesh = CuttingPlaneGenerator.generateCuttingPlane(objects, yPlaneValue);
        cuttingPlaneMesh.deduplicateVertices();

        // Step 4: Output the result
        printMesh(cuttingPlaneMesh);
    }

    /**
     * Creates a list of sample objects with complex meshes for testing.
     * 
     * @return List of objects.
     */
    private static List<ObjectInfo> createSampleObjectInfos() {
        List<ObjectInfo> objects = new ArrayList<>();

        // ObjectInfo 1: A tilted rectangular prism
        float[] vertices1 = {
            -0.5f, 0.2f, -0.5f,  // Vertex 0
             0.5f, 0.2f, -0.5f,  // Vertex 1
             0.5f, 0.8f, -0.5f,  // Vertex 2
            -0.5f, 0.8f, -0.5f,  // Vertex 3
            -0.5f, 0.2f,  0.5f,  // Vertex 4
             0.5f, 0.2f,  0.5f,  // Vertex 5
             0.5f, 0.8f,  0.5f,  // Vertex 6
            -0.5f, 0.8f,  0.5f   // Vertex 7
        };
        int[] indices1 = {
            0, 1, 2,  0, 2, 3,  // Bottom
            4, 5, 6,  4, 6, 7,  // Top
            0, 1, 5,  0, 5, 4,  // Front
            1, 2, 6,  1, 6, 5,  // Right
            2, 3, 7,  2, 7, 6,  // Back
            3, 0, 4,  3, 4, 7   // Left
        };
        Mesh mesh1 = new Mesh(vertices1, indices1);
        Pose pose1 = new Pose(new Vector3(0.0f, 0.0f, 0.0f), Quaternion.identity());
        objects.add(new ObjectInfo(mesh1, pose1));

        // ObjectInfo 2: A rotated pyramid
        float[] vertices2 = {
            -0.3f, 0.3f, -0.3f,  // Base Vertex 0
             0.3f, 0.3f, -0.3f,  // Base Vertex 1
             0.3f, 0.3f,  0.3f,  // Base Vertex 2
            -0.3f, 0.3f,  0.3f,  // Base Vertex 3
             0.0f, 0.9f,  0.0f   // Apex Vertex 4
        };
        int[] indices2 = {
            0, 1, 4,  // Side 1
            1, 2, 4,  // Side 2
            2, 3, 4,  // Side 3
            3, 0, 4,  // Side 4
            0, 1, 2,  0, 2, 3   // Base
        };
        Mesh mesh2 = new Mesh(vertices2, indices2);
        Pose pose2 = new Pose(new Vector3(1.0f, 0.0f, 0.0f), new Quaternion(new Vector3(0, 1, 0), (float) Math.toRadians(30)));
        objects.add(new ObjectInfo(mesh2, pose2));

        // ObjectInfo 3: A slanted cube intersecting the plane
        float[] vertices3 = {
            -0.4f, 0.0f, -0.4f,  // Vertex 0
             0.4f, 0.0f, -0.4f,  // Vertex 1
             0.4f, 1.0f, -0.4f,  // Vertex 2
            -0.4f, 1.0f, -0.4f,  // Vertex 3
            -0.4f, 0.0f,  0.4f,  // Vertex 4
             0.4f, 0.0f,  0.4f,  // Vertex 5
             0.4f, 1.0f,  0.4f,  // Vertex 6
            -0.4f, 1.0f,  0.4f   // Vertex 7
        };
        int[] indices3 = {
            0, 1, 2,  0, 2, 3,  // Bottom
            4, 5, 6,  4, 6, 7,  // Top
            0, 1, 5,  0, 5, 4,  // Front
            1, 2, 6,  1, 6, 5,  // Right
            2, 3, 7,  2, 7, 6,  // Back
            3, 0, 4,  3, 4, 7   // Left
        };
        Mesh mesh3 = new Mesh(vertices3, indices3);
        Pose pose3 = new Pose(new Vector3(-1.0f, 0.2f, -0.5f), new Quaternion(new Vector3(1, 0, 0), (float) Math.toRadians(15)));
        objects.add(new ObjectInfo(mesh3, pose3));

        return objects;
    }

    /**
     * Prints the vertices and indices of a Mesh to verify the result.
     * 
     * @param mesh The Mesh object to print.
     */
    private static void printMesh(Mesh mesh) {
        System.out.println("Cutting Plane Mesh Vertices:");
        float[] vertices = mesh.getVertices();
        for (int i = 0; i < vertices.length; i += 3) {
            System.out.printf("Vertex %d: (%.3f, %.3f, %.3f)%n", i / 3, vertices[i], vertices[i + 1], vertices[i + 2]);
        }

        System.out.println("\nCutting Plane Mesh Indices:");
        int[] indices = mesh.getIndices();
        for (int i = 0; i < indices.length; i += 3) {
            System.out.printf("Triangle %d: (%d, %d, %d)%n", i / 3, indices[i], indices[i + 1], indices[i + 2]);
        }
    }
}
