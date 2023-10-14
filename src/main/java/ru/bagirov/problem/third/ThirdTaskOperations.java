package ru.bagirov.problem.third;

import ru.bagirov.problem.algorithms.GraphMathOperations;
import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;

import java.util.List;

public class ThirdTaskOperations {

    private final Graph sourceGraph;

    private TaskSpecCalculationResult<List<int[]>> bridges = new TaskSpecCalculationResult<>();
    private TaskSpecCalculationResult<List<Integer>> pivots = new TaskSpecCalculationResult<>();

    public ThirdTaskOperations(final Graph sourceGraph) {
        this.sourceGraph = Graph.deepCopy(sourceGraph);
        this.sourceGraph.transformToRelatedGraph();
        calculateSpecs();
    }

    private void calculateSpecs() {
        Object[] data = GraphMathOperations.findBridgesAndPivots(sourceGraph);
        bridges.setSpec((List<int[]>)data[0]);
        pivots.setSpec((List<Integer>)data[1]);
    }

    public List<int[]> getBridges() {
        return bridges.getSpec();
    }

    public List<Integer> getPivots() {
        return pivots.getSpec();
    }
}
