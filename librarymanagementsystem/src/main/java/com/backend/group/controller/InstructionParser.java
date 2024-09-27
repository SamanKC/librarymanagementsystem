package com.backend.group.controller;

import com.backend.group.handler.AddHandler;
import com.backend.group.handler.DeleteHandler;
import com.backend.group.handler.QueryHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InstructionParser {
    private  AddHandler addHandler;
    private  DeleteHandler deleteHandler;
    private  QueryHandler queryHandler;

    public InstructionParser(AddHandler addHandler, DeleteHandler deleteHandler, QueryHandler queryHandler) {
        this.addHandler = addHandler;
        this.deleteHandler = deleteHandler;
        this.queryHandler = queryHandler;
    }

    // Parse instructions and execute appropriate handlers
    public void parseInstructions(String instructionFile) {
        String instructionFilePath = getInstructionFilePath(instructionFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(instructionFilePath))) {
            String instruction;
            while ((instruction = reader.readLine()) != null) {
                executeInstruction(instruction);
            }
        } catch (IOException e) {
            System.err.println("Error reading instructions file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error executing instructions: " + e.getMessage());
        }
    }

    private String getInstructionFilePath(String instructionFile) {
        String projectDir = System.getProperty("user.dir");
        String defaultPath = projectDir
                + "/librarymanagementsystem/src/main/java/com/backend/group/output/instructions.txt";

        if (instructionFile != null && !instructionFile.trim().isEmpty()) {
            return projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/" + instructionFile;
        } else {
            return ensureFileAndDirectoryExists(defaultPath);
        }
    }

    private String ensureFileAndDirectoryExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                // Create parent directories if they do not exist
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("Created new instruction file at: " + filePath);
            } catch (IOException e) {
                System.err.println("Failed to create instruction file: " + e.getMessage());
            }
        }
        return filePath;
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
            System.out.println("Saving library data to output and query results to report...");
        } else {
            System.err.println("Invalid instruction: " + instruction);
        }
    }
}
