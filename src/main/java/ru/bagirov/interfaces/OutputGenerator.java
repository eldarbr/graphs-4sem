package ru.bagirov.interfaces;

import ru.bagirov.problem.algorithms.Constants;
import ru.bagirov.problem.shared.Edge;

import java.util.List;
import java.util.Set;

public class OutputGenerator {

    public static String FirstTaskOutputGenerator(boolean isDirected, int[][] verticesDegree,
                                                  int[][] shortestDistMatrix, Integer diameter, Integer radius,
                                                  List<Integer> centralVertices, List<Integer> peripheralVertices) {
        StringBuilder sb = new StringBuilder();

        sb.append("The graph is ");
        if (isDirected) {
            sb.append("directed\n\n");
        } else {
            sb.append("not directed\n\n");
        }


        ////////////
        // Vertices Degree
        sb.append("Vertices degree:\n");
        for (int i = 0; i < verticesDegree.length; i++) {
            sb.append("\t");
            sb.append(i+1);
        }
        if (isDirected) {
            sb.append("\ninD:");
            for (int[] vertexDegree : verticesDegree) {
                sb.append("\t");
                sb.append(vertexDegree[1]);
            }
            sb.append("\noutD:");
            for (int[] vertexDegree : verticesDegree) {
                sb.append("\t");
                sb.append(vertexDegree[2]);
            }
        } else {
            sb.append("\ninoutD:");
            for (int[] vertexDegree : verticesDegree) {
                sb.append("\t");
                sb.append(vertexDegree[0]);
            }
        }
        sb.append("\n\n");
        // Vertices Degree
        ////////////


        ////////////
        // Shortest Dist Matrix

        sb.append("Shortest Dist Matrix:");

        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < shortestDistMatrix.length; i++) {
            sb.append("\n");
            for (int j = 0; j < shortestDistMatrix[0].length; j++) {
                if (j > 0) {
                    sb.append("\t");
                }
                int dat = shortestDistMatrix[i][j];
                if (dat == Constants.INF) {
                    sb.append("inf");
                } else {
                    sb.append(dat);
                }
            }
        }

        sb.append("\n\n");
        // Shortest Dist Matrix
        ////////////


        ////////////
        // Graph Diameter

        if (!isDirected && diameter!=null) {
            sb.append("Graph Diameter:\n");

            sb.append(diameter);

            sb.append("\n\n");
        }

        // Graph Diameter
        ////////////

        ////////////
        // Graph Radius

        if (!isDirected && radius!=null) {
            sb.append("Graph Radius:\n");

            sb.append(radius);

            sb.append("\n\n");
        }

        // Graph Radius
        ////////////

        ////////////
        // Central Vertices

        if (!isDirected && centralVertices!=null) {
            sb.append("Central vertices:\n");
            for (int vertex : centralVertices) {
                sb.append(vertex+1);
                sb.append("\t");
            }
            sb.append("\n\n");
        }

        // Central Vertices
        ////////////


        ////////////
        // Peripheral Vertices

        if (!isDirected && peripheralVertices!=null) {

            sb.append("Peripheral vertices:\n");
            for (int vertex : peripheralVertices) {
                sb.append(vertex+1);
                sb.append("\t");
            }
            sb.append("\n\n");
        }

        // Peripheral Vertices
        ////////////


