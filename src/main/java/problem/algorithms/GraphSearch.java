package problem.algorithms;

import problem.shared.Graph;

import java.util.List;
import java.util.LinkedList;

public class GraphSearch {

    public static boolean[] BFSConnectedness(final Graph graph, final int startV, boolean[] prevMarked) {

        LinkedList<Integer> verticesQueue = new LinkedList<>();

        boolean[] markedVertices = new boolean[graph.getVerticesCount()];

        verticesQueue.add(startV);
        markedVertices[startV] = true;

        int currentVertex;

        while (!verticesQueue.isEmpty()) {

            currentVertex = verticesQueue.poll();

            for (int adjacentVertex : graph.getVertexAdjacencyList(currentVertex)) {
                if (!markedVertices[adjacentVertex] && !prevMarked[currentVertex]) {
                    verticesQueue.add(adjacentVertex);
                    markedVertices[adjacentVertex] = true;
                }
            }
        }
        return markedVertices;
    }

    public static boolean[] DFSConnectedness(final Graph graph, final int startV, boolean[] prevMarked) {

        LinkedList<Integer> verticesQueue = new LinkedList<>();

        boolean[] markedVertices = new boolean[graph.getVerticesCount()];
        verticesQueue.add(startV);

        int currentVertex;

        while (!verticesQueue.isEmpty()) {

            currentVertex = verticesQueue.pop();
            if (markedVertices[currentVertex] || prevMarked[currentVertex]) {
                continue;
            }

            markedVertices[currentVertex] = true;
            for (int nextVertex : graph.getVertexAdjacencyList(currentVertex)) {
                if (!markedVertices[nextVertex] && !prevMarked[nextVertex]) {
                    verticesQueue.add(nextVertex);
                }
            }
        }

        return markedVertices;
    }


    // recursive dfs
    public static void DFS(final Graph sourceGraph, List<Integer> order, boolean[] visitedVertices, int vertex) {
        visitedVertices[vertex] = true;
        for (int adjVertex : sourceGraph.getVertexAdjacencyList(vertex)) {
            if (!visitedVertices[adjVertex]) {
                DFS(sourceGraph,order,visitedVertices,adjVertex);
            }
        }
        order.add(vertex);
    }
}
