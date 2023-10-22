package ru.bagirov.problem.algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.bagirov.problem.shared.Graph;

import java.util.List;

public class GraphMathOperationsTest {

    @Test
    public void topologicalSortShortLegit() {

        Graph srcG = new Graph(new int[][]{
                {0,0,0,1},
                {0,0,0,0},
                {0,1,0,0},
                {0,1,1,0}
        });
        List<Integer> sortRes = GraphMathOperations.sortTopologically(srcG);
        assertThat(sortRes).isEqualTo(List.of(0,1,2,3));
    }
}
