package ru.bagirov.problem.second;

import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.algorithms.GraphMathOperations;
import ru.bagirov.problem.shared.TaskSpecCalculationResult;

import java.util.List;

public class SecondTaskOperations {

    private final Graph sourceGraph;

    private final TaskSpecCalculationResult<List<List<Integer>>> weakConnectionComponents = new TaskSpecCalculationResult<>();

    private final TaskSpecCalculationResult<List<List<Integer>>> strongConnectionComponents = new TaskSpecCalculationResult<>();



    public SecondTaskOperations(final Graph sourceGraph) {
        this.sourceGraph = Graph.deepCopy(sourceGraph);
        calculateSpecs();
    }

    private void calculateSpecs() {
        weakConnectionComponents.setSpec(GraphMathOperations.calculateWeakConnections(sourceGraph));
        if (sourceGraph.isADirectedGraph()) {
            strongConnectionComponents.setSpec(GraphMathOperations.calculateStrongConnections(sourceGraph));
        }
    }

    public Integer getWeakConnectionComponentsCount() {
        if (weakConnectionComponents.getIsCalculated()) {
            return weakConnectionComponents.getSpec().size();
        }
        return null;
    }

    public List<List<Integer>> getWeakConnectionComponents() {
        return weakConnectionComponents.getSpec();
    }

    public List<List<Integer>> getStrongConnectionComponents() {
        return strongConnectionComponents.getSpec();
    }

    public Integer getStrongConnectionComponentsCount() {
        return strongConnectionComponents.getSpec().size();
    }

    public boolean getGraphConnectedness() {
        if (weakConnectionComponents.getIsCalculated()){
            return weakConnectionComponents.getSpec().size() == 1;
        }
        throw new IllegalStateException("Weak connectedness components - Not calculated yet");
    }

    public boolean getDirGraphStrongConnectedness() {
        if (strongConnectionComponents.getIsCalculated()) {
            return strongConnectionComponents.getSpec().size() == 1;
        }
        return false;
    }

    public boolean isADirectedGraph() {
        return sourceGraph.isADirectedGraph();
    }
}
