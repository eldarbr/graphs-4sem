
import interfaces.ConsoleInterface;
import interfaces.OutputGenerator;
import problem.shared.Graph;
import problem.shared.GraphSourceType;

import problem.first.FirstTaskOperations;
import problem.second.SecondTaskOperations;
import problem.third.ThirdTaskOperations;
import problem.fourth.FourthTaskOperations;
import problem.fifth.FifthTaskOperations;

import java.io.FileNotFoundException;

public class GraphT {
    public static void main(String[] args) {
        Object[] data;
        try {
            data = ConsoleInterface.argsHandler(args);
        } catch (IllegalArgumentException exc) {
            ConsoleInterface.printWrongInput(exc.getMessage());
            System.exit(1);
            return;
        }
        int taskID = (Integer)data[0];

        if ((Boolean)data[1]) {
            ConsoleInterface.printHelp(taskID);
            System.exit(0);
            return;
        }



        Graph myGraph;
        try {
            myGraph = new Graph((String)data[3], GraphSourceType.values()[(Integer)data[2]]);
        } catch (FileNotFoundException fileNotFoundException) {
            ConsoleInterface.printWrongInput("file not found. can't input the graph");
            System.exit(2);
            return;
        }

        String output = null;
        if (taskID == 1) {
            FirstTaskOperations firstTaskOperations = new FirstTaskOperations(myGraph);
            output =
                    OutputGenerator.FirstTaskOutputGenerator(firstTaskOperations.getSourceGraph().isADirectedGraph(),
                            firstTaskOperations.getVerticesDegree(), firstTaskOperations.getShortestDistMatrix(),
                            firstTaskOperations.getGraphDiameter(), firstTaskOperations.getGraphRadius(),
                            firstTaskOperations.getCentralVertices(), firstTaskOperations.getPeripheralVertices());

        } else if (taskID == 2) {
            SecondTaskOperations secondTaskOperations = new SecondTaskOperations(myGraph);
            output = OutputGenerator.SecondTaskOutputGenerator(secondTaskOperations.isADirectedGraph(),
                    secondTaskOperations.getGraphConnectedness(), secondTaskOperations.getWeakConnectionComponents(),
                    secondTaskOperations.getDirGraphStrongConnectedness(), secondTaskOperations.getStrongConnectionComponents());

        } else if (taskID == 3) {
            ThirdTaskOperations thirdTaskOperations = new ThirdTaskOperations(myGraph);
            output = OutputGenerator.ThirdTaskOutputGenerator(thirdTaskOperations.getBridges(),
                    thirdTaskOperations.getPivots());

        } else if (taskID == 4) {
            FourthTaskOperations fourthTaskOperations = new FourthTaskOperations(myGraph, (Integer)data[4]);
            output = OutputGenerator.FourthTaskOutputGenerator(fourthTaskOperations.getMST(),
                    fourthTaskOperations.getMSTWeight(), fourthTaskOperations.getExecutionTime());

        } else if (taskID == 5) {
            FifthTaskOperations fifthTaskOperations = new FifthTaskOperations(myGraph, ((int[])data[5])[0], ((int[])data[5])[1]);

        }

        System.out.print(output);
    }
}
