package ru.bagirov.problem.algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.bagirov.problem.shared.Graph;


public class GraphSearchTest {

    @Test
    public void BFSConnectednessShortLegit() {

        Graph sourceGraph = new Graph(new int[][]{
                {0,1,1,0,0},
                {1,0,1,1,0},
                {1,1,0,0,1},
                {0,1,0,0,1},
                {0,0,1,1,0}
        });

        int[] mv = new int[5];
        boolean[] visited = new boolean[5];

        for (int i = 0; i < 5; i++) {

            boolean[] newVisited = GraphSearch.BFSConnectedness(sourceGraph, i, visited);
            for (int j = 0; j < 5; j++) {
                if (newVisited[j]) {
                    visited[j] = true;
                }
            }
        }
    }
}
