package ru.bagirov.problem.shared;

import ru.bagirov.problem.algorithms.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {

    private final int verticesCount;
    private final int[][] graphAdjacencyMatrix;
    GraphDirectedProperty directedProperty;

    public Graph(final String filepath, final GraphSourceType type) throws FileNotFoundException {
        graphAdjacencyMatrix = new GraphReader(filepath, type).readData();
        verticesCount = graphAdjacencyMatrix.length;
        directedProperty = new GraphDirectedProperty();
    }

    public Graph(final int[][] graphAdjacencyMatrix) {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < graphAdjacencyMatrix.length; i++) {
            if (graphAdjacencyMatrix[i].length!=graphAdjacencyMatrix.length) {
                throw new IllegalArgumentException("not a quadratic matrix");
            }
        }
        this.graphAdjacencyMatrix = graphAdjacencyMatrix.clone();
        this.verticesCount = graphAdjacencyMatrix.length;
        directedProperty = new GraphDirectedProperty();
    }

    public int getVerticesCount() {
        return verticesCount;
    }

    public int weight(final int vi, final int vj) {
        if (graphAdjacencyMatrix[vi][vj] == 0) {
            return Constants.INF;
        }
        return graphAdjacencyMatrix[vi][vj];
    }

    public boolean isEdge(final int vi, final int vj) {
        return weight(vi, vj) != 0;
    }

    public int[][] getGraphAdjacencyMatrix() {
        return graphAdjacencyMatrix;
    }

    public List<Integer> getVertexAdjacencyList(final int v) {
        List<Integer> adjacentVertices = new LinkedList<>();
        for (int vi = 0; vi < verticesCount; vi++) {
            if (isEdge(v, vi)) {
                adjacentVertices.add(vi);
            }
        }
        return adjacentVertices;
    }

    // source - dest - weight
    public List<Edge> getGraphEdgesList() {
        //System.out.print("might be faulty Graph.getGraphEdgesList\n");
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < verticesCount; i++) {
            int j = i;
            if (isADirectedGraph()) {
                j = 0;
            }
            for (; j < verticesCount; j++) {
                if (graphAdjacencyMatrix[i][j] != 0) {
                    edges.add(new Edge(i, j, weight(i, j)));
                }
            }
        }
        return edges;
    }

    public List<Edge> getVertexEdgesList(final int v) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < verticesCount; i++) {
            if (isEdge(v, i)) {
                edges.add(new Edge(v, i, weight(v, i)));
            }
        }
        return edges;
    }

    public boolean isADirectedGraph() {
        return directedProperty.isDirected(graphAdjacencyMatrix);
    }


    public static Graph deepCopy(final Graph sourceGraph) {

        int verticesCount = sourceGraph.getVerticesCount();
        int[][] newAdjMatrix = new int[verticesCount][];
        for (int i = 0; i < newAdjMatrix.length; i++) {
            newAdjMatrix[i] = Arrays.copyOf(sourceGraph.getGraphAdjacencyMatrix()[i], verticesCount);
        }

        return new Graph(newAdjMatrix);
    }

    public void transformToRelatedGraph() {
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if (graphAdjacencyMatrix[i][j] != 0) {
                    graphAdjacencyMatrix[j][i] = graphAdjacencyMatrix[i][j];
                }
            }
        }
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

    boolean isDirected(final int[][] adjacencyMatrix) {
        int newHashCode = Arrays.deepHashCode(adjacencyMatrix);
        if (!initialised || prevArrayHash != newHashCode) {
            isDirected = calculateIsADirectedGraph(adjacencyMatrix);
            initialised = true;
            prevArrayHash = newHashCode;
        }
        return isDirected;
    }

    static boolean calculateIsADirectedGraph(final int[][] adjacencyMatrix) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i+1; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] != adjacencyMatrix[j][i]) {
                    return true;
                }
            }
        }
        return false;
    }

}

