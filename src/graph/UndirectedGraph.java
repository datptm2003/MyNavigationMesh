package graph;

import graph.edge.Edge;
import graph.node.UndirectedNode;

public class UndirectedGraph<N extends UndirectedNode> extends Graph<N, Edge> {
    public UndirectedGraph() {
        super();
    }
    
    @Override
    public void bindRelation(int outNodeId, int inNodeId, int relationId) {
        this.getNodeById(inNodeId).getAdjNodes().put(outNodeId, relationId);
        this.getNodeById(outNodeId).getAdjNodes().put(inNodeId, relationId);
    }
    
}
