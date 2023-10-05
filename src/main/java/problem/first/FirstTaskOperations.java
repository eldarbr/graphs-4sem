package problem.first;

import problem.shared.Graph;
import problem.shared.TaskSpecCalculationResult;
import problem.algorithms.Constants;

import java.util.ArrayList;
import java.util.List;

public class FirstTaskOperations {

    private final Graph sourceGraph;

    private final TaskSpecCalculationResult<int[][]> shortestDistMatrix;
    private final TaskSpecCalculationResult<int[]> eccentricity;
    private final TaskSpecCalculationResult<int[][]> verticesDegree;
    private final TaskSpecCalculationResult<List<Integer>> centralVertices;
    private final TaskSpecCalculationResult<List<Integer>> peripheralVertices;
    private final TaskSpecCalculationResult<Integer> graphRadius;
    private final TaskSpecCalculationResult<Integer> graphDiameter;


    public FirstTaskOperations(final Graph graph) {
        sourceGraph = graph;

        shortestDistMatrix = new TaskSpecCalculationResult<>();
        eccentricity = new TaskSpecCalculationResult<>();
        verticesDegree = new TaskSpecCalculationResult<>();
        centralVertices = new TaskSpecCalculationResult<>();
        peripheralVertices = new TaskSpecCalculationResult<>();
        graphRadius = new TaskSpecCalculationResult<>();
        graphDiameter = new TaskSpecCalculationResult<>();

        calculateSpecs();
    }


    private void calculateSpecs() {
        _calculateShortestDist();
        _calculateEccentricity();
        _calculateRadiusAndDiameter();
        _calculateCentralVertices();
        _calculatePeripheralVertices();
        _calculateVerticesDegree();
    }


    private void _calculateShortestDist() {

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

        this.shortestDistMatrix.setSpec(shortestDistMatrix);
    }

    private void _calculateEccentricity() {
        if (!shortestDistMatrix.getIsCalculated()) {
            throw new IllegalStateException("Eccentricity can only be calculated after the matrix of distances is calculated");
        }

        final int verticesCount = sourceGraph.getVerticesCount();
        int[] eccentricity = new int[verticesCount];
        int[][] shortestDistMatrix = this.shortestDistMatrix.getSpec();
        for (int i = 0; i < verticesCount; i++) {
            eccentricity[i] = shortestDistMatrix[i][0];
            for (int j = 1; j < verticesCount; j++) {
                if (shortestDistMatrix[i][j] > eccentricity[i]) {
                    eccentricity[i] = shortestDistMatrix[i][j];
                }
            }
        }
        this.eccentricity.setSpec(eccentricity);
    }

    private void _calculateCentralVertices() {
        if (!graphRadius.getIsCalculated()) {
            throw new IllegalStateException("Central vertices can only be calculated after the radius is calculated");
        }

        List<Integer> centralVertices = new ArrayList<>();
        final int verticesCount = sourceGraph.getVerticesCount();
        int graphRadius = this.graphRadius.getSpec();
        int[] eccentricity = this.eccentricity.getSpec();
        for (int i = 0; i < verticesCount; i++) {
            if (eccentricity[i] == graphRadius) {
                centralVertices.add(i);
            }
        }
        this.centralVertices.setSpec(centralVertices);
    }

    private void _calculatePeripheralVertices() {
        if (!graphRadius.getIsCalculated()) {
            throw new IllegalStateException("Peripheral vertices can only be calculated after the radius is calculated");
        }

        List<Integer> peripheralVertices = new ArrayList<>();
        final int verticesCount = sourceGraph.getVerticesCount();
        int graphDiameter = this.graphDiameter.getSpec();
        int[] eccentricity = this.eccentricity.getSpec();
        for (int i = 0; i < verticesCount; i++) {
            if (eccentricity[i] == graphDiameter) {
                peripheralVertices.add(i);
            }
        }
        this.peripheralVertices.setSpec(peripheralVertices);
    }

    private void _calculateRadiusAndDiameter() {
        if (!eccentricity.getIsCalculated()) {
            throw new IllegalStateException("Radius can only be calculated after the eccentricity is calculated");
        }

        int radius = Constants.INF;
        int diameter = -Constants.INF;
        final int verticesCount = sourceGraph.getVerticesCount();
        int[] eccentricity = this.eccentricity.getSpec();
        for (int i = 0; i < verticesCount; i++) {
            if (eccentricity[i] < radius) {
                radius = eccentricity[i];
            }
            if (eccentricity[i] > diameter) {
                diameter = eccentricity[i];
            }
        }
        this.graphRadius.setSpec(radius);
        this.graphDiameter.setSpec(diameter);
    }

    // inout-in-out
    private void _calculateVerticesDegree() {

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

        this.verticesDegree.setSpec(data);
    }

    public Graph getSourceGraph() {
        return sourceGraph;
    }

    public int[][] getVerticesDegree() {
        return verticesDegree.getSpec();
    }

    public int[][] getShortestDistMatrix() {
        return shortestDistMatrix.getSpec();
    }

    public int[] getEccentricity() {
        return this.eccentricity.getSpec();
    }

    public int getGraphRadius() {
        return this.graphRadius.getSpec();
    }

    public int getGraphDiameter() {
        return this.graphDiameter.getSpec();
    }

    public List<Integer> getCentralVertices() {
        return this.centralVertices.getSpec();
    }

    public List<Integer> getPeripheralVertices() {
        return this.peripheralVertices.getSpec();
    }
}
