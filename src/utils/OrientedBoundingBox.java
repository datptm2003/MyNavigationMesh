package utils;

public class OrientedBoundingBox {
    private Pose pose;
    private Vector3 extents;

    public OrientedBoundingBox(Pose pose, Vector3 extents) {
        this.pose = pose;
        this.extents = extents;
    }

    public Pose getPose() {
        return pose;
    }
    public void setPose(Pose pose) {
        this.pose = pose;
    }

    public Vector3 getExtents() {
        return extents;
    }
    public void setExtents(Vector3 extents) {
        this.extents = extents;
    }

    
}
