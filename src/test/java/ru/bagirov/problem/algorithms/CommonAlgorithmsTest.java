package ru.bagirov.problem.algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.bagirov.problem.shared.Edge;
import ru.bagirov.problem.shared.Graph;

import java.util.List;

public class CommonAlgorithmsTest {

    @Test
    public void kruskalasMSTShort() {
        Graph src = new Graph(new int[][]{
                {0,3,0,0,1},
                {3,0,5,0,4},
                {0,5,0,2,6},
                {0,0,2,0,7},
                {1,4,6,7,0}
        });

        for (Edge edge : CommonAlgorithms.kruskalsMST(src)) {
            System.out.print(edge.from);
            System.out.print(edge.to);
            System.out.println(edge.weight);
        }
        System.out.println();
    }

    @Test
    public void primaMSTShort() {
        Graph src = new Graph(new int[][]{
                {0,3,0,0,1},
                {3,0,5,0,4},
                {0,5,0,2,6},
                {0,0,2,0,7},
                {1,4,6,7,0}
        });

        for (Edge edge : CommonAlgorithms.primaMST(src)) {
            System.out.print(edge.from);
            System.out.print(edge.to);
            System.out.println(edge.weight);
        }
        System.out.println();
    }

    @Test
    public void boruvkasMSTShort() {
        Graph src = new Graph(new int[][]{
                {0,3,0,0,1},
                {3,0,5,0,4},
                {0,5,0,2,6},
                {0,0,2,0,7},
                {1,4,6,7,0}
        });

        for (Edge edge : CommonAlgorithms.boruvkasMST(src)) {
            System.out.print(edge.from);
            System.out.print(edge.to);
            System.out.println(edge.weight);
        }
        System.out.println();
    }
}
