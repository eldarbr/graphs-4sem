package interfaces;

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
            {-k, -p, -b, -s}choose the algorithm to calculate (kruskala, prima, boruvka, comparing)
            
            This task (4) calculates:
            minimum spanning tree of the graph""";

    private static final String HELP_MESSAGE_FIFTH = """
                -n          source vertex id [0-k]
                -d          destination vertex id [0-k]
            
            This task (5) calculates:
            a geodesic chain between two vertices in the graph""";

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
        }
        System.out.println();
    }

    public static void printWrongInput(String info) {
        System.out.println("The input is wrong:");
        System.out.println(info);
    }

    public static Object[] argsHandler(String[] inpArgs) {

        String filepath = null;
        int inputType = -1;

        boolean helpRequested = false;
        int taskId = -1;

        int fourthAlgo = -1;
        boolean fourthTaskChosen = false;

        int[] fifthSrcDst = new int[] {-1, -1};


        for (int i = 0; i < inpArgs.length; i++) {
            if (inpArgs[i].equals("-t") && i < inpArgs.length-1) {
                taskId = Integer.parseInt(inpArgs[i+1]);
            }
            if (inpArgs[i].equals("-h")) {
                helpRequested = true;
            }
            if (inpArgs[i].equals("-e") && i < inpArgs.length-1) {
                inputType = 0;
                filepath = inpArgs[i+1];
            }
            if (inpArgs[i].equals("-m") && i < inpArgs.length-1) {
                inputType = 1;
                filepath = inpArgs[i+1];
            }
            if (inpArgs[i].equals("-l") && i < inpArgs.length-1) {
                inputType = 2;
                filepath = inpArgs[i+1];
            }
            if (inpArgs[i].equals("-k")) {
                if (fourthTaskChosen) {
                    throw new IllegalArgumentException("wrong key combination: -k, -p, -b, -s");
                }
                fourthAlgo = 0;
                fourthTaskChosen = true;
            }
            if (inpArgs[i].equals("-p")) {
                if (fourthTaskChosen) {
                    throw new IllegalArgumentException("wrong key combination: -k, -p, -b, -s");
                }
                fourthAlgo = 1;
                fourthTaskChosen = true;
            }
            if (inpArgs[i].equals("-b")) {
                if (fourthTaskChosen) {
                    throw new IllegalArgumentException("wrong key combination: -k, -p, -b, -s");
                }
                fourthAlgo = 2;
                fourthTaskChosen = true;
            }
            if (inpArgs[i].equals("-s")) {
                if (fourthTaskChosen) {
                    throw new IllegalArgumentException("wrong key combination: -k, -p, -b, -s");
                }
                fourthAlgo = 3;
                fourthTaskChosen = true;
            }
            if (inpArgs[i].equals("-n") && i < inpArgs.length-1) {
                try {
                    fifthSrcDst[0] = Integer.parseInt(inpArgs[i+1]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("wrong number format for the key -n");
                }
            }
            if (inpArgs[i].equals("-d") && i < inpArgs.length-1) {
                try {
                    fifthSrcDst[1] = Integer.parseInt(inpArgs[i+1]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("wrong number format for the key -d");
                }
            }
        }
        if (taskId < 1 || taskId > 5) {
            throw new IllegalArgumentException("wrong task id");
        }
        if (inputType == -1) {
            throw new IllegalArgumentException("wrong input provided");
        }
        if (taskId!=4 && fourthTaskChosen) {
            throw new IllegalArgumentException("keys: -k, -p, -b, -s - only allowed for the 4th task");
        }
        if (taskId == 4 && !fourthTaskChosen) {
            throw new IllegalArgumentException("one of the keys: -k, -p, -b, -s - were not provided the 4th task");
        }

        if (taskId == 5 && (fifthSrcDst[0] == -1 || fifthSrcDst[1] == -1)) {
            throw new IllegalArgumentException("for the 5th task no keys were provided: -n or -d");
        }

        if (taskId != 5 && (fifthSrcDst[0] != -1 || fifthSrcDst[1] != -1)) {
            throw new IllegalArgumentException("keys: -n, -d - only allowed for the 5th task");
        }

        return new Object[]{ taskId, helpRequested, inputType, filepath, fourthAlgo, fifthSrcDst};
    }

}
