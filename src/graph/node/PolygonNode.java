package graph.node;

import utils.Polygon;

public class PolygonNode extends UndirectedNode {
    private final Polygon polygon;
    
    public PolygonNode(int id, Polygon polygon) {
        super(id);
        this.polygon = polygon;
    }

    public Polygon getMesh() {
        return polygon;
    }
    
}
