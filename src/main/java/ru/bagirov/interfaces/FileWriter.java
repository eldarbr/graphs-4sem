package ru.bagirov.interfaces;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {
    public static void WriteToFile(String filepath, String data) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filepath);
        byte[] strToBytes = data.getBytes();
        outputStream.write(strToBytes);

        outputStream.close();
    }
}
