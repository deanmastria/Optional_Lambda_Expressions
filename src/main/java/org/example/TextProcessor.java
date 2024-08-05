package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TextProcessor {

    public static void main(String[] args) {
        // Input file containing the text to be processed
        String inputFilePath = "src/main/resources/Optional.txt";
        // Output file where sorted words will be written
        String outputFilePath = "src/main/resources/sorted_words.txt";

        // Create a File object for the output file
        File outputFile = new File(outputFilePath);
        // Get the directory where the output file will be created
        File outputDir = outputFile.getParentFile();

        // Check if the output directory exists, if not, create it
        if (!outputDir.exists()) {
            System.out.println("Output directory does not exist. Creating...");
            boolean dirCreated = outputDir.mkdirs();
            if (!dirCreated) {
                System.err.println("Failed to create output directory. Check permissions.");
                return;
            }
        } else if (!outputDir.canWrite()) {
            // Check if the program has write permission to the output directory
            System.err.println("Cannot write to the output directory. Check permissions.");
            return;
        }

        try {
            // Read all lines from the input file into a List of Strings
            List<String> lines = Files.readAllLines(Paths.get(inputFilePath));

            // Count the number of lines in the file
            long lineCount = processText(lines, linesList -> (long) linesList.size());
            System.out.println("Number of lines: " + lineCount);

            // Count the number of words in the file
            long wordCount = processText(lines, TextProcessor::countWords);
            System.out.println("Number of words: " + wordCount);

            // Find the longest word in the file
            String longestWord = processText(lines, TextProcessor::findLongestWord);
            System.out.println("Longest word: " + longestWord);

            // Sort the words in alphabetical order and store the results in a new file
            List<String> sortedWords = processText(lines, TextProcessor::sortWords);
            Files.write(Paths.get(outputFilePath), sortedWords);
            System.out.println("Alphabetically sorted words have been written to " + outputFilePath);

        } catch (IOException e) {
            // Handle any IOExceptions that occur during file reading/writing
            System.err.println("An error occurred during file operations: " + e.getMessage());
        }
    }

    // Processes text using a specified text operation
    public static <R> R processText(List<String> lines, TextOperations<R> operation) {
        return operation.apply(lines);
    }

    // Counts the number of words in the file
    public static long countWords(List<String> lines) {
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+"))) // Split lines into words using regex
                .count(); // Count the words
    }

    // Finds the longest word in the file
    public static String findLongestWord(List<String> lines) {
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+"))) // Split lines into words using regex
                .max(Comparator.comparingInt(String::length)) // Find the word with maximum length
                .orElse(""); // Return an empty string if no words are found
    }

    // Sorts the words in the file in alphabetical order
    public static List<String> sortWords(List<String> lines) {
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+"))) // Split lines into words using regex
                .sorted() // Sort the words in alphabetical order
                .collect(Collectors.toList()); // Collect the sorted words into a list
    }
}



