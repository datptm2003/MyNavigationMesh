package utils;

public class Vector3 {
    private final float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Add vector operation
    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Add vector operation
    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Scale vector operation
    public Vector3 scale(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public boolean equals(Vector3 other) {
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    // Function to calculate distance between two vertices
    public float distance(Vector3 vec) {
        return (float) Math.sqrt(Math.pow(this.getX() - vec.getX(), 2) + Math.pow(this.getY() - vec.getY(), 2));
    }

    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
}

