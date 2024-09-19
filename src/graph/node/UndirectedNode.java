package graph.node;

import java.util.HashMap;
import java.util.Map;
import utils.Vector3;

public class UndirectedNode extends Node {

    // { Node ID: Relation ID }
    private final Map<Integer, Integer> adjNodes = new HashMap<>();

    public UndirectedNode(int id, Vector3 position) {
        super(id, position);
    }
    
    public Map<Integer, Integer> getAdjNodes() {
        return adjNodes;
    }
}
