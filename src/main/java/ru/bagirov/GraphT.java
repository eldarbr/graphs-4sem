package ru.bagirov;

import ru.bagirov.interfaces.ArgsParser;
import ru.bagirov.interfaces.ConsoleInterface;
import ru.bagirov.interfaces.FileWriter;
import ru.bagirov.interfaces.OutputGenerator;
import ru.bagirov.problem.algorithms.Constants;
import ru.bagirov.problem.seventh.SeventhTaskOperations;
import ru.bagirov.problem.shared.Graph;

import ru.bagirov.problem.first.FirstTaskOperations;
import ru.bagirov.problem.second.SecondTaskOperations;
import ru.bagirov.problem.third.ThirdTaskOperations;
import ru.bagirov.problem.fourth.FourthTaskOperations;
import ru.bagirov.problem.fifth.FifthTaskOperations;
import ru.bagirov.problem.sixth.SixthTaskOperations;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GraphT {
    public static void main(String[] args) {
        ArgsParser parser;

        try {
            parser = new ArgsParser(args);
        } catch (IllegalArgumentException exc) {
            ConsoleInterface.printWrongInput(exc.getMessage());
            System.exit(1);
            return;
        }

        if (parser.getHelpRequested()) {
            ConsoleInterface.printHelp(parser.getTaskId());
            System.exit(0);
            return;
        }

        Graph myGraph;
        try {
            System.out.println("Reading the graph");
            myGraph = new Graph(parser.getInputFilePath(), parser.getInputType());
        } catch (FileNotFoundException fileNotFoundException) {
            ConsoleInterface.printWrongInput("File not found. Can't input the graph.");
            System.exit(2);
            return;
        }
        System.out.println("Reading is complete\n");

        String output = null;
        if (parser.getTaskId() == 1) {
            FirstTaskOperations firstTaskOperations = new FirstTaskOperations(myGraph);
            output =
                    OutputGenerator.FirstTaskOutputGenerator(firstTaskOperations.getSourceGraph().isADirectedGraph(),
                            firstTaskOperations.getVerticesDegree(), firstTaskOperations.getShortestDistMatrix(),
                            firstTaskOperations.getGraphDiameter(), firstTaskOperations.getGraphRadius(),
                            firstTaskOperations.getCentralVertices(), firstTaskOperations.getPeripheralVertices());

        } else if (parser.getTaskId() == 2) {
            SecondTaskOperations secondTaskOperations = new SecondTaskOperations(myGraph);
            output = OutputGenerator.SecondTaskOutputGenerator(secondTaskOperations.isADirectedGraph(),
                    secondTaskOperations.getGraphConnectedness(), secondTaskOperations.getWeakConnectionComponents(),
                    secondTaskOperations.getDirGraphStrongConnectedness(), secondTaskOperations.getStrongConnectionComponents());

        } else if (parser.getTaskId() == 3) {
            ThirdTaskOperations thirdTaskOperations = new ThirdTaskOperations(myGraph);
            output = OutputGenerator.ThirdTaskOutputGenerator(thirdTaskOperations.getIsADirectedGraph(),
                    thirdTaskOperations.getBridges(), thirdTaskOperations.getPivots());

        } else if (parser.getTaskId() == 4) {
            FourthTaskOperations fourthTaskOperations = new FourthTaskOperations(myGraph, parser.getFourthAlgorithm());
            output = OutputGenerator.FourthTaskOutputGenerator(fourthTaskOperations.getMST(),
                    fourthTaskOperations.getMSTWeight(), fourthTaskOperations.getExecutionTime());

        } else if (parser.getTaskId() == 5) {
            FifthTaskOperations fifthTaskOperations = null;
            Integer[] vertices = parser.getFifthVertices();
            try {
                fifthTaskOperations = new FifthTaskOperations(myGraph, vertices[0], vertices[1]);
            } catch (ArithmeticException expected) {
                //noinspection UnusedAssignment
                output = OutputGenerator.FifthTaskOutputGenerator(vertices[0], vertices[1], Constants.INF, null);
            } catch (IllegalArgumentException exception) {
                ConsoleInterface.printWrongInput("Illegal vertex id");
                System.exit(3);
                return;
            }

            if (fifthTaskOperations != null) {
                output = OutputGenerator.FifthTaskOutputGenerator(vertices[0], vertices[1],
                        fifthTaskOperations.getPathLength(),
                        fifthTaskOperations.getPathEdges());
            } else {
                throw new IllegalStateException();
            }
        } else if (parser.getTaskId() == 6) {
            SixthTaskOperations sixthTaskOperations = null;
            try {
                sixthTaskOperations = new SixthTaskOperations(myGraph, parser.getSixthStartVertex(),
                        parser.getSixthAlgorithm());
            } catch (UnsupportedOperationException expected) {
                output = expected.getMessage();
            } catch (IllegalArgumentException exc) {
                ConsoleInterface.printWrongInput("Illegal start vertex id");
                System.exit(3);
                return;
            }
            if (output == null && sixthTaskOperations != null) {
                output = OutputGenerator.SixthTaskOutputGenerator(parser.getSixthStartVertex(),
                        sixthTaskOperations.getShortestDistances());
            }
        } else if (parser.getTaskId() == 7) {
            SeventhTaskOperations seventhTaskOperations = new SeventhTaskOperations(myGraph);
            output = OutputGenerator.SeventhTaskOutputGenerator(seventhTaskOperations.getPairsDistances());
        }

        if (output == null) {
            throw new IllegalStateException();
        }

        String ofp = parser.getOutputFilePath();
        if (ofp != null) {
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
