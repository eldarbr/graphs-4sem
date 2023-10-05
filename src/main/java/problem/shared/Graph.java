package problem.shared;

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

    public List<List<Integer>> getGraphEdgesList() {
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if (graphAdjacencyMatrix[i][j] != 0) {
                    List<Integer> newEdge = new ArrayList<>();
                    newEdge.add(i);
                    newEdge.add(j);
                    newEdge.add(graphAdjacencyMatrix[i][j]);
                    edges.add(newEdge);
                }
            }
        }
        return edges;
    }

    public List<List<Integer>> getVertexEdgesList(final int v) {
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < verticesCount; i++) {
            if (graphAdjacencyMatrix[v][i] != 0) {
                List<Integer> newEdge = new ArrayList<>();
                newEdge.add(i);
                newEdge.add(graphAdjacencyMatrix[v][i]);
                edges.add(newEdge);
            }
        }
        return edges;
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
            vertices.add(edgeInfo.get(0));
            vertices.add(edgeInfo.get(1));
        }

        int verticesCount = vertices.last()+1;
        int[][] dataArr = new int[verticesCount][verticesCount];

        // edge:
        // weighted = vi vj weight
        // not w. = vi vj
        boolean weighted = dataList.get(0).size() != 2;


        for (List<Integer> edgeInfo : dataList) {
            if (dataArr[edgeInfo.get(0)][edgeInfo.get(1)] != 0) {
                throw new IllegalArgumentException("File content is improper: edge is contained twice or more times");
            }
            if (weighted) {
                dataArr[edgeInfo.get(0)][edgeInfo.get(1)] = edgeInfo.get(2);
            } else {
                dataArr[edgeInfo.get(0)][edgeInfo.get(1)] = 1;
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
                dataArr[vertexIndex][destination] = 1;
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
