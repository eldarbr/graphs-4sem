package ru.bagirov.problem.first;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.bagirov.problem.shared.Graph;

public class FirstTaskOperationsTest {
    FirstTaskOperations firstTaskOperations;

    @Test
    public void verticesDegreeLegitShort() {

        firstTaskOperations = new FirstTaskOperations(new Graph(new int[][]{
                {1,0},
                {0,1}
        }));

        int[][] expected = {
                {2,2,2},
                {2,2,2}
        };
        int[][] actual = firstTaskOperations.getVerticesDegree();

        assertThat(firstTaskOperations.getVerticesDegree()).isEqualTo(expected);
    }


    @Test
    public void shortestDMatrixShort() {
        firstTaskOperations = new FirstTaskOperations(new Graph(new int[][]{
                {0,0,-2,0},
                {4,0,3,0},
                {0,0,0,2},
                {0,-1,0,0}
        }));
        int[][] expected = {
                {0,-1,-2,0},
                {4,0,2,4},
                {5,1,0,2},
                {3,-1,1,0}
        };

        assertThat(firstTaskOperations.getShortestDistMatrix()).isEqualTo(expected);
    }

    @Test
    public void eccentricityShort() {
        firstTaskOperations = new FirstTaskOperations(new Graph(new int[][]{
                {0,1,1,1,0,0,0},
                {1,0,0,0,1,0,0},
                {1,0,0,0,0,1,0},
                {1,0,0,0,1,1,0},
                {0,1,0,1,0,0,1},
                {0,0,1,1,0,0,1},
                {0,0,0,0,1,1,0}
        }));
        int[] expected = {
                3,
                3,
                3,
                2,
                3,
                3,
                3
        };

        assertThat(firstTaskOperations.getEccentricity()).isEqualTo(expected);
    }
}
