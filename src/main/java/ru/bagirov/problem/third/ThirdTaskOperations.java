package ru.bagirov.problem.third;

import ru.bagirov.problem.algorithms.GraphMathOperations;
import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;

import java.util.List;
import java.util.Set;

public class ThirdTaskOperations {

    private final Graph sourceGraph;

    private final TaskSpecCalculationResult<List<int[]>> bridges = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<Set<Integer>> pivots = new TaskSpecCalculationResult<>();

    public ThirdTaskOperations(final Graph sourceGraph) {
        this.sourceGraph = Graph.deepCopy(sourceGraph);
        this.sourceGraph.transformToRelatedGraph();
        calculateSpecs();
    }

    private void calculateSpecs() {
        Object[] data = GraphMathOperations.findBridgesAndPivots(sourceGraph);
        bridges.setSpec((List<int[]>)data[0]);
        pivots.setSpec((Set<Integer>)data[1]);
    }

    public List<int[]> getBridges() {
        return bridges.getSpec();
    }

    public Set<Integer> getPivots() {
        return pivots.getSpec();
    }

    public boolean getIsADirectedGraph() {
        return sourceGraph.isADirectedGraph();
    }
}
