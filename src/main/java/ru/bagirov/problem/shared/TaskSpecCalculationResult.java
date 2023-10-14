package ru.bagirov.problem.shared;

public class TaskSpecCalculationResult<T> {
    T spec;
    boolean isCalculated;

    public TaskSpecCalculationResult() {
        isCalculated = false;
    }

    public T getSpec() {
        if (!isCalculated) {
            return null;
        }
        return spec;
    }

    public void setSpec(T spec) {
        if (isCalculated) {
            throw new UnsupportedOperationException("The specification was set already");
        }
        this.spec = spec;
        isCalculated = true;
    }

    public boolean getIsCalculated() {
        return isCalculated;
    }
}
