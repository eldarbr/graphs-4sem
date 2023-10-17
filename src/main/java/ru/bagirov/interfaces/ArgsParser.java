package ru.bagirov.interfaces;

import ru.bagirov.problem.fourth.FourthTaskAlgorithm;
import ru.bagirov.problem.shared.GraphSourceType;
import ru.bagirov.problem.sixth.SixthTaskAlgorithm;

import java.util.NoSuchElementException;


public class ArgsParser {

    private Integer taskId = null;
    private boolean helpRequested = false;
    private GraphSourceType inputType = null;
    private String inputFilePath = null;
    private String outputFilePath = null;
    private FourthTaskAlgorithm fourthAlgorithm = null;
    private final Integer[] fifthSrcDst = new Integer[] {null, null};
    private SixthTaskAlgorithm sixthAlgorithm = null;
    private Integer sixthStartVertex = null;

    public ArgsParser(final String[] args) {
        _parseArgs(args);
        _checkArgsCollision();
    }

    private void _parseArgs(final String[] args) {

        final int argsCount = args.length;

        for (int i = 0; i < argsCount; i++) {
            if (args[i].equals("-o") && i < argsCount-1) {
                if (outputFilePath != null) {
                    throw new IllegalArgumentException("output filepath was specified twice");
                }
                outputFilePath = args[i+1];
            }

            if (args[i].equals("-t") && i < argsCount-1) {
                if (taskId != null) {
                    throw new IllegalArgumentException("task id was already specified");
                }
                taskId = Integer.parseInt(args[i+1]);
            }
            if (args[i].equals("-h")) {
                helpRequested = true;
            }
            if (args[i].equals("-e") && i < argsCount-1) {
                if (inputType!=null) {
                    throw new IllegalArgumentException("filepath and type were already specified");
                }
                inputType = GraphSourceType.ListOfEdges;
                inputFilePath = args[i+1];
            }
            if (args[i].equals("-m") && i < argsCount-1) {
                if (inputType!=null) {
                    throw new IllegalArgumentException("filepath and type were already specified");
                }
                inputType = GraphSourceType.AdjacencyMatrix;
                inputFilePath = args[i+1];
            }
            if (args[i].equals("-l") && i < argsCount-1) {
                if (inputType!=null) {
                    throw new IllegalArgumentException("filepath and type were already specified");
                }
                inputType = GraphSourceType.ListOfAdjacency;
                inputFilePath = args[i+1];
            }
            if (args[i].equals("-k")) {
                if (fourthAlgorithm != null) {
                    throw new IllegalArgumentException("wrong key combination: -k, -p, -b, -s");
                }
                fourthAlgorithm = FourthTaskAlgorithm.KRUSKALA;
            }
            if (args[i].equals("-p")) {
                if (fourthAlgorithm != null) {
                    throw new IllegalArgumentException("wrong key combination: -k, -p, -b, -s");
                }
                fourthAlgorithm = FourthTaskAlgorithm.PRIMA;
            }
            if (args[i].equals("-b")) {
                if (fourthAlgorithm != null) {
                    throw new IllegalArgumentException("wrong key combination: -k, -p, -b, -s");
                }
                fourthAlgorithm = FourthTaskAlgorithm.BORUVKA;
            }
            if (args[i].equals("-s")) {
                if (fourthAlgorithm != null) {
                    throw new IllegalArgumentException("wrong key combination: -k, -p, -b, -s");
                }
                fourthAlgorithm = FourthTaskAlgorithm.COMPARING;
            }
            if (args[i].equals("-n") && i < argsCount-1) {
                try {
                    int data = Integer.parseInt(args[i+1]);
                    fifthSrcDst[0] = data-1;
                    sixthStartVertex = data-1;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("wrong number format for the key -n");
                }
            }
            if (args[i].equals("-d") && i < argsCount-1) {
                try {
                    fifthSrcDst[1] = Integer.parseInt(args[i+1])-1;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("wrong number format for the key -d");
                }
            }
            if (args[i].equals("-D")) {
                if (sixthAlgorithm != null) {
                    throw new IllegalArgumentException("wrong key combination: -D, -B, -L");
                }
                sixthAlgorithm = SixthTaskAlgorithm.DIJKSTRA;
            }
            if (args[i].equals("-B")) {
                if (sixthAlgorithm != null) {
                    throw new IllegalArgumentException("wrong key combination: -D, -B, -L");
                }
                sixthAlgorithm = SixthTaskAlgorithm.BELLMAN_FORD;
            }
            if (args[i].equals("-L")) {
                if (sixthAlgorithm != null) {
                    throw new IllegalArgumentException("wrong key combination: -D, -B, -L");
                }
                sixthAlgorithm = SixthTaskAlgorithm.LEVIT;
            }
        }
    }

