package graph;

import graph.edge.Edge;
import graph.node.DirectedNode;

public class DirectedGraph extends Graph<DirectedNode, Edge> {
    public DirectedGraph() {
        super();
    }

    @Override
    public void bindRelation(int outNodeId, int inNodeId, int relationId) {
        this.getNodeById(inNodeId).getInNodes().put(outNodeId, relationId);
        this.getNodeById(outNodeId).getOutNodes().put(inNodeId, relationId);
    }
    
}
