package ru.bagirov.problem.first;

import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.algorithms.GraphMathOperations;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;
import ru.bagirov.problem.algorithms.CommonAlgorithms;


import java.util.List;

public class FirstTaskOperations {

    private final Graph sourceGraph;

    private final TaskSpecCalculationResult<int[][]> shortestDistMatrix = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<int[]> eccentricity = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<int[][]> verticesDegree = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<List<Integer>> centralVertices = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<List<Integer>> peripheralVertices = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<Integer> graphRadius = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<Integer> graphDiameter = new TaskSpecCalculationResult<>();


    public FirstTaskOperations(final Graph sourceGraph) {
        this.sourceGraph = Graph.deepCopy(sourceGraph);
        calculateSpecs();
    }

    private void calculateSpecs() {

        this.shortestDistMatrix.setSpec(CommonAlgorithms.floydWarshallShortestDist(sourceGraph));

        this.eccentricity.setSpec(GraphMathOperations.calculateEccentricity(shortestDistMatrix.getSpec()));

        if (!sourceGraph.isADirectedGraph()) {
            List<Integer> radiusAndDiameter = GraphMathOperations.calculateRadiusAndDiameter(eccentricity.getSpec());
            this.graphRadius.setSpec(radiusAndDiameter.get(0));
            this.graphDiameter.setSpec(radiusAndDiameter.get(1));
            this.centralVertices.setSpec(GraphMathOperations.calculateCentralVertices(eccentricity.getSpec(), graphRadius.getSpec()));
            this.peripheralVertices.setSpec(GraphMathOperations.calculatePeripheralVertices(eccentricity.getSpec(), graphDiameter.getSpec()));
        }

        this.verticesDegree.setSpec(GraphMathOperations.calculateVerticesDegree(sourceGraph));
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

    public Integer getGraphRadius() {
        return this.graphRadius.getSpec();
    }

    public Integer getGraphDiameter() {
        return this.graphDiameter.getSpec();
    }

    public List<Integer> getCentralVertices() {
        return this.centralVertices.getSpec();
    }

    public List<Integer> getPeripheralVertices() {
        return this.peripheralVertices.getSpec();
    }
}
