package problem.shared;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Graph {

    private final int vertexCount;
    private int[][] graphAdjacencyMatrix;
    GraphDirectedProperty directedProperty;

    public Graph(String filepath, GraphSourceType type) throws IOException {
        vertexCount = readGraph(filepath, type);
        directedProperty = new GraphDirectedProperty();
    }

    private int readGraph(String filepath, GraphSourceType type) throws IOException {
        throw new UnsupportedOperationException("needs implementation");
    }


    public int getVertexCount() {
        return vertexCount;
    }

    public int weight(int vi, int vj) {
        throw new UnsupportedOperationException("needs implementation");
    }

    public boolean isEdge(int vi, int vj) {
        throw new UnsupportedOperationException("needs implementation");
    }

    public int[][] getGraphAdjacencyMatrix() {
        return graphAdjacencyMatrix;
    }

    public List<Integer> getVertexAdjacencyList(int v) {
        throw new UnsupportedOperationException("needs implementation");
    }

    public List<Integer> getGraphEdgesList() {
        throw new UnsupportedOperationException("needs implementation");
    }

    public List<Integer> getVertexEdgesList(int v) {
        throw new UnsupportedOperationException("needs implementation");
    }

    public boolean isADirectedGraph() {
        return directedProperty.isDirected(graphAdjacencyMatrix);
    }
}

class GraphDirectedProperty {
    private boolean isDirected;
    private int prevArrayHash;
    private boolean initialised;

    GraphDirectedProperty() {
        prevArrayHash = 0;
        initialised = false;
        isDirected = false;
    }

    boolean isDirected(int[][] adjacencyMatrix) {
        int newHashCode = Arrays.deepHashCode(adjacencyMatrix);
        if (!initialised || prevArrayHash != newHashCode) {
            isDirected = calculateIsADirectedGraph(adjacencyMatrix);
            initialised = true;
            prevArrayHash = newHashCode;
        }
        return isDirected;
    }

    static boolean calculateIsADirectedGraph(int[][] adjacencyMatrix) {
        boolean isDirected = false;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i+1; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] != adjacencyMatrix[j][i]) {
                    isDirected = true;
                    return isDirected;
                }
            }
        }
        return isDirected;
    }

}
