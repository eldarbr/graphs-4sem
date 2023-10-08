package problem.third;

import problem.algorithms.GraphMathOperations;
import problem.shared.Graph;
import problem.shared.TaskSpecCalculationResult;

import java.util.List;

public class ThirdTaskOperations {

    private final Graph sourceGraph;

    private TaskSpecCalculationResult<List<int[]>> bridges = new TaskSpecCalculationResult<>();
    private TaskSpecCalculationResult<List<Integer>> pivots = new TaskSpecCalculationResult<>();

    public ThirdTaskOperations(final Graph sourceGraph) {
        this.sourceGraph = sourceGraph;
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
