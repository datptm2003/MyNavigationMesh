package graph.edge;

public class Edge {
    private final int id;
    private final double cost;

    public Edge(int id, double cost) {
        this.id = id;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }
}
