package problem.third;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import problem.shared.Graph;

import java.util.Arrays;

public class ThirdTaskOperationsTest {
    @Test
    public void shortlegit() {
        final Graph gr = new Graph(new int[][] {
                {0,1,0},
                {1,0,1},
                {0,1,0}
        });
        ThirdTaskOperations tto = new ThirdTaskOperations(gr);
        System.out.println(tto.getPivots());
        for (int[] edge : tto.getBridges()) {
            System.out.println(Arrays.toString(edge));
        }
    }
}
