import graph.Graph;
import graph.UndirectedGraph;
import graph.edge.Edge;
import graph.node.UndirectedNode;
import graph.search.AStarGraphSearch;
import graph.search.DijkstraGraphSearch;
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
        UndirectedGraph<PointNode> graph = new UndirectedGraph<>();
        setupPointGraph(graph);

        // Test Dijkstra algorithm
        DijkstraGraphSearch<PointNode> dijkstraSearch = new DijkstraGraphSearch<>(graph);
        
        System.out.println("Dijkstra Search: Shortest Path from Node 1 to Node 4");
        System.out.println(dijkstraSearch.search(0, 3));

        // Test A* algorithm
        AStarGraphSearch<PointNode> aStarSearch = new AStarGraphSearch<>(graph);
        System.out.println("A* Search: Shortest Path from Node 1 to Node 4");
        System.out.println(aStarSearch.search(0, 3));
    }

    // Setup a sample graph with nodes and edges
    private static void setupPointGraph(UndirectedGraph<PointNode> graph) {
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
    }
}
