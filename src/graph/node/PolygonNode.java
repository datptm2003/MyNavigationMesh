package graph.node;

import utils.Polygon;

public class PolygonNode extends UndirectedNode {
    private final Polygon polygon;
    private final float[] center;
    
    public PolygonNode(int id, Polygon polygon) {
        super(id);
        this.polygon = polygon;
        this.center = polygon.calculateCenter();
    }

    public Polygon getPolygon() {
        return polygon;
    }
    
    public float[] getCenter() {
        return center;
    }

    public int[] getIndices() {
        return polygon.getIndices();
    }
}
