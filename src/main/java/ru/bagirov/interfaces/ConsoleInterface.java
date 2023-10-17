package ru.bagirov.interfaces;

public class ConsoleInterface {

    private static final String HELP_MESSAGE_COMMON = """
            
            Eldar Bagirov, M3O-311B-21 - GraphT
            
                -h          print this help message and exit
            -t [taskID]     choose the task to resolve
            {-e, -m, -l}    choose the type of input data type (edges, matrix, list)
            -o [filepath]   optional - output to the file""";
    private static final String HELP_MESSAGE_FIRST = """


            This task (1) calculates:
            vertices degree, shortest distance matrix, diameter, radius,
            (if the graph is not directed): central and peripheral vertices""";

    private static final String HELP_MESSAGE_SECOND = """
            
            
            This task (2) calculates:
            graph connectedness and number of connectedness components""";

    private static final String HELP_MESSAGE_THIRD = """
            
            
            This task (3) calculates:
            graph bridges and pivots
            If a directed graph is the input, calculations are done for its correlated graph""";

    private static final String HELP_MESSAGE_FOURTH = """
            
            {-k, -p, -b, -s}choose the algorithm to calculate with (kruskala, prima, boruvka, comparing)
            
            This task (4) calculates:
            minimum spanning tree of the graph""";

    private static final String HELP_MESSAGE_FIFTH = """
                
                -n          source vertex id [0-k]
                -d          destination vertex id [0-k]
            
            This task (5) calculates:
            a geodesic chain between two vertices in the graph""";

    private static final String HELP_MESSAGE_SIXTH = """
            
                -n          start vertex
             {-D, -L, -B}   choose the algorithm to calculate with (dijkstra, levit, bellman-ford)
            
            This task (6) calculates:
            distance from desired vertex to any other in the graph""";

    public static void printHelp(int taskID) {
        System.out.print(HELP_MESSAGE_COMMON);
        if (taskID == 1) {
            System.out.print(HELP_MESSAGE_FIRST);
        } else if (taskID == 2) {
            System.out.print(HELP_MESSAGE_SECOND);
        } else if (taskID == 3) {
            System.out.print(HELP_MESSAGE_THIRD);
        } else if (taskID == 4) {
            System.out.print(HELP_MESSAGE_FOURTH);
        } else if (taskID == 5) {
            System.out.print(HELP_MESSAGE_FIFTH);
        } else if (taskID == 6) {
            System.out.print(HELP_MESSAGE_SIXTH);
        }
        System.out.println();
    }

    public static void printWrongInput(String info) {
        System.out.println("The input is wrong:");
        System.out.println(info);
    }

}
