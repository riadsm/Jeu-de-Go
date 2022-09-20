package Util;

import Model.Pawn;

import java.io.*;
import java.util.ArrayList;

public class FileIO {

    public static String getFileContent(File file) throws IOException {
        ArrayList<Pawn> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null)
            sb.append(line);

        reader.close();
        return sb.toString();
    }

    public static void saveToFile(File file, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(text);
        writer.close();
    }

}
