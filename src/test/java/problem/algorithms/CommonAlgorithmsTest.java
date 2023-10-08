package problem.algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import problem.shared.Graph;

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

        for (List<Integer> edge : CommonAlgorithms.kruskalsMST(src)) {
            System.out.print(edge.get(0));
            System.out.print(edge.get(1));
            System.out.println(edge.get(2));
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

        for (List<Integer> edge : CommonAlgorithms.primaMST(src)) {
            System.out.print(edge.get(0));
            System.out.print(edge.get(1));
            System.out.println(edge.get(2));
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

        for (List<Integer> edge : CommonAlgorithms.BoruvkasMST(src)) {
            System.out.print(edge.get(0));
            System.out.print(edge.get(1));
            System.out.println(edge.get(2));
        }
        System.out.println();
    }
}
