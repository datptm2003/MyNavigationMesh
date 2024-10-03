package graph.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.EnhancedMesh;
import utils.Vector3;

public class DelaunayMeshGenerator {

    private static class Triangle {
        Vector3 p1, p2, p3;

        Triangle(Vector3 p1, Vector3 p2, Vector3 p3) {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }

        boolean isPointInCircumcircle(Vector3 p) {
            float ax = p1.getX() - p.getX();
            float ay = p1.getY() - p.getY();
            float bx = p2.getX() - p.getX();
            float by = p2.getY() - p.getY();
            float cx = p3.getX() - p.getX();
            float cy = p3.getY() - p.getY();

            float det = (ax * ax + ay * ay) * (bx * cy - cx * by)
                    - (bx * bx + by * by) * (ax * cy - cx * ay)
                    + (cx * cx + cy * cy) * (ax * by - bx * ay);

            return det > 0;
        }

        boolean containsEdge(Vector3 p1, Vector3 p2) {
            return (this.p1.equals(p1) && this.p2.equals(p2)) ||
                    (this.p2.equals(p1) && this.p3.equals(p2)) ||
                    (this.p3.equals(p1) && this.p1.equals(p2)) ||
                    (this.p1.equals(p2) && this.p2.equals(p1)) ||
                    (this.p2.equals(p2) && this.p3.equals(p1)) ||
                    (this.p3.equals(p2) && this.p1.equals(p1));
        }
    }

    public static List<Vector3> generateRandomVertices(List<Vector3> originalVertices, float[] boundary, int numberOfNewVertices) {
        float minX = boundary[0];
        float minY = boundary[1];
        float maxX = boundary[2];
        float maxY = boundary[3];
    
        List<Vector3> newVertices = new ArrayList<>(originalVertices);
        
        // Loop to generate the required number of new vertices
        for (int i = 0; i < numberOfNewVertices; i++) {
            Vector3 randomVertex = null;
            float largestMinDistance = -1;
    
            // Generate random vertices and find the one furthest from any existing vertex
            for (int attempt = 0; attempt < 100; attempt++) {
                float randomX = minX + (float) Math.random() * (maxX - minX);
                float randomY = minY + (float) Math.random() * (maxY - minY);
                Vector3 candidateVertex = new Vector3(randomX, randomY, 0);
    
                // Calculate the minimum distance from this vertex to any existing vertex
                float minDistance = Float.MAX_VALUE;
                for (Vector3 vertex : newVertices) {
                    float dist = vertex.distance(candidateVertex);
                    if (dist < minDistance) {
                        minDistance = dist;
                    }
                }
    
                // Keep the vertex that maximizes the minimum distance to others
                if (minDistance > largestMinDistance) {
                    largestMinDistance = minDistance;
                    randomVertex = candidateVertex;
                }
            }
    
            // Add the selected random vertex to the list
            if (randomVertex != null) {
                newVertices.add(randomVertex);
            }
        }
    
        return newVertices;
    }
    

    public static EnhancedMesh generateDelaunay(List<Vector3> vertices, List<Integer> objectIndices, float[] boundary) {
        float minX = boundary[0];
        float minY = boundary[1];
        float maxX = boundary[2];
        float maxY = boundary[3];

        List<Vector3> newVertices = generateRandomVertices(vertices, boundary, 1);

        // Super-triangle encompassing all points
        Vector3[] boundaryVertices = new Vector3[] {
            new Vector3(minX, minY, 0),
            new Vector3(maxX, minY, 0),
            new Vector3(maxX, maxY, 0),
            new Vector3(minX, maxY, 0)
        };
        List<Triangle> triangulation = new ArrayList<>();
        triangulation.add(new Triangle(boundaryVertices[0], boundaryVertices[1], boundaryVertices[2]));
        triangulation.add(new Triangle(boundaryVertices[0], boundaryVertices[2], boundaryVertices[3]));

        // for (int i = 0; i < boundaryVertices.length; ++i) {
        //     vertices.add(boundaryVertices[i]);
        //     objectIndices.add(-1);
        // }

        // Create data structures to track vertices, indices, and objectIndices
        List<Float> verticesList = new ArrayList<>();
        List<Integer> indicesList = new ArrayList<>();
        List<Integer> objectIndicesList = new ArrayList<>();
        Map<Vector3, Integer> vertexMap = new HashMap<>();

        // Insert each point into the triangulation and keep track of object indices
        for (int i = 0; i < newVertices.size(); i++) {
            Vector3 vertex = newVertices.get(i);
            // int objectIndex = objectIndices.get(i);

            List<Triangle> badTriangles = new ArrayList<>();
            for (Triangle triangle : triangulation) {
                if (triangle.isPointInCircumcircle(vertex)) {
                    badTriangles.add(triangle);
                }
            }

            List<Vector3[]> polygon = new ArrayList<>();
            for (Triangle badTriangle : badTriangles) {
                Vector3[] edges = { badTriangle.p1, badTriangle.p2, badTriangle.p3 };
                for (int j = 0; j < 3; j++) {
                    Vector3 a = edges[j];
                    Vector3 b = edges[(j + 1) % 3];
                    boolean isShared = false;
                    for (Triangle other : badTriangles) {
                        if (other != badTriangle && other.containsEdge(a, b)) {
                            isShared = true;
                            break;
                        }
                    }
                    if (!isShared) {
                        polygon.add(new Vector3[]{ a, b });
                    }
                }
            }

            triangulation.removeAll(badTriangles);

            for (Vector3[] edge : polygon) {
                triangulation.add(new Triangle(edge[0], edge[1], vertex));
            }
        }

        for (Triangle triangle : triangulation) {
            Vector3[] pointsInTriangle = { triangle.p1, triangle.p2, triangle.p3 };
            for (Vector3 pt : pointsInTriangle) {
                if (!vertexMap.containsKey(pt)) {
                    vertexMap.put(pt, verticesList.size() / 3);
                    verticesList.add(pt.getX());
                    verticesList.add(pt.getY());
                    verticesList.add(pt.getZ());
                    // TODO
                    int objectIndex = vertices.indexOf(pt);
                    if (objectIndex == -1) {
                        objectIndicesList.add(-1);
                    } else {
                        objectIndicesList.add(objectIndices.get(objectIndex));  // Match object index
                    }
                }
                indicesList.add(vertexMap.get(pt));
            }
        }

        float[] verticesArr = new float[verticesList.size()];
        for (int i = 0; i < verticesList.size(); i++) {
            verticesArr[i] = verticesList.get(i);
        }

        int[] indicesArr = new int[indicesList.size()];
        for (int i = 0; i < indicesList.size(); i++) {
            indicesArr[i] = indicesList.get(i);
        }

        int[] objectIndicesArr = new int[objectIndicesList.size()];
        for (int i = 0; i < objectIndicesList.size(); i++) {
            objectIndicesArr[i] = objectIndicesList.get(i);
        }

        return new EnhancedMesh(verticesArr, indicesArr, objectIndicesArr);
    }
}
