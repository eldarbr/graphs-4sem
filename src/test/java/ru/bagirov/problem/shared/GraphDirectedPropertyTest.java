package ru.bagirov.problem.shared;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GraphDirectedPropertyTest {
    GraphDirectedProperty testClass = new GraphDirectedProperty();

    @Test
    public void emptyMatrixTest() {
        int[][] data = {};
        boolean expectedIsDirected = false;
        assertThat(testClass.isDirected(data)).isEqualTo(expectedIsDirected);
    }

    @Test
    public void oneElementTest() {
        int[][] data = {{1}};
        boolean expectedIsDirected = false;
        assertThat(testClass.isDirected(data)).isEqualTo(expectedIsDirected);
    }

    @Test
    public void fourElementNotDirectedTest() {
        int[][] data = {{0,1},{1,0}};
        boolean expectedIsDirected = false;
        assertThat(testClass.isDirected(data)).isEqualTo(expectedIsDirected);
    }

    @Test
    public void fourElementCycledNotDirectedTest() {
        int[][] data = {{1,1},{1,0}};
        boolean expectedIsDirected = false;
        assertThat(testClass.isDirected(data)).isEqualTo(expectedIsDirected);
    }

    @Test
    public void fourElementDirectedTest() {
        int[][] data = {{0,1},{2,0}};
        boolean expectedIsDirected = true;
        assertThat(testClass.isDirected(data)).isEqualTo(expectedIsDirected);
    }

    @Test
    public void fourElementCycledDirectedTest() {
        int[][] data = {{1,2},{1,0}};
        boolean expectedIsDirected = true;
        assertThat(testClass.isDirected(data)).isEqualTo(expectedIsDirected);
    }

    @Test
    public void tenByTenNotDirectedTest() {
        int[][] data = {
                {0, 0, 0, 19, 4, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 25, 0, 0, 4, 20, 0},
                {0, 0, 0, 16, 0, 0, 0, 0, 0, 0},
                {19, 0, 16, 0, 21, 0, 0, 0, 0, 0},
                {4, 25, 0, 21, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 11, 0, 25},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 24},
                {0, 4, 0, 0, 0, 11, 0, 0, 0, 0},
                {0, 20, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 25, 24, 0, 0, 0}
        };
        boolean expectedIsDirected = false;
        assertThat(testClass.isDirected(data)).isEqualTo(expectedIsDirected);
    }
}
