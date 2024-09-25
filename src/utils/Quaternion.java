package utils;

public class Quaternion {
    private float rPart = 0;
    private float[] iPart = new float[]{0.0f, 0.0f, 0.0f};

    public Quaternion(float rPart, float[] iPart) {
        this.rPart = rPart;
        this.iPart = iPart;
    }
    public Quaternion(Vector3 axis, float angle) {
        float halfAngle = angle / 4.0f;
        float sinHalfAngle = (float) Math.sin(halfAngle);

        this.rPart = (float) Math.cos(halfAngle);
        this.iPart = new float[] {
            axis.getX() * sinHalfAngle,
            axis.getY() * sinHalfAngle,
            axis.getZ() * sinHalfAngle
        };
    }
    public Quaternion() {}

    public Quaternion add(Quaternion other) {
        float newR = this.rPart + other.rPart;
        float[] newI = new float[]{this.iPart[0] + other.iPart[0], this.iPart[1] + other.iPart[1], this.iPart[2] + other.iPart[2]};

        return new Quaternion(newR, newI);
    }

    public Quaternion subtract(Quaternion other) {
        float newR = this.rPart - other.rPart;
        float[] newI = new float[]{this.iPart[0] - other.iPart[0], this.iPart[1] - other.iPart[1], this.iPart[2] - other.iPart[2]};

        return new Quaternion(newR, newI);
    }

    public Quaternion scale(float scalar) {
        float newR = this.rPart * scalar;
        float[] newI = new float[]{this.iPart[0] * scalar, this.iPart[1] * scalar, this.iPart[2] * scalar};

        return new Quaternion(newR, newI);
    }

    public float dot(Quaternion other) {
        return this.rPart * other.rPart + this.iPart[0] * other.iPart[0] + this.iPart[1] * other.iPart[1] + this.iPart[2] * other.iPart[2];
    }

    public Quaternion multiply(Quaternion other) {
        float a = this.rPart;
        float b = this.iPart[0];
        float c = this.iPart[1];
        float d = this.iPart[2];

        float x = other.rPart;
        float y = other.iPart[0];
        float z = other.iPart[1];
        float t = other.iPart[2];

        float newR = a * x - b * y - c * z - d * t;
        float[] newI = new float[3];
        newI[0] = a * y + b * x + c * t - d * z;
        newI[1] = a * z - b * t + c * x + d * y;
        newI[2] = a * t + b * z - c * y + d * x;

        return new Quaternion(newR, newI);
    }

    public Quaternion conjugate() {
        float[] newI = new float[]{-this.iPart[0], -this.iPart[1], -this.iPart[2]};
        return new Quaternion(this.rPart, newI);
    }

    public float length() {
        float norm = (float) Math.sqrt(this.rPart * this.rPart + this.iPart[0] * this.iPart[0] + this.iPart[1] * this.iPart[1] + this.iPart[2] * this.iPart[2]);

        return norm;
    }

    public Quaternion inverse() {
        float normSquared = this.length();
        if (normSquared == 0) {
            throw new ArithmeticException("Cannot invert a quaternion with zero norm.");
        }

        Quaternion conjugate = this.conjugate();
        float newR = conjugate.rPart / normSquared;
        float[] newI = new float[]{conjugate.iPart[0] / normSquared, conjugate.iPart[1] / normSquared, conjugate.iPart[2] / normSquared};

        return new Quaternion(newR, newI);
    }

    public Quaternion normalize() {
        float norm = this.length();

        float newR = this.rPart / norm;
        float[] newI = new float[]{this.iPart[0] / norm, this.iPart[1] / norm, this.iPart[2] / norm};

        return new Quaternion(newR, newI);
    }

    public float[][] convertToRotationMatrix() {
        float w = this.rPart;
        float x = this.iPart[0];
        float y = this.iPart[1];
        float z = this.iPart[2];

        float[][] rotationMatrix = new float[4][4];

        rotationMatrix[0][0] = 1 - 2 * (y * y + z * z);
        rotationMatrix[0][1] = 2 * (x * y - z * w);
        rotationMatrix[0][2] = 2 * (x * z + y * w);

        rotationMatrix[1][0] = 2 * (x * y + z * w);
        rotationMatrix[1][1] = 1 - 2 * (x * x + z * z);
        rotationMatrix[1][2] = 2 * (y * z - x * w);

        rotationMatrix[2][0] = 2 * (x * z - y * w);
        rotationMatrix[2][1] = 2 * (y * z + x * w);
        rotationMatrix[2][2] = 1 - 2 * (x * x + y * y);

        rotationMatrix[3][3] = 1;

        return rotationMatrix;
    }
    
    public Quaternion interpolate(Quaternion other, float t) {
        float dotProduct = this.dot(other);
        float theta = (float) Math.acos(dotProduct);
        float sinTheta = (float) Math.sin(theta);

        if (sinTheta == 0) {
            return this;

        } else {
            float scaleFactor = (float) Math.sin((1 - t) * theta) / sinTheta;
            float otherScaleFactor = (float) Math.sin(t * theta) / sinTheta;
            return this.scale(scaleFactor).add(other.scale(otherScaleFactor));

        }
    }

    public static Quaternion identity() {
        return new Quaternion(1, new float[]{0,0,0});
    }
}
