package ru.bagirov.problem.algorithms;

import ru.bagirov.problem.shared.DisjointSet;
import ru.bagirov.problem.shared.Graph;

import java.util.*;

public class CommonAlgorithms {


    public static int[][] floydWarshallShortestDist(final Graph sourceGraph) {

        final int verticesCount = sourceGraph.getVerticesCount();
        int[][] shortestDistMatrix = new int[verticesCount][verticesCount];


        // copy the contents of graph matrix
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if (i==j) {
                    shortestDistMatrix[i][j] = 0;
                } else {
                    if (sourceGraph.isEdge(i,j)) {
                        shortestDistMatrix[i][j] = sourceGraph.weight(i,j);
                    } else {
                        shortestDistMatrix[i][j] = Constants.INF;
                    }
                }
            }
        }

        // WFL shortest distance matrix
        for (int k = 0; k < verticesCount; k++) {
            for (int i = 0; i < verticesCount; i++) {
                for (int j = 0; j < verticesCount; j++) {
                    if (shortestDistMatrix[i][j] > shortestDistMatrix[i][k]+shortestDistMatrix[k][j]) {
                        shortestDistMatrix[i][j] = shortestDistMatrix[i][k]+shortestDistMatrix[k][j];
                    }
                }
            }
        }

        return shortestDistMatrix;
    }


    public static List<List<Integer>> kruskalsMST(final Graph sourceGraph) {
        if (sourceGraph.isADirectedGraph()) {
            throw new IllegalArgumentException("no directed graphs are allowed");
        }

        // sort by edge weight
        List<List<Integer>> sortedEdgeList = sourceGraph.getGraphEdgesList();
        sortedEdgeList.sort(Comparator.comparingInt(list -> (Integer) list.get(2)));

        List<List<Integer>> MST = new ArrayList<>();

        // disjoint-set
        DisjointSet dsu = new DisjointSet(sourceGraph.getVerticesCount());

        // for each edge of the graph in the sorted order
        for (List<Integer> edge : sortedEdgeList) {
            // if not a cycle
            if (dsu.findSet(edge.get(0)) != dsu.findSet(edge.get(1))) {
                dsu.unionSet(edge.get(0), edge.get(1));
                // add the edge to the tree
                MST.add(edge);
            }
        }
        return MST;
    }

    public static List<List<Integer>> primaMST(final Graph sourceGraph) {
        if (sourceGraph.isADirectedGraph()) {
            throw new IllegalArgumentException("no directed graphs are allowed");
        }

        // MST container
        List<List<Integer>> MST = new ArrayList<>();

        int verticesCount = sourceGraph.getVerticesCount();
        boolean[] addedVertices = new boolean[verticesCount];
        addedVertices[0] = true;
        int addedVerticesCount = 1;

        List<List<Integer>> edgeBuffer = new ArrayList<>(sourceGraph.getVertexEdgesList(0));
        edgeBuffer.sort(Comparator.comparingInt(list -> (Integer)list.get(2)));

        while (addedVerticesCount < verticesCount) {
            for (List<Integer> edge : edgeBuffer) {
                if (addedVertices[edge.get(0)] && addedVertices[edge.get(1)]) {
                    continue;
                }
                MST.add(edge);
                if (addedVertices[edge.get(0)]) {
                    edgeBuffer.addAll(sourceGraph.getVertexEdgesList(edge.get(1)));
                    addedVertices[edge.get(1)] = true;
                } else {
                    edgeBuffer.addAll(sourceGraph.getVertexEdgesList(edge.get(0)));
                    addedVertices[edge.get(0)] = true;
                }
                addedVerticesCount++;
                edgeBuffer.remove(edge);
                edgeBuffer.sort(Comparator.comparingInt(list -> list.get(2)));
                break;
            }
        }

        return MST;
    }

    public static List<List<Integer>> boruvkasMST(final Graph sourceGraph) {
        if (sourceGraph.isADirectedGraph()) {
            throw new IllegalArgumentException("no directed graphs are allowed");
        }

        final int verticesCount = sourceGraph.getVerticesCount();
        List[] cheapest = new List[verticesCount];
        DisjointSet disjointSet = new DisjointSet(verticesCount);
        int treesCount = verticesCount;

        List<List<Integer>> MST = new ArrayList<>();

        while (treesCount > 1) {
            for (List<Integer> edge : sourceGraph.getGraphEdgesList()) {
                int x = disjointSet.findSet(edge.get(0));
                int y = disjointSet.findSet(edge.get(1));

                if (x!=y) {
                    for (int val : List.of(x,y)) {
                        if (cheapest[val] == null) {
                            cheapest[val] = edge;
                        } else if ((Integer)cheapest[val].get(2) > edge.get(2)) {
                            cheapest[val] = edge;
                        }
                    }
                }
            }

            for (List<Integer> edge : cheapest) {
                if (edge == null) {
                    continue;
                }
                int x = disjointSet.findSet(edge.get(0));
                int y = disjointSet.findSet(edge.get(1));
                if (x != y) {
                    disjointSet.unionSet(x,y);
                    MST.add(edge);
                    treesCount--;
                }
            }
            cheapest = new List[verticesCount];
        }

        return MST;
    }

    public static Object[] DijkstraShortestPath(final Graph sourceGraph, int srcV, int destV) {

        int verticesCount = sourceGraph.getVerticesCount();
        LinkedList<int[]> verticesQueue = new LinkedList<>();
        int[] distanceArray = new int[verticesCount];
        Arrays.fill(distanceArray, Constants.INF);
        int[] pathArray = new int[verticesCount];
        Arrays.fill(pathArray, -1);

        distanceArray[srcV] = 0;
        verticesQueue.add(new int[] {0, srcV});

        while (!verticesQueue.isEmpty()) {
            int currentVert = verticesQueue.poll()[1];
            List<List<Integer>> currIncEdges = sourceGraph.getVertexEdgesList(currentVert);

            for (List<Integer> edge : currIncEdges) {
                if ((distanceArray[currentVert] + edge.get(2)) < distanceArray[edge.get(1)]) {
                    distanceArray[edge.get(1)] = distanceArray[currentVert] + edge.get(2);
                    pathArray[edge.get(1)] = currentVert;
                    verticesQueue.add(new int[] { distanceArray[edge.get(1)], edge.get(1) });
                }
            }
        }

        if (distanceArray[destV] == Constants.INF) {
            throw new IllegalArgumentException("no path between the vertices");
        }

        List<List<Integer>> pathEdges = new ArrayList<>();
        for (int curEdge = destV; curEdge != srcV;) {
            List<Integer> edge = new ArrayList<>();
            edge.add(pathArray[curEdge]);
            edge.add(curEdge);
            edge.add(sourceGraph.weight(pathArray[curEdge], curEdge));
            pathEdges.add(0, edge);

            curEdge = pathArray[curEdge];
        }


        return new Object[]{distanceArray[destV], pathEdges};
    }
}
