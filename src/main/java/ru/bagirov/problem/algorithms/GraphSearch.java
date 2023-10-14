package ru.bagirov.problem.algorithms;

import ru.bagirov.problem.shared.Graph;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;

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
        verticesQueue.add(startV);

        boolean[] markedVertices = new boolean[graph.getVerticesCount()];

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

    public static void DFSBridges(final Graph sourceGraph, int currentVertex, Integer prevVertex,
                                  boolean[] visitedVertices, int[] tin, int[] tout, int tick,
                                  List<int[]> bridges, Set<Integer> pivots) {


        visitedVertices[currentVertex] = true;
        tin[currentVertex] = tick;
        tout[currentVertex] = tick;

        int children = 0;

        for (int adjacentVertex : sourceGraph.getVertexAdjacencyList(currentVertex)) {
            if (prevVertex != null) {
                if (prevVertex == adjacentVertex) {
                    continue;
                }
            }

            if (visitedVertices[adjacentVertex]) {
                tout[currentVertex] = Math.min(tout[currentVertex], tin[adjacentVertex]);
            } else {
                DFSBridges(sourceGraph, adjacentVertex, currentVertex, visitedVertices, tin, tout, tick+1, bridges, pivots);

                tout[currentVertex] = Math.min(tout[currentVertex], tout[adjacentVertex]);
                if (tin[currentVertex] < tout[adjacentVertex]) {
                    bridges.add(new int[] {currentVertex, adjacentVertex});
                }

                if (tin[currentVertex] <= tout[adjacentVertex] && prevVertex != null) {
                    pivots.add(currentVertex);
                }
                children++;
            }
        }

        if (prevVertex == null && children > 1) {
            pivots.add(currentVertex);
        }
    }
}
