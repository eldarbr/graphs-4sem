package ru.bagirov.problem.fifth;

import ru.bagirov.problem.algorithms.CommonAlgorithms;
import ru.bagirov.problem.shared.Edge;
import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;

import java.util.List;

public class FifthTaskOperations {

    private final Graph sourceGraph;
    private final int sourceVertex;
    private final int destinationVertex;

    private final TaskSpecCalculationResult<Integer> pathLength = new TaskSpecCalculationResult<>();
    private final TaskSpecCalculationResult<List<Edge>> pathEdges = new TaskSpecCalculationResult<>();

    public FifthTaskOperations(final Graph sourceGraph, int sourceVertex, int destinationVertex) {
        this.sourceGraph = sourceGraph;
        this.sourceVertex = sourceVertex;
        this.destinationVertex = destinationVertex;
        calculateSpecs();
    }

    private void calculateSpecs() {
        Object[] data = CommonAlgorithms.dijkstraShortestPath(sourceGraph, sourceVertex, destinationVertex);
        this.pathLength.setSpec((Integer)data[0]);
        this.pathEdges.setSpec((List<Edge>)data[1]);
    }

    public Integer getPathLength() {
        return this.pathLength.getSpec();
    }

    public List<Edge> getPathEdges() {
        return this.pathEdges.getSpec();
    }
}
