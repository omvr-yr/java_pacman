package main.java.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileIntegerOperations {

    public static void main(String[] args) {
        String filePath = "your_file.txt"; 
        subtractValueFromFile(filePath, 1000);
    }

    public static void subtractValueFromFile(String filePath, int valueToSubtract) {
        try {
            int currentValue = readIntegerFromFile(filePath);

            int newValue = currentValue - valueToSubtract;

            writeIntegerToFile(filePath, newValue);

            System.out.println("Operation successful. The new value is: " + newValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int readIntegerFromFile(String filePath) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                throw new IllegalArgumentException("le fichier ne contient pas de int valide.");
            }
        }
    }

    private static void writeIntegerToFile(String filePath, int value) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(Integer.toString(value));
        }
    }
}

