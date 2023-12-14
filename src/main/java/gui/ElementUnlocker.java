package main.java.gui;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ElementUnlocker {

    private static final String UNLOCKED_ELEMENTS_FILE = "unlocked_elements.txt";

    public static void unlockElement(String elementId) {
        Set<String> unlockedElements = readUnlockedElements();
        unlockedElements.add(elementId);
        writeUnlockedElements(unlockedElements);
    }

    public static boolean isElementUnlocked(String elementId) {
        Set<String> unlockedElements = readUnlockedElements();
        return unlockedElements.contains(elementId);
    }

    private static Set<String> readUnlockedElements() {
        Set<String> unlockedElements = new HashSet<>();
        try (Scanner scanner = new Scanner(new File(UNLOCKED_ELEMENTS_FILE))) {
            while (scanner.hasNextLine()) {
                unlockedElements.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
        }
        return unlockedElements;
    }

    private static void writeUnlockedElements(Set<String> unlockedElements) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(UNLOCKED_ELEMENTS_FILE))) {
            for (String elementId : unlockedElements) {
                writer.println(elementId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
