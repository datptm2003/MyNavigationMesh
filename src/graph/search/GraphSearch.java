package graph.search;

import graph.Graph;
import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class GraphSearch<G extends Graph> {
    protected final G graph;

    public GraphSearch(G graph) {
        this.graph = graph;
    }

    public G getGraph() {
        return graph;
    }

    public abstract List<Integer> search(int source, int destination);
}
