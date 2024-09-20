import graph.Graph;
import graph.MeshGraph;
import graph.UndirectedGraph;
import graph.edge.Edge;
import graph.node.PolygonNode;
import graph.node.UndirectedNode;
import graph.search.AStarGraphSearch;
import graph.search.DijkstraGraphSearch;
import java.util.Map.Entry;
import utils.Mesh;
import utils.Vector3;

public class App {
    public static class PointNode extends UndirectedNode {
        private final Vector3 position;
        
        public PointNode(int id, Vector3 position) {
            super(id);
            this.position = position;
        }

        public Vector3 getPosition() {
            return position;
        }   
    }

    public static void main(String[] args) {
        testMeshGraph();
        // testPointGraph();
    }

    private static void testMeshGraph() {
        MeshGraph graph = setupMeshGraph(); 
        DijkstraGraphSearch<PolygonNode> dijkstraSearch = new DijkstraGraphSearch<>(graph);
        
        System.out.println("Dijkstra Search: Shortest Path from Node 1 to Node 8");
        System.out.println(dijkstraSearch.search(0, 7));

    }

    private static void testPointGraph() {
        UndirectedGraph<PointNode> graph = setupPointGraph();

        // Test Dijkstra algorithm
        DijkstraGraphSearch<PointNode> dijkstraSearch = new DijkstraGraphSearch<>(graph);
        
        System.out.println("Dijkstra Search: Shortest Path from Node 1 to Node 4");
        System.out.println(dijkstraSearch.search(0, 3));

        // Test A* algorithm
        AStarGraphSearch<PointNode> aStarSearch = new AStarGraphSearch<>(graph);
        System.out.println("A* Search: Shortest Path from Node 1 to Node 4");
        System.out.println(aStarSearch.search(0, 3));
    }

    private static MeshGraph setupMeshGraph() {
        // Define vertices (x, y, z) of the mesh.
        // For simplicity, creating a grid-like pattern of vertices in the XY plane.
        float[] vertices = {
            0.0f, 0.0f, 0.0f,   // Vertex 0
            1.0f, 0.0f, 0.0f,   // Vertex 1
            2.0f, 0.0f, 0.0f,   // Vertex 2
            0.5f, 1.0f, 0.0f,   // Vertex 3
            1.5f, 1.0f, 0.0f,   // Vertex 4
            2.5f, 1.0f, 0.0f,   // Vertex 5
            0.0f, 2.0f, 0.0f,   // Vertex 6
            1.0f, 2.0f, 0.0f,   // Vertex 7
            2.0f, 2.0f, 0.0f    // Vertex 8
        };

        // Define indices to form triangles using the vertices.
        // Creating triangles that connect in a grid pattern.
        int[] indices = {
            0, 1, 3,   // Triangle 1
            1, 4, 3,   // Triangle 2
            1, 2, 4,   // Triangle 3
            2, 5, 4,   // Triangle 4
            3, 4, 6,   // Triangle 5
            4, 7, 6,   // Triangle 6
            4, 5, 7,   // Triangle 7
            5, 8, 7    // Triangle 8
        };

        // Create a Mesh object with the vertices and indices.
        Mesh mesh = new Mesh(vertices, indices);

        // Create the MeshGraph from the sample mesh.
        MeshGraph meshGraph = new MeshGraph(mesh);

        // Print out the nodes and edges in the graph to verify correctness.
        System.out.println("-- Nodes in the MeshGraph --");
        for (Entry<Integer, PolygonNode> node : meshGraph.getNodes().entrySet()) {
            System.out.print("Node ID: " + node.getKey() + ", Adjacent: ");
            for (int adj : node.getValue().getAdjNodes().keySet()) {
                double cost = meshGraph.getEdgeById(node.getValue().getAdjNodes().get(adj)).getCost();
                System.out.print(adj + " (" + cost + ")" + ", ");
            }
            System.out.println("");
        }

        return meshGraph;
    }

    // Setup a sample graph with nodes and edges
    private static UndirectedGraph<PointNode> setupPointGraph() {
        UndirectedGraph<PointNode> graph = new UndirectedGraph<>();

        // Adding nodes with positions
        graph.addNode(new PointNode(Graph.getNextNodeId(), new Vector3(0, 0, 0)));
        graph.addNode(new PointNode(Graph.getNextNodeId(), new Vector3(1, 0, 0)));
        graph.addNode(new PointNode(Graph.getNextNodeId(), new Vector3(1, 1, 0)));
        graph.addNode(new PointNode(Graph.getNextNodeId(), new Vector3(2, 1, 0)));

        // Adding edges with weights
        graph.addEdge(new Edge(Graph.getNextEdgeId(), 1)); 
        graph.addEdge(new Edge(Graph.getNextEdgeId(), 2));
        graph.addEdge(new Edge(Graph.getNextEdgeId(), 1));
        graph.addEdge(new Edge(Graph.getNextEdgeId(), 4));

        // Binding relations (edges) to nodes
        graph.bindRelation(0, 1, 0); 
        graph.bindRelation(1, 2, 1); 
        graph.bindRelation(2, 3, 2);
        graph.bindRelation(0, 3, 3);

        return graph;
    }
}
