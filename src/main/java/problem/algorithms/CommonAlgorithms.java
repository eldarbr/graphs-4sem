package problem.algorithms;

import problem.shared.DisjointSet;
import problem.shared.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public static List<List<Integer>> BoruvkasMST(final Graph sourceGraph) {
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
}
