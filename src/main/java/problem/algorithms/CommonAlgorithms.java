package problem.algorithms;

import problem.shared.Graph;

public class CommonAlgorithms {


    public static int[][] floydWarshallShortestDist(final Graph sourceGraph) {

        final int verticesCount = sourceGraph.getVerticesCount();
        int[][] shortestDistMatrix = new int[verticesCount][verticesCount];


        // copy the contents of graph matrix
        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if (i==j) {
                    shortestDistMatrix[i][j] = 0;
                } else {
                    if (sourceGraph.isEdge(i,j)) {
                        shortestDistMatrix[i][j] = sourceGraph.weight(i,j);
                    } else {
                        shortestDistMatrix[i][j] = Constants.INF;
                    }
                }
            }
        }

        // WFL shortest distance matrix
        for (int k = 0; k < verticesCount; k++) {
            for (int i = 0; i < verticesCount; i++) {
                for (int j = 0; j < verticesCount; j++) {
                    if (shortestDistMatrix[i][j] > shortestDistMatrix[i][k]+shortestDistMatrix[k][j]) {
                        shortestDistMatrix[i][j] = shortestDistMatrix[i][k]+shortestDistMatrix[k][j];
                    }
                }
            }
        }

        return shortestDistMatrix;
    }
}
