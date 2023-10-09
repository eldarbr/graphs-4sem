package problem.fourth;

import problem.algorithms.CommonAlgorithms;
import problem.shared.Graph;
import problem.shared.TaskSpecCalculationResult;

import java.util.ArrayList;
import java.util.List;

public class FourthTaskOperations {
    private Graph sourceGraph;

    private TaskSpecCalculationResult<List<List<Integer>>> MSTEdgesList = new TaskSpecCalculationResult<>();
    private TaskSpecCalculationResult<Integer> MSTweight = new TaskSpecCalculationResult<>();
    private TaskSpecCalculationResult<List<Long>> calculationTime = new TaskSpecCalculationResult<>();

    public FourthTaskOperations(final Graph sourceGraph, final int algorithm){
        this.sourceGraph = Graph.deepCopy(sourceGraph);

        if (this.sourceGraph.isADirectedGraph()) {
            this.sourceGraph.transformToRelatedGraph();
        }

        calculateSpecs(algorithm);
    }

    private void calculateSpecs(final int algorithm) {
        if (algorithm == 0) {
            MSTEdgesList.setSpec(CommonAlgorithms.kruskalsMST(sourceGraph));
        } else if (algorithm == 1) {
            MSTEdgesList.setSpec(CommonAlgorithms.primaMST(sourceGraph));
        } else if (algorithm == 2) {
            MSTEdgesList.setSpec(CommonAlgorithms.boruvkasMST(sourceGraph));
        } else if (algorithm == 3) {
            compareExecutionTime();
        } else {
            throw new IllegalArgumentException("Wrong algorithm index");
        }
        MSTweight.setSpec(_calculateMSTWeight());
    }

    private int _calculateMSTWeight() {
        if (!MSTEdgesList.getIsCalculated()) {
            throw new IllegalStateException("wrong order: calculate mst first");
        }
        int weight = 0;
        for (List<Integer> edge: MSTEdgesList.getSpec()) {
            weight += edge.get(2);
        }
        return weight;
    }

    public List<List<Integer>> getMST() {
        return MSTEdgesList.getSpec();
    }

    public List<Long> getExecutionTime() {
        return calculationTime.getSpec();
    }

    private void compareExecutionTime() {
        calculationTime.setSpec(new ArrayList<>());
        List<List<Integer>> result = new ArrayList<>();

        long startNanos = System.nanoTime();
        result= CommonAlgorithms.kruskalsMST(sourceGraph);
        long endNanos = System.nanoTime();
        calculationTime.getSpec().add(endNanos-startNanos);

        startNanos = System.nanoTime();
        CommonAlgorithms.primaMST(sourceGraph);
        endNanos = System.nanoTime();
        calculationTime.getSpec().add(endNanos-startNanos);

        startNanos = System.nanoTime();
        CommonAlgorithms.boruvkasMST(sourceGraph);
        endNanos = System.nanoTime();
        calculationTime.getSpec().add(endNanos-startNanos);


        MSTEdgesList.setSpec(result);
    }

    public int getMSTWeight() {
        return this.MSTweight.getSpec();
    }
}