    private void _checkArgsCollision() {
        if (taskId == null) {
            throw new IllegalArgumentException("specify task id with -t");
        }
        if (inputType == null) {
            throw new IllegalArgumentException("wrong input type/fp provided");
        }
        if (taskId == 4 && fourthAlgorithm == null) {
            throw new IllegalArgumentException("one of the keys: (-k, -p, -b, -s) - were not provided the 4th task");
        }
        if (taskId !=4 && fourthAlgorithm != null) {
            throw new IllegalArgumentException("keys: -k, -p, -b, -s - are only allowed for the 4th task");
        }
        if (taskId == 5 && (fifthSrcDst[0] == null || fifthSrcDst[1] == null)) {
            throw new IllegalArgumentException("for the 5th task no keys were provided: -n or -d");
        }
        if (taskId != 5 && fifthSrcDst[1] != null) {
            throw new IllegalArgumentException("key -d is only allowed for the 5th task");
        }
        if (taskId != 5 && taskId != 6 && fifthSrcDst[0] != null) {
            throw new IllegalArgumentException("key -n is only allowed for the 5th and 6th tasks");
        }
        if (taskId == 6 && (sixthAlgorithm == null || sixthStartVertex == null)) {
            throw new IllegalArgumentException("one of the keys: -n, (-D, -B, -L) - were not provided the 6th task");
        }
        if (taskId != 6 && sixthAlgorithm != null) {
            throw new IllegalArgumentException("keys: -D, -B, -L - are only allowed for the 6th task");
        }
    }

    public int getTaskId() {
        return taskId;
    }

    public boolean getHelpRequested() {
        return helpRequested;
    }

    public GraphSourceType getInputType() {
        if (helpRequested) {
            throw new UnsupportedOperationException("help was requested");
        }
        return inputType;
    }

    public String getInputFilePath() {
        if (helpRequested) {
            throw new UnsupportedOperationException("help was requested");
        }
        return inputFilePath;
    }

    public String getOutputFilePath() {
        if (helpRequested) {
            throw new UnsupportedOperationException("help was requested");
        }
        return outputFilePath;
    }

    public FourthTaskAlgorithm getFourthAlgorithm() {
        if (helpRequested) {
            throw new UnsupportedOperationException("help was requested");
        }
        if (taskId != 4) {
            throw new NoSuchElementException("no data is available for not-4th task");
        }
        return fourthAlgorithm;
    }

    public Integer[] getFifthVertices() {
        if (helpRequested) {
            throw new UnsupportedOperationException("help was requested");
        }
        if (taskId != 5) {
            throw new NoSuchElementException("no data is available for not-5th task");
        }
        return fifthSrcDst;
    }

    public SixthTaskAlgorithm getSixthAlgorithm() {
        if (helpRequested) {
            throw new UnsupportedOperationException("help was requested");
        }
        if (taskId != 6) {
            throw new NoSuchElementException("no data is available for not-6th task");
        }
        return sixthAlgorithm;
    }

    public Integer getSixthStartVertex() {
        if (helpRequested) {
            throw new UnsupportedOperationException("help was requested");
        }
        if (taskId != 6) {
            throw new NoSuchElementException("no data is available for not-6th task");
        }
        return sixthStartVertex;
    }
}
