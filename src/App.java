import graph.Graph;
import graph.UndirectedGraph;
import graph.edge.Edge;
import graph.node.UndirectedNode;
import graph.search.GraphSearch_AStar;
import graph.search.GraphSearch_Dijkstra;
import utils.Vector3;

public class App {
    public static void main(String[] args) {
        UndirectedGraph graph = new UndirectedGraph();
        setupGraph(graph);

        // Test Dijkstra algorithm
        GraphSearch_Dijkstra dijkstraSearch = new GraphSearch_Dijkstra(graph);
        
        System.out.println("Dijkstra Search: Shortest Path from Node 1 to Node 4");
        System.out.println(dijkstraSearch.search(0, 3));

        // Test A* algorithm
        GraphSearch_AStar aStarSearch = new GraphSearch_AStar(graph);
        System.out.println("A* Search: Shortest Path from Node 1 to Node 4");
        System.out.println(aStarSearch.search(0, 3));
    }

    // Setup a sample graph with nodes and edges
    private static void setupGraph(UndirectedGraph graph) {
        // Adding nodes with positions
        graph.addNode(new UndirectedNode(Graph.getNextNodeId(), new Vector3(0, 0, 0)));
        graph.addNode(new UndirectedNode(Graph.getNextNodeId(), new Vector3(1, 0, 0)));
        graph.addNode(new UndirectedNode(Graph.getNextNodeId(), new Vector3(1, 1, 0)));
        graph.addNode(new UndirectedNode(Graph.getNextNodeId(), new Vector3(2, 1, 0)));

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
    }
}
