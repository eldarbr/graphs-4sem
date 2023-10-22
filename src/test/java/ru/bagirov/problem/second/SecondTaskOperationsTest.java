package ru.bagirov.problem.second;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.bagirov.problem.shared.Graph;

public class SecondTaskOperationsTest {

    @Test
    public void weakConnectionShortLegit() {
        Graph graph = new Graph(new int[][]{
                {0,1,0},
                {1,0,0},
                {0,0,0}
        });

        SecondTaskOperations secondTaskOperations = new SecondTaskOperations(graph);

        assertThat(secondTaskOperations.getWeakConnectionComponentsCount()).isEqualTo(1);
    }
}
