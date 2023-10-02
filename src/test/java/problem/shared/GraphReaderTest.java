package problem.shared;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
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

        String data = "1 2\n2\n0 2";
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

        String data = "0 1\n\n\n\n\n\n\n\n\n\n\n0";
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

        String data = "0 1 10\n0 0 1\n1 0 3\n1 2 4\n 2 2 2";
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

        String data = "1 2 10\n1 8 11\n1 9 12\n1 4 13\n1 0 14\n2 1 15\n3 2 16\n4 6 17\n4 2 18\n4 8 19\n5 9 10\n8 7 11\n6 4 12\n6 6 13\n6 3 14\n6 8 15\n7 5 16\n7 0 17\n9 5 18\n9 0 19";
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

        String data = "0 1\n0 0\n1 0\n1 2\n 2 2";
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

        String data = "1 2\n1 8\n1 9\n1 4\n1 0\n2 1\n3 2\n4 6\n4 2\n4 8\n5 9\n8 7\n6 4\n6 6\n6 3\n6 8\n7 5\n7 0\n9 5\n9 0";
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

        String data = "0 1\n0 1\n1 0\n1 2\n 2 2";


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
