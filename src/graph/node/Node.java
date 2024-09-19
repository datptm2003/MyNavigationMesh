package graph.node;

import utils.Vector3;

public class Node {
    private final int id;
    private final Vector3 position;

    public Node(int id, Vector3 position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Vector3 getPosition() {
        return position;
    }

}
