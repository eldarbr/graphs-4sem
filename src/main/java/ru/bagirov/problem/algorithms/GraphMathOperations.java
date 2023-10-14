package ru.bagirov.problem.algorithms;

import ru.bagirov.problem.shared.Graph;

import java.util.*;

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
        //noinspection ForLoopReplaceableByForEach
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

        Graph myGraph = Graph.deepCopy(sourceGraph);
        myGraph.transformToRelatedGraph();

        for (int i = 0; i < verticesCount; i++) {
            boolean[] newVisitedVertices = GraphSearch.BFSConnectedness(myGraph, i, visitedVertices);
            if (!visitedVertices[i]) {
                List<Integer> currentComponents = new ArrayList<>();
                for (int j = 0; j < verticesCount; j++) {
                    if (newVisitedVertices[j]) {
                        currentComponents.add(j);
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

        int[][] sourceMatrix = sourceGraph.getGraphAdjacencyMatrix();
        int[][] transposedMatrix = new int[verticesCount][verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                transposedMatrix[j][i] = sourceMatrix[i][j];
            }
        }
        Graph transposedGraph = new Graph(transposedMatrix);

        List<Integer> order = sortTopologically(transposedGraph);
        boolean[] visitedVertices = new boolean[verticesCount];

        for (int vertex : order) {
            if (!visitedVertices[vertex]) {
                boolean[] newVisitedVertices = GraphSearch.DFSConnectedness(sourceGraph, vertex, visitedVertices);
                if (!visitedVertices[vertex]) {
                    List<Integer> newComponent = new ArrayList<>();
                    for (int i = 0; i < verticesCount; i++) {
                        if (newVisitedVertices[i]) {
                            newComponent.add(i);
                        }
                    }
                    components.add(newComponent);
                }
                for (int i = 0; i < verticesCount; i++) {
                    if (newVisitedVertices[i]) {
                        visitedVertices[i] = true;
                    }
                }
            }
        }
        return components;
    }

    public static List<Integer> sortTopologically(final Graph sourceGraph) {
        int verticesCount = sourceGraph.getVerticesCount();
        boolean[] visitedVertices = new boolean[verticesCount];
        List<Integer> order = new ArrayList<>();

        for (int v = 0; v < verticesCount; v++) {
            if (!visitedVertices[v]) {
                GraphSearch.DFS(sourceGraph, order, visitedVertices, v);
            }
        }
        Collections.reverse(order);
        return order;
    }

    public static Object[] findBridgesAndPivots(final Graph sourceGraph) {

        if (sourceGraph.isADirectedGraph()) {
            throw new IllegalArgumentException("no directed graphs are allowed");
        }


        int verticesCount = sourceGraph.getVerticesCount();

        Graph myGraph = Graph.deepCopy(sourceGraph);

        int[] tin = new int[verticesCount]; // keep track when for the first time that particular vertex is reached

        // keep track of the lowest possible time by which we can reach that vertex ‘other than parent’
        int[] tout = new int[verticesCount]; // if edge from parent is removed can the particular node can be reached other than parent

        boolean[] visitedVertices = new boolean[verticesCount];
        List<int[]> bridges = new ArrayList<>();
        Set<Integer> pivots = new TreeSet<>();

        for (int n = 0; n < verticesCount; n++) {
            GraphSearch.DFSBridges(myGraph, n, null, visitedVertices, tin, tout, 0, bridges, pivots);
        }

        return new Object[]{bridges, pivots};
    }
}
