package ru.bagirov.problem.sixth;

import ru.bagirov.problem.algorithms.CommonAlgorithms;
import ru.bagirov.problem.algorithms.GraphMathOperations;
import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;

public class SixthTaskOperations {

    private final Graph sourceGraph;
    private final TaskSpecCalculationResult<int[]> shortestDistances = new TaskSpecCalculationResult<>();

    public SixthTaskOperations(final Graph sourceGraph, final int srcV, final SixthTaskAlgorithm algorithm) {
        this.sourceGraph = Graph.deepCopy(sourceGraph);
        calculateSpecs(srcV, algorithm);
    }

    private void calculateSpecs(final int srcV, final SixthTaskAlgorithm algorithm) {
        if (srcV >= sourceGraph.getVerticesCount() || srcV < 0) {
            throw new IllegalArgumentException("vertex id out of bounds");
        }
        if (algorithm == SixthTaskAlgorithm.BELLMAN_FORD) {
            this.shortestDistances.setSpec(CommonAlgorithms.bellmanFordShortestDistances(sourceGraph, srcV));
            if (this.shortestDistances.getSpec() == null) {
                throw new UnsupportedOperationException("Graph contains a negative cycle");
            }
        } else if (algorithm == SixthTaskAlgorithm.DIJKSTRA) {
            if (GraphMathOperations.containsNegativeWeights(sourceGraph)) {
                throw new UnsupportedOperationException("Graph contains edges with negative weight");
            }
            this.shortestDistances.setSpec((int[])CommonAlgorithms.dijkstraShortestPath(sourceGraph, srcV, srcV)[2]);
        } else if (algorithm == SixthTaskAlgorithm.LEVIT) {
            if (CommonAlgorithms.bellmanFordShortestDistances(sourceGraph, srcV) == null) {
                throw new UnsupportedOperationException("Graph contains a negative cycle");
            }
            this.shortestDistances.setSpec(CommonAlgorithms.levitShortestDistances(sourceGraph, srcV));
        }
    }

    public int[] getShortestDistances() {
        return shortestDistances.getSpec();
    }
}
