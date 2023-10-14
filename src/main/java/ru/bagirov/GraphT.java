package ru.bagirov;

import ru.bagirov.interfaces.ConsoleInterface;
import ru.bagirov.interfaces.FileWriter;
import ru.bagirov.interfaces.OutputGenerator;
import ru.bagirov.problem.shared.Graph;
import ru.bagirov.problem.shared.GraphSourceType;

import ru.bagirov.problem.first.FirstTaskOperations;
import ru.bagirov.problem.second.SecondTaskOperations;
import ru.bagirov.problem.third.ThirdTaskOperations;
import ru.bagirov.problem.fourth.FourthTaskOperations;
import ru.bagirov.problem.fifth.FifthTaskOperations;

import java.io.FileNotFoundException;
import java.io.IOException;

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
            FifthTaskOperations fifthTaskOperations;
            try {
                fifthTaskOperations = new FifthTaskOperations(myGraph, ((int[])data[5])[0], ((int[])data[5])[1]);
            } catch (IllegalArgumentException expected) {
                System.out.println("No path between the points");
                System.exit(0);
                return;
            }
            output = OutputGenerator.FifthTaskOutputGenerator(fifthTaskOperations.getPathLength(),
                    fifthTaskOperations.getPathEdges());
        }

        if (data[6] != null) {
            String ofp = (String)data[6];
            try {
                FileWriter.WriteToFile(ofp, output);
            } catch (IOException expected) {
                System.out.println("\n[ERR] Could not write to the file\n\n");
                System.out.println(output);
            }
        } else {
            System.out.println(output);
        }
    }
}
