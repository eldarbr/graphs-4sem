package ru.bagirov.problem.algorithms;

import ru.bagirov.problem.shared.DisjointSet;
import ru.bagirov.problem.shared.Edge;
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


    public static List<Edge> kruskalsMST(final Graph sourceGraph) {
        if (sourceGraph.isADirectedGraph()) {
            throw new IllegalArgumentException("no directed graphs are allowed");
        }

        // sort by edge weight
        List<Edge> sortedEdgeList = sourceGraph.getGraphEdgesList();
        sortedEdgeList.sort(Comparator.comparingInt(list -> list.weight));

        List<Edge> MST = new ArrayList<>();

        // disjoint-set
        DisjointSet dsu = new DisjointSet(sourceGraph.getVerticesCount());

        // for each edge of the graph in the sorted order
        for (Edge edge : sortedEdgeList) {
            // if not a cycle
            if (dsu.findSet(edge.from) != dsu.findSet(edge.to)) {
                dsu.unionSet(edge.from, edge.to);
                // add the edge to the tree
                MST.add(edge);
            }
        }
        return MST;
    }

    public static List<Edge> primaMST(final Graph sourceGraph) {
        if (sourceGraph.isADirectedGraph()) {
            throw new IllegalArgumentException("no directed graphs are allowed");
        }

        // MST container
        List<Edge> MST = new ArrayList<>();

        int verticesCount = sourceGraph.getVerticesCount();
        boolean[] addedVertices = new boolean[verticesCount];
        addedVertices[0] = true;
        int addedVerticesCount = 1;

        List<Edge> edgeBuffer = new ArrayList<>(sourceGraph.getVertexEdgesList(0));
        edgeBuffer.sort(Comparator.comparingInt(list -> list.weight));

        while (addedVerticesCount < verticesCount) {
            for (Edge edge : edgeBuffer) {
                if (addedVertices[edge.from] && addedVertices[edge.to]) {
                    continue;
                }
                MST.add(edge);
                if (addedVertices[edge.from]) {
                    edgeBuffer.addAll(sourceGraph.getVertexEdgesList(edge.to));
                    addedVertices[edge.to] = true;
                } else {
                    edgeBuffer.addAll(sourceGraph.getVertexEdgesList(edge.from));
                    addedVertices[edge.from] = true;
                }
                addedVerticesCount++;
                edgeBuffer.remove(edge);
                edgeBuffer.sort(Comparator.comparingInt(list -> list.weight));
                break;
            }
        }

        return MST;
    }

    public static List<Edge> boruvkasMST(final Graph sourceGraph) {
        if (sourceGraph.isADirectedGraph()) {
            throw new IllegalArgumentException("no directed graphs are allowed");
        }

        final int verticesCount = sourceGraph.getVerticesCount();
        Edge[] cheapest = new Edge[verticesCount];
        DisjointSet disjointSet = new DisjointSet(verticesCount);
        int treesCount = verticesCount;

        List<Edge> MST = new ArrayList<>();

        while (treesCount > 1) {
            for (Edge edge : sourceGraph.getGraphEdgesList()) {
                int x = disjointSet.findSet(edge.from);
                int y = disjointSet.findSet(edge.to);

                if (x!=y) {
                    for (int val : List.of(x,y)) {
                        if (cheapest[val] == null) {
                            cheapest[val] = edge;
                        } else if (cheapest[val].weight > edge.weight) {
                            cheapest[val] = edge;
                        }
                    }
                }
            }

            for (Edge edge : cheapest) {
                if (edge == null) {
                    continue;
                }
                int x = disjointSet.findSet(edge.from);
                int y = disjointSet.findSet(edge.to);
                if (x != y) {
                    disjointSet.unionSet(x,y);
                    MST.add(edge);
                    treesCount--;
                }
            }
            cheapest = new Edge[verticesCount];
        }

        return MST;
    }

    public static Object[] dijkstraShortestPath(final Graph sourceGraph, int srcV, Integer destV) {

        int verticesCount = sourceGraph.getVerticesCount();

        if (srcV >= verticesCount || destV != null) {
            if (destV >= verticesCount || srcV >= verticesCount) {
                throw new IllegalArgumentException("false vertex index");
            }
        }

        LinkedList<int[]> verticesQueue = new LinkedList<>();
        int[] distanceArray = new int[verticesCount];
        Arrays.fill(distanceArray, Constants.INF);
        int[] pathArray = new int[verticesCount];
        Arrays.fill(pathArray, -1);

        distanceArray[srcV] = 0;
        verticesQueue.add(new int[] {0, srcV});

        while (!verticesQueue.isEmpty()) {
            int currentVert = verticesQueue.poll()[1];
            List<Edge> currIncEdges = sourceGraph.getVertexEdgesList(currentVert);

            for (Edge edge : currIncEdges) {
                if ((distanceArray[currentVert] + edge.weight) < distanceArray[edge.to]) {
                    distanceArray[edge.to] = distanceArray[currentVert] + edge.weight;
                    pathArray[edge.to] = currentVert;
                    verticesQueue.add(new int[] { distanceArray[edge.to], edge.to });
                }
            }
        }

        if (destV != null) {
            if (distanceArray[destV] == Constants.INF) {
                throw new ArithmeticException("no path between the vertices");
            }
        }

        if (destV != null) {
            List<Edge> pathEdges = new ArrayList<>();
            for (int curEdge = destV; curEdge != srcV;) {
                pathEdges.add(0, new Edge(pathArray[curEdge], curEdge, sourceGraph.weight(pathArray[curEdge], curEdge)));
                curEdge = pathArray[curEdge];
            }
            return new Object[]{distanceArray[destV], pathEdges, distanceArray};
        }
        return new Object[]{null,null,distanceArray};
    }


    // calculate shortest distances
    // returns null if there are negative cycles in the graph
    public static int[] bellmanFordShortestDistances(final Graph sourceGraph, final int srcV) {
        final int verticesCount = sourceGraph.getVerticesCount();

        int[] shortestDistances = new int[verticesCount];
        Arrays.fill(shortestDistances, Constants.INF);
        shortestDistances[srcV] = 0;


        for (int i = 1; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                List<Edge> edges = sourceGraph.getVertexEdgesList(j);
                for (final Edge edge : edges) {
                    if (shortestDistances[edge.from] != Constants.INF &&
                            shortestDistances[edge.from] + edge.weight < shortestDistances[edge.to]) {
                        shortestDistances[edge.to] = shortestDistances[edge.from] + edge.weight;
                    }
                }
            }
        }

        for (final Edge edge : sourceGraph.getGraphEdgesList()) {
            if (shortestDistances[edge.from] != Constants.INF &&
                    shortestDistances[edge.from] + edge.weight < shortestDistances[edge.to]) {
                return null;
            }
        }

        return shortestDistances;
    }

    public static int[] levitShortestDistances(final Graph sourceGraph, final int srcV) {
        final int verticesCount = sourceGraph.getVerticesCount();
        int[] shortestDistances = new int[verticesCount];
        Arrays.fill(shortestDistances, Constants.INF);
        shortestDistances[srcV] = 0;
        int[] state = new int[verticesCount];
        Arrays.fill(state, 2);
        state[srcV]=1;

        LinkedList<Integer> deque = new LinkedList<>();
        deque.add(srcV);

        while (!deque.isEmpty()) {
            int vertex = deque.poll();
            state[vertex] = 0;
            for (Edge edge : sourceGraph.getVertexEdgesList(vertex)) {
                if (shortestDistances[edge.to] == Constants.INF
                        || shortestDistances[edge.to] > shortestDistances[vertex]+edge.weight) {
                    shortestDistances[edge.to] = shortestDistances[vertex]+edge.weight;
                    if (state[edge.to] == 2) {
                        deque.add(edge.to);
                    } else if (state[edge.to] == 0) {
                        deque.addFirst(edge.to);
                    }
                    state[edge.to] = 1;
                }
            }
        }
        return shortestDistances;
    }


    public static Object[] fordFulkerson(final Graph sourceGraph, int[] sourceSink) {

        int[][] myMatrix = Graph.deepCopy(sourceGraph).getGraphAdjacencyMatrix();


        int source = sourceSink[0];
        int sink = sourceSink[1];

        Integer[] parent = new Integer[sourceGraph.getVerticesCount()];

        int maxFlow = 0;
        List<int[]> res = new ArrayList<>();

        while(GraphSearch.BFSFF(myMatrix, source, sink, parent)) {
            Integer pathFlow = null;
            int s = sink;
            while (s != source) {
                if (pathFlow == null) {
                    pathFlow = myMatrix[parent[s]][s];
                } else if (pathFlow > myMatrix[parent[s]][s]) {
                    pathFlow = myMatrix[parent[s]][s];
                }
                s = parent[s];
            }

            if (pathFlow != null) {
                maxFlow += pathFlow;
            }


            int v = sink;

            while (v != source) {
                int u = parent[v];
                myMatrix[u][v] -= pathFlow;
                myMatrix[v][u] += pathFlow;
                v = parent[v];
            }
        }
        final int[][] sourceMatrix = sourceGraph.getGraphAdjacencyMatrix();
        for (int i = 0; i < sourceGraph.getVerticesCount(); i++) {
            for (int j = 0; j < sourceGraph.getVerticesCount(); j++) {
                if (sourceMatrix[i][j] > 0) {
                    int flow = sourceMatrix[i][j] - myMatrix[i][j];
                    res.add(new int[]{i, j, flow, sourceMatrix[i][j]});
                } /*else if (sourceMatrix[i][j] == 0 && myMatrix[i][j] > 0) {
                    res.add(new int[]{i, j, myMatrix[i][j], sourceMatrix[i][j]});
                }*/
            }
        }
        return new Object[] {res, maxFlow};
    }

    public static int[] findSourceSink(final Graph sourceGraph) {
        int source = 0;
        int sink = 0;

        for (int i = 0; i < sourceGraph.getGraphAdjacencyMatrix().length; i++) {

            int outgoingEdges = 0;
            int incomingEdges = 0;

            for (int j = 0; j < sourceGraph.getGraphAdjacencyMatrix().length; j++) {
                if (sourceGraph.getGraphAdjacencyMatrix()[i][j] > 0)
                    outgoingEdges++;
                if (sourceGraph.getGraphAdjacencyMatrix()[j][i] > 0)
                    incomingEdges++;
            }

            if (outgoingEdges > 0 && incomingEdges == 0)
                source = i;
            else if (outgoingEdges == 0 && incomingEdges > 0)
                sink = i;
        }

        return new int[]{source, sink};
    }
}