        return sb.toString();
    }


    public static String SecondTaskOutputGenerator(boolean isDirected, boolean graphConnectedness,
                                                   List<List<Integer>> weakConnectionComponents,
                                                   boolean dirGraphStrongConnectedness,
                                                   List<List<Integer>> strongConnectionComponents) {

        StringBuilder sb = new StringBuilder();

        sb.append("The graph is ");
        if (isDirected) {
            sb.append("directed\n\n");
        } else {
            sb.append("not directed\n\n");
        }

        sb.append("The graph is ");
        if (!graphConnectedness) {
            sb.append("not ");

        }
        sb.append("connected");
        if (!graphConnectedness) {
            sb.append("\nContains ");
            sb.append(weakConnectionComponents.size());
            sb.append(" components");
        }

        sb.append("\n");

        for (List<Integer> component : weakConnectionComponents) {
            for (int vertex : component) {
                sb.append(vertex+1);
                sb.append("\t");
            }
            sb.append("\n");
        }

        sb.append("\n");


        if (isDirected) {
            sb.append("The directed graph is ");
            if (!dirGraphStrongConnectedness) {
                sb.append("not ");

            }
            sb.append("strongly connected\n");
            if (!dirGraphStrongConnectedness) {
                sb.append("Contains ");
                sb.append(strongConnectionComponents.size());
                sb.append(" strongly connected components\n");
            }
            for (List<Integer> component : strongConnectionComponents) {
                for (int vertex : component) {
                    sb.append(vertex+1);
                    sb.append("\t");
                }
                sb.append("\n");
            }
        }


        return sb.toString();
    }

    public static String ThirdTaskOutputGenerator(boolean isDirected, List<int[]> bridges, Set<Integer> pivots) {
        StringBuilder sb = new StringBuilder();

        sb.append("The graph is ");
        if (isDirected) {
            sb.append("directed\n\n");
        } else {
            sb.append("not directed\n\n");
        }

        sb.append("Graph bridges\n");
        for (int[] bridgeEdge : bridges) {
            sb.append(bridgeEdge[0]+1);
            sb.append("\t");
            sb.append(bridgeEdge[1]+1);
            sb.append("\n");
        }
        sb.append("\n\n");
        sb.append("Graph pivots\n");
        for (int pivotVertex : pivots) {
            sb.append(pivotVertex+1);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String FourthTaskOutputGenerator(List<Edge> MSTEdges,
                                                   int MSTWeight, List<Long> calculationTime) {
        StringBuilder sb = new StringBuilder();

        sb.append("MST weight:\t");
        sb.append(MSTWeight);


        if (calculationTime!=null) {
            sb.append("\n\nAlgorithm comparison:\n");
            sb.append("Kruskala:\t");
            sb.append(calculationTime.get(0)/1000);
            sb.append(" microseconds\nPrima:\t\t");
            sb.append(calculationTime.get(1)/1000);
            sb.append(" microseconds\nBoruvka:\t");
            sb.append(calculationTime.get(2)/1000);
            sb.append(" microseconds");
        }



        sb.append("\n\nMST edges:\n");
        for (Edge edge : MSTEdges) {
            sb.append(edge.from+1);
            sb.append("\t");
            sb.append(edge.to+1);
            sb.append("\t");
            sb.append(edge.weight);
            sb.append("\n");
        }


        return sb.toString();
    }

    public static String FifthTaskOutputGenerator(int srcV, int dstV, int pathLength, List<Edge> pathEdges) {
        StringBuilder sb = new StringBuilder();

        sb.append("Src vertex: ");
        sb.append(srcV+1);
        sb.append("\nDst vertex: ");
        sb.append(dstV+1);
        sb.append("\n\n");

        if (pathLength == Constants.INF) {
            sb.append("No path between the vertices");
            return sb.toString();
        }

        sb.append("Path length: ");
        sb.append(pathLength);

        sb.append("\n\nPath edges:\n");
        for (Edge edge: pathEdges) {
            sb.append(edge.from+1);
            sb.append("\t");
            sb.append(edge.to+1);
            sb.append("\t");
            sb.append(edge.weight);
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String SixthTaskOutputGenerator(final int srcV, final int[] distanceArray) {

        final int distanceArrayLength = distanceArray.length;
        if (srcV >= distanceArrayLength || srcV < 0) {
            throw new IllegalArgumentException("src vertex id not in range 0-%d".formatted(distanceArrayLength));
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Shortest paths from ");
        sb.append(srcV+1);
        sb.append("\nto\tdist\n");

        for (int i = 0; i < distanceArrayLength; i++) {
            if (i != srcV) {
                sb.append(i+1);
                sb.append("\t");
                int distance = distanceArray[i];
                if (distance == Constants.INF) {
                    sb.append("inf");
                } else {
                    sb.append(distance);
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
