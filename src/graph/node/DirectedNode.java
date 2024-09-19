package graph.node;

import java.util.HashMap;
import java.util.Map;
import utils.Vector3;

public class DirectedNode extends Node {

    // { Node ID: Relation ID }
    private final Map<Integer, Integer> inNodes = new HashMap<>();
    private final Map<Integer, Integer> outNodes = new HashMap<>();

    public DirectedNode(int id, Vector3 position) {
        super(id, position);
    }
    
    public Map<Integer, Integer> getInNodes() {
        return inNodes;
    }
    public Map<Integer, Integer> getOutNodes() {
        return outNodes;
    }
}
