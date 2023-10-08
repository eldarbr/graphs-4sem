package problem.second;

import problem.shared.Graph;
import problem.algorithms.GraphMathOperations;
import problem.shared.TaskSpecCalculationResult;

import java.util.List;

public class SecondTaskOperations {

    private final Graph sourceGraph;

    private final TaskSpecCalculationResult<List<List<Integer>>> weakConnectionComponents = new TaskSpecCalculationResult<>();

    private final TaskSpecCalculationResult<List<List<Integer>>> strongConnectionComponents = new TaskSpecCalculationResult<>();



    public SecondTaskOperations(final Graph sourceGraph) {
        this.sourceGraph = sourceGraph;
        calculateSpecs();
    }

    private void calculateSpecs() {
        weakConnectionComponents.setSpec(GraphMathOperations.calculateWeakConnections(sourceGraph));
        if (sourceGraph.isADirectedGraph()) {
            strongConnectionComponents.setSpec(GraphMathOperations.calculateStrongConnections(sourceGraph));
        }
    }

    public int getWeakConnectionComponentsCount() {
        return weakConnectionComponents.getSpec().size();
    }

    public List<List<Integer>> getWeakConnectionComponents() {
        return weakConnectionComponents.getSpec();
    }

    public List<List<Integer>> getStrongConnectionComponents() {
        if (!sourceGraph.isADirectedGraph()) {
            throw new UnsupportedOperationException("The graph is not a directed graph");
        }
        return strongConnectionComponents.getSpec();
    }

    public int getStrongConnectionComponentsCount() {
        if (!sourceGraph.isADirectedGraph()) {
            throw new UnsupportedOperationException("The graph is not a directed graph");
        }
        return strongConnectionComponents.getSpec().size();
    }
}
