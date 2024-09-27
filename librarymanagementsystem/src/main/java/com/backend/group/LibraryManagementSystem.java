package com.backend.group;

import com.backend.group.controller.InstructionParser;
import com.backend.group.file.DefaultFileManager;
import com.backend.group.handler.AddHandler;
import com.backend.group.handler.DeleteHandler;
import com.backend.group.handler.QueryHandler;
import com.backend.group.model.Library;

import java.util.Scanner;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the book and instruction filenames from the user
        String bookFile = getFileName(scanner, "initial book records file", "books");
        String instructionFile = getFileName(scanner, "instructions file", "instructions");

        Library library = new Library();
        
        DefaultFileManager fileManager = new DefaultFileManager(bookFile);

        // Initialize the handlers for different operations
        AddHandler addHandler = new AddHandler(library, fileManager);
        DeleteHandler deleteHandler = new DeleteHandler(library, fileManager);
        QueryHandler queryHandler = new QueryHandler(library, fileManager);

        // Set up the instruction parser with the handlers
        InstructionParser instructionParser = new InstructionParser(addHandler, deleteHandler, queryHandler);

        // Load initial data from the specified file
        fileManager.loadInitialData(library);

        // Parse and execute instructions from the instruction file
        instructionParser.parseInstructions(instructionFile);

        scanner.close();
    }

    // Method to prompt for file name input and return the full file name with .txt
    // extension
    // If no input is given, use the default name provided
    private static String getFileName(Scanner scanner, String prompt, String defaultName) {
        System.out.print("Enter the name of the " + prompt + " (without .txt extension, e.g., books) [default: "
                + defaultName + "]: ");

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultName + ".txt"; // Use default name if input is empty
        }

        return input + ".txt"; // Append .txt extension
    }
}
