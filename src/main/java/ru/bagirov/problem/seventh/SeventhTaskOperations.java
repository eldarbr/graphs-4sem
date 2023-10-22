package ru.bagirov.problem.seventh;

import ru.bagirov.problem.algorithms.CommonAlgorithms;
import ru.bagirov.problem.algorithms.Constants;
import ru.bagirov.problem.shared.Edge;
import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;

import java.util.ArrayList;
import java.util.List;

public class SeventhTaskOperations {
    private final Graph sourceGraph;

    private final TaskSpecCalculationResult<List<Edge>> pairsDistance = new TaskSpecCalculationResult<List<Edge>>();

    public SeventhTaskOperations(final Graph sourceGraph) {
        this.sourceGraph = Graph.deepCopy(sourceGraph);
        calculateSpecs();
    }

    private void calculateSpecs() {
        int x = sourceGraph.getVerticesCount();
        TTTGraph tttGraph = new TTTGraph(x, Graph.deepCopy(sourceGraph).getGraphAdjacencyMatrix());
        int[][] distance = tttGraph.johnsons();
        if (distance == null) {
            return;
        }
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                if (distance[i][j] < -Constants.INF || distance[i][j] > Constants.INF) {
                    continue;
                }
                if (distance[i][j] != 0) {
                    edgeList.add(new Edge(i, j, distance[i][j]));
                }
            }
        }
        pairsDistance.setSpec(edgeList);
    }

    public List<Edge> getPairsDistances() {
        return pairsDistance.getSpec();
    }
}
