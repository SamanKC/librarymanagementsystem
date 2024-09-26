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

        // Prompt user for file names
        System.out.print("Enter the name of the initial book records file (e.g., books.txt): ");
        String bookFile = scanner.nextLine();

        System.out.print("Enter the name of the instructions file (e.g., instructions.txt): ");
        String instructionFile = scanner.nextLine();

        System.out.print("Enter the name for the output file (e.g., output.txt): ");
        String outputFile = scanner.nextLine();

        System.out.print("Enter the name for the report file (e.g., report.txt): ");
        String reportFile = scanner.nextLine();

        Library library = new Library();
        DefaultFileManager fileManager = new DefaultFileManager(bookFile, outputFile, reportFile);

        // Handlers
        AddHandler addHandler = new AddHandler(library, fileManager);
        DeleteHandler deleteHandler = new DeleteHandler(library, fileManager);
        QueryHandler queryHandler = new QueryHandler(library, fileManager);

        InstructionParser instructionParser = new InstructionParser(addHandler, deleteHandler, queryHandler);

        // Load initial data
        fileManager.loadInitialData(library);

        // Parse and execute instructions
        instructionParser.parseInstructions(instructionFile);

        scanner.close();
    }
}

