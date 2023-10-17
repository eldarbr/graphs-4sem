package ru.bagirov.problem.fourth;

import ru.bagirov.problem.algorithms.CommonAlgorithms;
import ru.bagirov.problem.shared.Edge;
import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;

import java.util.ArrayList;
import java.util.List;

public class FourthTaskOperations {
    private final Graph sourceGraph;

    private final TaskSpecCalculationResult<List<Edge>> MSTEdgesList = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<Integer> MSTweight = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<List<Long>> calculationTime = new TaskSpecCalculationResult<>();

    public FourthTaskOperations(final Graph sourceGraph, final FourthTaskAlgorithm algorithm){
        this.sourceGraph = Graph.deepCopy(sourceGraph);

        if (this.sourceGraph.isADirectedGraph()) {
            this.sourceGraph.transformToRelatedGraph();
        }

        calculateSpecs(algorithm);
    }

    private void calculateSpecs(final FourthTaskAlgorithm algorithm) {
        if (algorithm == FourthTaskAlgorithm.KRUSKALA) {
            MSTEdgesList.setSpec(CommonAlgorithms.kruskalsMST(sourceGraph));
        } else if (algorithm == FourthTaskAlgorithm.PRIMA) {
            MSTEdgesList.setSpec(CommonAlgorithms.primaMST(sourceGraph));
        } else if (algorithm == FourthTaskAlgorithm.BORUVKA) {
            MSTEdgesList.setSpec(CommonAlgorithms.boruvkasMST(sourceGraph));
        } else if (algorithm == FourthTaskAlgorithm.COMPARING) {
            compareExecutionTime();
        } else {
            throw new IllegalArgumentException("unsupported algorithm");
        }
        MSTweight.setSpec(_calculateMSTWeight());
    }

    private int _calculateMSTWeight() {
        if (!MSTEdgesList.getIsCalculated()) {
            throw new IllegalStateException("wrong order: calculate mst first");
        }
        int weight = 0;
        for (Edge edge: MSTEdgesList.getSpec()) {
            weight += edge.weight;
        }
        return weight;
    }

    public List<Edge> getMST() {
        return MSTEdgesList.getSpec();
    }

    public List<Long> getExecutionTime() {
        return calculationTime.getSpec();
    }

    private void compareExecutionTime() {
        calculationTime.setSpec(new ArrayList<>());
        List<Edge> result = new ArrayList<>();

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

    public Integer getMSTWeight() {
        return this.MSTweight.getSpec();
    }
}
