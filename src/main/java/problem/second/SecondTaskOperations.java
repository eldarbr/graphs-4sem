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
        this.sourceGraph = Graph.deepCopy(sourceGraph);
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

    public boolean getGraphConnectedness() {
        return weakConnectionComponents.getSpec().size() == 1;
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
