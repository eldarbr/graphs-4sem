package ru.bagirov.problem.tenth;

import ru.bagirov.problem.algorithms.CommonAlgorithms;
import ru.bagirov.problem.shared.Edge;
import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;

import java.util.List;

public class TenthTaskOperations {
    private final Graph sourceGraph;

    private final TaskSpecCalculationResult<List<int[]>> flows = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<Integer> maxFlow = new TaskSpecCalculationResult<>();

    private final TaskSpecCalculationResult<int[]> sourceSink = new TaskSpecCalculationResult<>();

    public TenthTaskOperations(final Graph sourceGraph) {
        this.sourceGraph = Graph.deepCopy(sourceGraph);
        calculateSpecs();
    }

    private void calculateSpecs() {
        this.sourceSink.setSpec(CommonAlgorithms.findSourceSink(sourceGraph));
        Object[] data = CommonAlgorithms.fordFulkerson(sourceGraph, sourceSink.getSpec());
        this.flows.setSpec((List<int[]>)data[0]);
        this.maxFlow.setSpec((Integer)data[1]);
    }

    public Integer getMaxFlow() {
        return maxFlow.getSpec();
    }
    public List<int[]> getFlows() {
        return flows.getSpec();
    }
    public int[] getSourceSink() {
        return sourceSink.getSpec();
    }
}