class GraphReader {
    private final File fileObject;
    private final GraphSourceType sourceType;

    GraphReader(final String filepath, final GraphSourceType sourceType) {
        this.sourceType = sourceType;

        this.fileObject = new File(filepath);
    }

    int[][] readData() throws FileNotFoundException {
        if (sourceType == GraphSourceType.ListOfEdges) {
            return readListOfEdges();
        } else if (sourceType == GraphSourceType.ListOfAdjacency) {
            return readListOfAdjacency();
        } else if (sourceType == GraphSourceType.AdjacencyMatrix) {
            return readAdjacencyMatrix();
        }
        throw new IllegalStateException("Source type unsupported");
    }

    private int[][] readListOfEdges() throws FileNotFoundException {
        return convertEdgesListToMatrix(readFileContentsToList());
    }

    private int[][] readListOfAdjacency() throws FileNotFoundException {
        return convertAdjacencyListToMatrix(readFileContentsToList());
    }

    private int[][] readAdjacencyMatrix() throws FileNotFoundException {
        return convertMatrixListToMatrix(readFileContentsToList());
    }

    private static int[][] convertEdgesListToMatrix(final List<List<Integer>> dataList) {
        TreeSet<Integer> vertices = new TreeSet<>();
        for (List<Integer> edgeInfo : dataList) {
            vertices.add(edgeInfo.get(0)-1);
            vertices.add(edgeInfo.get(1)-1);
        }

        int verticesCount = vertices.last()+1;
        int[][] dataArr = new int[verticesCount][verticesCount];

        // edge:
        // weighted = vi vj weight
        // not w. = vi vj
        boolean weighted = dataList.get(0).size() != 2;


        for (List<Integer> edgeInfo : dataList) {
            if (dataArr[edgeInfo.get(0)-1][edgeInfo.get(1)-1] != 0) {
                throw new IllegalArgumentException("File content is improper: edge is contained twice or more times");
            }
            if (weighted) {
                dataArr[edgeInfo.get(0)-1][edgeInfo.get(1)-1] = edgeInfo.get(2);
            } else {
                dataArr[edgeInfo.get(0)-1][edgeInfo.get(1)-1] = 1;
            }
        }
        return dataArr;
    }

    private static int[][] convertMatrixListToMatrix(final List<List<Integer>> dataList) {
        int size = dataList.size();
        int[][] dataArr = new int[size][size];

        int i = 0;
        int j = 0;
        for (List<Integer> line : dataList) {
            for (Integer value : line) {
                try{
                    dataArr[i][j] = value;
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Improper adjacency matrix - not a square");
                }
                j++;
            }
            if (j != size) {
                throw new IllegalArgumentException("Improper adjacency matrix - not a square");
            }
            j = 0;
            i++;
        }

        return dataArr;
    }

    private static int[][] convertAdjacencyListToMatrix(final List<List<Integer>> dataList) {
        int size = dataList.size();
        int[][] dataArr = new int[size][size];

        int vertexIndex = 0;
        for (List<Integer> vertexAdj : dataList) {
            for (int destination : vertexAdj) {
                dataArr[vertexIndex][destination-1] = 1;
            }
            vertexIndex++;
        }

        return dataArr;
    }

    private List<List<Integer>> readFileContentsToList() throws FileNotFoundException {
        List<List<Integer>> data = new ArrayList<>();
        Scanner scanner = new Scanner(fileObject);

        boolean _emptyLines = true;

        while (scanner.hasNextLine()) {
            Scanner lineScanner = new Scanner(scanner.nextLine());
            ArrayList<Integer> line = new ArrayList<>();
            while (lineScanner.hasNextInt()) {
                line.add(lineScanner.nextInt());
                _emptyLines = false;
            }
            data.add(line);
        }
        if (_emptyLines) {
            throw new IllegalArgumentException("File content is improper");
        }
        return data;
    }
}
