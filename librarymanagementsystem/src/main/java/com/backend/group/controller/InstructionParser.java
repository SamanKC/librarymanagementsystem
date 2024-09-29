package com.backend.group.controller;

import com.backend.group.file.FileManager;
import com.backend.group.handler.AddHandler;
import com.backend.group.handler.DeleteHandler;
import com.backend.group.handler.QueryHandler;
import com.backend.group.model.Library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InstructionParser {
    private AddHandler addHandler;
    private DeleteHandler deleteHandler;
    private QueryHandler queryHandler;
    private FileManager fileManager;
    private Library library;

    public InstructionParser(AddHandler addHandler, DeleteHandler deleteHandler, QueryHandler queryHandler,
            FileManager fileManager, Library library) {
        this.addHandler = addHandler;
        this.deleteHandler = deleteHandler;
        this.queryHandler = queryHandler;
        this.fileManager = fileManager;
        this.library = library; // Pass library to the instruction parser
    }

    // Parse instructions and execute appropriate handlers
    public void parseInstructions(String instructionFile) {
        String projectDir = System.getProperty("user.dir");
        instructionFile = projectDir
                + "/librarymanagementsystem/src/main/java/com/backend/group/output/instructions.txt";
        File file = new File(instructionFile);
        try {
            FileUtils util = new FileUtils(instructionFile);
            util.ensureFileAndDirectoryExists(file.getPath(), "instructions");
            System.out.println("Instruction file created: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error creating instruction file: " + e.getMessage());
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String instruction;
            while ((instruction = reader.readLine()) != null) {
                executeInstruction(instruction);
            }
        } catch (Exception e) {
            System.err.println("Error executing instructions: " + e.getMessage());

        }
    }

    private void executeInstruction(String instruction) {
        if (instruction.startsWith("add")) {
            addHandler.handleInstruction(instruction);
        } else if (instruction.startsWith("delete")) {
            deleteHandler.handleInstruction(instruction);
        } else if (instruction.startsWith("query")) {
            queryHandler.handleInstruction(instruction);
        } else if (instruction.equalsIgnoreCase("save")) {
            // Logic for saving library data can be implemented here
            fileManager.saveQueryResults(instruction, library.getBooks());
            System.out.println("Saving library data to output and query results to report...");
        } else {
            System.err.println("Invalid instruction: " + instruction);
        }
    }
}
