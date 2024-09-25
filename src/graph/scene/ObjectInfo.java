package graph.scene;

import utils.Mesh;
import utils.Pose;

public class ObjectInfo {
    private final Mesh mesh;
    private final Pose pose;

    public ObjectInfo(Mesh mesh, Pose pose) {
        this.mesh = mesh;
        this.pose = pose;
    }

    public Mesh getMesh() {
        return mesh;
    }
    public Pose getPose() {
        return pose;
    }
}
