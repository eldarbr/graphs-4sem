package ru.bagirov.problem.shared;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class GraphReaderTest {
    GraphReader graphReader;

    @Test
    public void unsupportedSourceType() {
        graphReader = new GraphReader("", GraphSourceType.UNSUPPORTED);
        assertThrows(RuntimeException.class, () -> {graphReader.readData();});
    }

    @Test
    public void listOfAdjacencyNonexistentFile() {
        graphReader = new GraphReader("nonexistent file path", GraphSourceType.ListOfAdjacency);
        assertThrows(FileNotFoundException.class, () -> {graphReader.readData();});
    }

    @Test
    public void listOfAdjacencyExistingFileWrongContent() {

        final String anyFile = Objects.requireNonNull(new File(System.getenv("temp")).listFiles())[0].getPath();

        graphReader = new GraphReader(anyFile, GraphSourceType.ListOfAdjacency);
        assertThrows(IllegalArgumentException.class, () -> {graphReader.readData();});
    }

    @Test
    public void listOfAdjacencyLegitFileShort() {

        String data = "2 3\n3\n1 3";
        int[][] expected = {
                {0,1,1},
                {0,0,1},
                {1,0,1}
        };

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_listOfAdjacency_legitFileShortTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.ListOfAdjacency);

        try{
            int[][] actual = graphReader.readData();
            assertThat(actual).isEqualTo(expected);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't read the file");
        }
    }

    @Test
    public void listOfAdjacencyLegitFileLotOfEmptyLines() {

        String data = "1 2\n\n\n\n\n\n\n\n\n\n\n1";
        int[][] expected = {
                {1,1,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {1,0,0,0,0,0,0,0,0,0,0,0}
        };

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_listOfAdjacency_legitFileLotOfEmptyLinesTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.ListOfAdjacency);

        try{
            int[][] actual = graphReader.readData();
            assertThat(actual).isEqualTo(expected);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't read the file");
        }
    }


    @Test
    public void listOfEdgesNonexistentFile() {
        graphReader = new GraphReader("nonexistent file path", GraphSourceType.ListOfEdges);
        assertThrows(FileNotFoundException.class, () -> {graphReader.readData();});
    }

    @Test
    public void listOfEdgesExistingFileWrongContent() {

        final String anyFile = Objects.requireNonNull(new File(System.getenv("temp")).listFiles())[0].getPath();

        graphReader = new GraphReader(anyFile, GraphSourceType.ListOfEdges);
        assertThrows(IllegalArgumentException.class, () -> {graphReader.readData();});
    }

    @Test
    public void listOfEdgesLegitFileWeightedShort() {

        String data = "1 2 10\n1 1 1\n2 1 3\n2 3 4\n 3 3 2";
        int[][] expected = {
                {1,10,0},
                {3,0,4},
                {0,0,2}
        };

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_listOfEdges_legitFileWeightedShortTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.ListOfEdges);

        try{
            int[][] actual = graphReader.readData();
            assertThat(actual).isEqualTo(expected);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't read the file");
        }
    }

    @Test
    public void listOfEdgesLegitFileWeightedLong() {

        String data = "2 3 10\n2 9 11\n2 10 12\n2 5 13\n2 1 14\n3 2 15\n4 3 16\n5 7 17\n5 3 18\n5 9 19\n6 10 10\n9 8 11\n7 5 12\n7 7 13\n7 4 14\n7 9 15\n8 6 16\n8 1 17\n10 6 18\n10 1 19";
        int[][] expected = {
                {0,0,0,0,0,0,0,0,0,0},
                {14,0,10,0,13,0,0,0,11,12},
                {0,15,0,0,0,0,0,0,0,0},
                {0,0,16,0,0,0,0,0,0,0},
                {0,0,18,0,0,0,17,0,19,0},
                {0,0,0,0,0,0,0,0,0,10},
                {0,0,0,14,12,0,13,0,15,0},
                {17,0,0,0,0,16,0,0,0,0},
                {0,0,0,0,0,0,0,11,0,0},
                {19,0,0,0,0,18,0,0,0,0},
        };

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_listOfEdges_legitFileWeightedLongTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.ListOfEdges);

        try{
            int[][] actual = graphReader.readData();
            assertThat(actual).isEqualTo(expected);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't read the file");
        }
    }

    @Test
    public void listOfEdgesLegitFileNotWeightedShort() {

        String data = "1 2\n1 1\n2 1\n2 3\n 3 3";
        int[][] expected = {
                {1,1,0},
                {1,0,1},
                {0,0,1}
        };

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_listOfEdges_legitFileNotWeightedShortTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.ListOfEdges);

        try{
            int[][] actual = graphReader.readData();
            assertThat(actual).isEqualTo(expected);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't read the file");
        }
    }

    @Test
    public void listOfEdgesLegitFileNotWeightedLong() {

        String data = "2 3\n2 9\n2 10\n2 5\n2 1\n3 2\n4 3\n5 7\n5 3\n5 9\n6 10\n9 8\n7 5\n7 7\n7 4\n7 9\n8 6\n8 1\n10 6\n10 1";
        int[][] expected = {
                {0,0,0,0,0,0,0,0,0,0},
                {1,0,1,0,1,0,0,0,1,1},
                {0,1,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,1,0,1,0},
                {0,0,0,0,0,0,0,0,0,1},
                {0,0,0,1,1,0,1,0,1,0},
                {1,0,0,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,0,1,0,0},
                {1,0,0,0,0,1,0,0,0,0},
        };

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_listOfEdges_legitFileNotWeightedLongTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.ListOfEdges);

        try{
            int[][] actual = graphReader.readData();
            assertThat(actual).isEqualTo(expected);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't read the file");
        }
    }

    @Test
    public void listOfEdgesEdgeComesTwice() {

        String data = "1 2\n1 2\n2 1\n2 3\n 3 3";


        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_listOfEdges_edgeComesTwiceTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.ListOfEdges);

        assertThrows(IllegalArgumentException.class, () -> {graphReader.readData();});
    }

    @Test
    public void matrixEmpty() {

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_matrix_emptyTest_", ".txt");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.AdjacencyMatrix);

        assertThrows(IllegalArgumentException.class, () -> {graphReader.readData();});
    }

    @Test
    public void matrixLegitShort() {

        String data = "0 10\n1 0";

        int[][] expected = {
                {0,10},
                {1,0},
        };

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_matrix_legitShortTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.AdjacencyMatrix);

        try{
            int[][] actual = graphReader.readData();
            assertThat(actual).isEqualTo(expected);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't read the file");
        }
    }

    @Test
    public void matrixLegitLong() {

        String data = "0 0 0 0 0\n1 0 1 0 1\n0 1 0 0 0\n0 0 1 0 0\n0 0 1 0 0";
        int[][] expected = {
                {0,0,0,0,0},
                {1,0,1,0,1},
                {0,1,0,0,0},
                {0,0,1,0,0},
                {0,0,1,0,0},
        };

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_matrix_legitLongTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }


        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.AdjacencyMatrix);

        try{
            int[][] actual = graphReader.readData();
            assertThat(actual).isEqualTo(expected);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't read the file");
        }
    }

    @Test
    public void matrixNotSquareLineIsLonger() {

        String data = "0 10\n1 0 1";

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_matrix_notSquareNotFirstTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }

        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.AdjacencyMatrix);

        assertThrows(IllegalArgumentException.class, () -> {graphReader.readData();});
    }

    @Test
    public void matrixNotSquareColumnIsLonger() {

        String data = "0\n1";

        File tempFile;
        try {
            tempFile = File.createTempFile("GraphReaderTest_matrix_notSquareFirstTest_", ".txt");
            Files.write(tempFile.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write the file");
        }

        graphReader = new GraphReader(tempFile.getPath(), GraphSourceType.AdjacencyMatrix);

        assertThrows(IllegalArgumentException.class, () -> {graphReader.readData();});
    }

}
