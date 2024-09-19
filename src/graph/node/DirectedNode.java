package graph.node;

import java.util.HashMap;
import java.util.Map;

public class DirectedNode extends Node {

    // { Node ID: Relation ID }
    private final Map<Integer, Integer> inNodes = new HashMap<>();
    private final Map<Integer, Integer> outNodes = new HashMap<>();

    public DirectedNode(int id) {
        super(id);
    }
    
    public Map<Integer, Integer> getInNodes() {
        return inNodes;
    }
    public Map<Integer, Integer> getOutNodes() {
        return outNodes;
    }
}
