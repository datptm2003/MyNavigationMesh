package graph.node;

import java.util.HashMap;
import java.util.Map;

public class UndirectedNode extends Node {

    // { Node ID: Relation ID }
    private final Map<Integer, Integer> adjNodes = new HashMap<>();

    public UndirectedNode(int id) {
        super(id);
    }
    
    public Map<Integer, Integer> getAdjNodes() {
        return adjNodes;
    }
}
