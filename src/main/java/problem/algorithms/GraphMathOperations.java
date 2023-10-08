package problem.algorithms;

import problem.shared.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GraphMathOperations {

    // max distance from minimum for each vertex to any vertex
    public static int[] calculateEccentricity(int[][] shortestDistMatrix) {
        final int verticesCount = shortestDistMatrix.length;
        int[] eccentricity = new int[verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            eccentricity[i] = shortestDistMatrix[i][0];
            for (int j = 1; j < verticesCount; j++) {
                if (shortestDistMatrix[i][j] > eccentricity[i]) {
                    eccentricity[i] = shortestDistMatrix[i][j];
                }
            }
        }
        return eccentricity;
    }

    public static List<Integer> calculateRadiusAndDiameter(int[] eccentricity) {
        int radius = Constants.INF;
        int diameter = -Constants.INF;
        final int verticesCount = eccentricity.length;
        for (int i = 0; i < verticesCount; i++) {
            if (eccentricity[i] < radius) {
                radius = eccentricity[i];
            }
            if (eccentricity[i] > diameter) {
                diameter = eccentricity[i];
            }
        }
        return List.of(radius, diameter);
    }

    public static List<Integer> calculateCentralVertices(int[] eccentricity, int graphRadius) {

        List<Integer> centralVertices = new ArrayList<>();
        final int verticesCount = eccentricity.length;
        for (int i = 0; i < verticesCount; i++) {
            if (eccentricity[i] == graphRadius) {
                centralVertices.add(i);
            }
        }
        return centralVertices;
    }

    public static List<Integer> calculatePeripheralVertices(int[] eccentricity, int graphDiameter) {
        List<Integer> peripheralVertices = new ArrayList<>();
        final int verticesCount = eccentricity.length;
        for (int i = 0; i < verticesCount; i++) {
            if (eccentricity[i] == graphDiameter) {
                peripheralVertices.add(i);
            }
        }
        return peripheralVertices;
    }


    // inout-in-out
    public static int[][] calculateVerticesDegree(final Graph sourceGraph) {

        int vc = sourceGraph.getVerticesCount();
        int[][] data = new int[vc][3];

        for (int i = 0; i < vc; i++) {
            for (int j = 0; j < vc; j++) {
                boolean iToj = sourceGraph.isEdge(i, j);
                boolean jToi = sourceGraph.isEdge(j, i);

                if (jToi) {
                    data[i][1]++;
                }
                if (iToj) {
                    data[i][2]++;
                }
                if (iToj || jToi) {
                    data[i][0]++;
                }
            }
        }

        return data;
    }

    public static List<List<Integer>> calculateWeakConnections(final Graph sourceGraph) {
        int verticesCount = sourceGraph.getVerticesCount();
        List<List<Integer>> components = new ArrayList<>();
        boolean[] visitedVertices = new boolean[verticesCount];


        int[][] adjMatrixCopy = new int[verticesCount][verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            adjMatrixCopy[i] = Arrays.copyOf(sourceGraph.getGraphAdjacencyMatrix()[i], verticesCount);
        }

        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if (adjMatrixCopy[i][j] != 0) {
                    adjMatrixCopy[j][i] = adjMatrixCopy[i][j];
                }
            }
        }

        for (int i = 0; i < verticesCount; i++) {
            boolean[] newVisitedVertices = GraphSearch.BFSConnectedness(new Graph(adjMatrixCopy), i, visitedVertices);
            if (!visitedVertices[i]) {
                List<Integer> currentComponents = new ArrayList<>();
                for (int j = 0; j < verticesCount; j++) {
                    if (newVisitedVertices[j]) {
                        currentComponents.add(j+1);
                    }
                }
                components.add(currentComponents);
            }
            for (int j = 0; j < verticesCount; j++) {
                if (newVisitedVertices[j]) {
                    visitedVertices[j] = true;
                }
            }
        }

        return components;
    }

    public static List<List<Integer>> calculateStrongConnections(final Graph sourceGraph) {
        int verticesCount = sourceGraph.getVerticesCount();
        List<List<Integer>> components = new ArrayList<>();
        boolean[] visitedVertices = new boolean[verticesCount];

        int[][] adjMatrixCopyTransposed = new int[verticesCount][verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                adjMatrixCopyTransposed[i][j] = sourceGraph.getGraphAdjacencyMatrix()[j][i];
            }
        }
        Graph transposedSorted = new Graph(adjMatrixCopyTransposed);
        GraphMathOperations.sortTopologically(transposedSorted);

        for (int i = 0; i < verticesCount; i++) {
            boolean[] newVisitedVertices = GraphSearch.DFSConnectedness(transposedSorted, i, visitedVertices);
            if (!visitedVertices[i]) {
                List<Integer> currentComponents = new ArrayList<>();
                for (int j = 0; j < verticesCount; j++) {
                    if (newVisitedVertices[j]) {
                        currentComponents.add(j+1);
                    }
                }
                components.add(currentComponents);
            }
            for (int j = 0; j < verticesCount; j++) {
                if (newVisitedVertices[j]) {
                    visitedVertices[j] = true;
                }
            }
        }

        return components;
    }

    public static List<Integer> sortTopologically(final Graph graph) {
        int verticesCount = graph.getVerticesCount();
        boolean[] visitedVertices = new boolean[verticesCount];
        List<Integer> order = new ArrayList<>();

        for (int v = 0; v < verticesCount; v++) {
            if (!visitedVertices[v]) {
                GraphSearch.DFS(graph, order, visitedVertices, v);
            }
        }
        Collections.reverse(order);
        return order;
    }

    public static Object[] findBridgesAndPivots(final Graph sourceGraph) {

        int verticesCount = sourceGraph.getVerticesCount();

        final int[][] myAdjacencyMatrix = new int[verticesCount][];
        for (int i = 0; i < myAdjacencyMatrix.length; i++) {
            myAdjacencyMatrix[i] = Arrays.copyOf(sourceGraph.getGraphAdjacencyMatrix()[i], verticesCount);
        }

        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if (myAdjacencyMatrix[i][j] != 0) {
                    myAdjacencyMatrix[j][i] = myAdjacencyMatrix[i][j];
                }
            }
        }

        Graph myGraph = new Graph(myAdjacencyMatrix);
        int[] tin = new int[verticesCount];
        int[] tup = new int[verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            tin[i] = Integer.MAX_VALUE;
            tup[i] = Integer.MAX_VALUE;
        }
        boolean[] visitedVertices = new boolean[verticesCount];
        List<int[]> bridges = new ArrayList<>();
        List<Integer> pivots = new ArrayList<>();

        for (int n = 0; n < verticesCount; n++) {
            GraphSearch.DFSBridges(myGraph, n, null, visitedVertices, tin, tup, 0, bridges, pivots);
        }

        return new Object[]{bridges, pivots};
    }
}
