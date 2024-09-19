package graph.edge;

public class Edge {
    private final int id;
    private final int cost;

    public Edge(int id, int cost) {
        this.id = id;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }
}
