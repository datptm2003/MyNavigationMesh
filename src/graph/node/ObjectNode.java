package graph.node;

import java.util.HashMap;
import java.util.Map;
import utils.Mesh;
import utils.OrientedBoundingBox;

public class ObjectNode {
    private final String name;

    private OrientedBoundingBox boundingBox;
    private Mesh mesh;

    private final Map<String, ObjectNode> childNodes = new HashMap<>();

    public ObjectNode(String name, OrientedBoundingBox boundingBox, Mesh mesh) {
        this.name = name;
        this.boundingBox = boundingBox;
        this.mesh = mesh;
    }
    public ObjectNode(String name) {
        this.name = name;
        this.boundingBox = null;
        this.mesh = null;
    }

    public String getName() {
        return name;
    }
    
    public OrientedBoundingBox getBoundingBox() {
        return boundingBox;
    }
    public void setBoundingBox(OrientedBoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Mesh getMesh() {
        return mesh;
    }
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public ObjectNode getChildByName(String name) {
        return childNodes.get(name);
    }
    public void addChild(ObjectNode object) {
        this.childNodes.put(object.getName(), object);
    }
}
