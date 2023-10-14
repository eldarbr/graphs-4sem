package ru.bagirov.interfaces;

import ru.bagirov.problem.algorithms.Constants;

import java.util.List;
import java.util.Set;

public class OutputGenerator {

    public static String FirstTaskOutputGenerator(boolean isDirected, int[][] verticesDegree,
                                                  int[][] shortestDistMatrix, int diameter, int radius,
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
            for (int i = 0; i < verticesDegree.length; i++) {
                sb.append("\t");
                sb.append(verticesDegree[i][1]);
            }
            sb.append("\noutD:");
            for (int i = 0; i < verticesDegree.length; i++) {
                sb.append("\t");
                sb.append(verticesDegree[i][2]);
            }
        } else {
            sb.append("\ninoutD:");
            for (int i = 0; i < verticesDegree.length; i++) {
                sb.append("\t");
                sb.append(verticesDegree[i][0]);
            }
        }
        sb.append("\n\n");
        // Vertices Degree
        ////////////


        ////////////
        // Shortest Dist Matrix

        sb.append("Shortest Dist Matrix:");

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

        sb.append("Graph Diameter:\n");

        sb.append(diameter);

        sb.append("\n\n");

        // Graph Diameter
        ////////////

        ////////////
        // Graph Radius

        sb.append("Graph Radius:\n");

        sb.append(radius);

        sb.append("\n\n");

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

    public static String ThirdTaskOutputGenerator(List<int[]> bridges, Set<Integer> pivots) {
        StringBuilder sb = new StringBuilder();
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

    public static String FourthTaskOutputGenerator(List<List<Integer>> MSTEdges,
                                                   int MSTWeight, List<Long> calculationTime) {
        StringBuilder sb = new StringBuilder();

        sb.append("MST weight:\t");
        sb.append(MSTWeight);


        if (calculationTime!=null) {
            sb.append("\n\nAlgorithm comparison:\n");
            sb.append("Kruskala:\t");
            sb.append(calculationTime.get(0)/1000);
            sb.append(" microsec\nPrima:\t\t");
            sb.append(calculationTime.get(1)/1000);
            sb.append(" microsec\nBoruvka:\t");
            sb.append(calculationTime.get(2)/1000);
            sb.append(" microsec");
        }



        sb.append("\n\nMST edges:\n");
        for (List<Integer> edge : MSTEdges) {
            sb.append(edge.get(0)+1);
            sb.append("\t");
            sb.append(edge.get(1)+1);
            sb.append("\t");
            sb.append(edge.get(2));
            sb.append("\n");
        }


        return sb.toString();
    }

    public static String FifthTaskOutputGenerator(int pathLength, List<List<Integer>> pathEdges) {
        StringBuilder sb = new StringBuilder();

        if (pathLength == Constants.INF) {
            sb.append("Path can't be found");
            return sb.toString();
        }

        sb.append("Path length: ");
        sb.append(pathLength);

        sb.append("\n\nPath edges:\n");
        for (List<Integer> edge: pathEdges) {
            sb.append(edge.get(0)+1);
            sb.append("\t");
            sb.append(edge.get(1)+1);
            sb.append("\t");
            sb.append(edge.get(2));
            sb.append("\n");
        }

        return sb.toString();
    }
}
