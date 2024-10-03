package utils;

import java.util.ArrayList;
import java.util.List;

public class EnhancedMesh extends Mesh {
    private int[] objectIndices;
    
    public EnhancedMesh(float[] vertices, int[] indices, int[] objectIndices) {
        super(vertices, indices);
        this.objectIndices = objectIndices;
    }

    public int[] getObjectIndices() {
        return objectIndices;
    }
    public void setObjectIndices(int[] objectIndices) {
        this.objectIndices = objectIndices;
    }

    public void removeInternalEdges() {
        List<Integer> newIndices = new ArrayList<>();

        // Iterate through each face (3 indices per face)
        for (int i = 0; i < getIndices().length; i += 3) {
            int i1 = getIndices()[i];
            int i2 = getIndices()[i + 1];
            int i3 = getIndices()[i + 2];

            // Get object index for each vertex
            int obj1 = objectIndices[i1];
            int obj2 = objectIndices[i2];
            int obj3 = objectIndices[i3];

            // If all vertices belong to the same object (and it's not -1), skip the face (internal)
            if (obj1 == obj2 && obj2 == obj3 && obj1 != -1) {
                continue;
            }

            // Otherwise, keep the face (external edge)
            newIndices.add(i1);
            newIndices.add(i2);
            newIndices.add(i3);
        }

        // Convert new indices back to array and set it
        int[] updatedIndices = newIndices.stream().mapToInt(Integer::intValue).toArray();
        setIndices(updatedIndices);
    }
}
