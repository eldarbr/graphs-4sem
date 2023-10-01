package problem.shared;

import java.io.IOException;
import java.util.List;

public class Graph {

    private int vertexCount;
    private int[][] graphAdjacencyMatrix;

    public Graph(String filepath, GraphSourceType type) throws IOException {
        readGraph(filepath, type);
    }

    private void readGraph(String filepath, GraphSourceType type) throws IOException {
        throw new UnsupportedOperationException("needs implementation");
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
        throw new UnsupportedOperationException("needs implementation");
    }
}
