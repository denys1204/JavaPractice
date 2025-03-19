package org.example.utiles;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public static void createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) return;
        String message = "Error creating file.";
        try {
            file.createNewFile();
        } catch (IOException ignored) {}
        if (!file.exists()) {
            System.err.println(message);
            System.exit(1);
        }
    }
}
