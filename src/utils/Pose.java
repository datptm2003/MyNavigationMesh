package utils;

public class Pose {
    private Vector3 position; // 3D position (x, y, z)
    private Quaternion orientation; // Rotation (quaternion)

    public Pose(Vector3 position, Quaternion orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public void translate(Vector3 translation) {
        this.position = this.position.add(translation);
    }

    public void rotate(Quaternion rotation) {
        this.orientation = this.orientation.multiply(rotation).normalize();
    }
    public void rotate(Vector3 axis, float angle) {
        Quaternion rotation = new Quaternion(axis, angle);
        this.orientation = this.orientation.multiply(rotation).normalize();
    }

    public float[][] getRotationMatrix() {
        return orientation.convertToRotationMatrix();
    }

    public Vector3 getPosition() {
        return position;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Quaternion getOrientation() {
        return orientation;
    }
    public void setOrientation(Quaternion orientation) {
        this.orientation = orientation;
    }
}

