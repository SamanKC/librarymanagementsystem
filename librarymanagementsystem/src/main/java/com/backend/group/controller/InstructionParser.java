// package com.backend.group.controller;

// import com.backend.group.handler.AddHandler;
// import com.backend.group.handler.DeleteHandler;
// import com.backend.group.handler.QueryHandler;

// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;

// public class InstructionParser {
//     private AddHandler addHandler;
//     private DeleteHandler deleteHandler;
//     private QueryHandler queryHandler;

//     public InstructionParser(AddHandler addHandler, DeleteHandler deleteHandler, QueryHandler queryHandler) {
//         this.addHandler = addHandler;
//         this.deleteHandler = deleteHandler;
//         this.queryHandler = queryHandler;
//     }

//     public void parseInstructions(String instructionFile) {
//         try (BufferedReader reader = new BufferedReader(
//                 new FileReader(instructionFile == "null" ? "../output/instructions.txt" : instructionFile))) {
//             String line;
//             while ((line = reader.readLine()) != null) {
//                 try {
//                     if (line.startsWith("add")) {
//                         addHandler.handleInstruction(line);
//                     } else if (line.startsWith("delete")) {
//                         deleteHandler.handleInstruction(line);
//                     } else if (line.startsWith("query")) {
//                         queryHandler.handleInstruction(line);
//                     }
//                 } catch (Exception e) {
//                     System.err.println("Error executing instruction: " + line + " | " + e.getMessage());
//                 }
//             }
//         } catch (IOException e) {
//             System.err.println("Error reading instructions file: " + e.getMessage());
//         }
//     }
// }
package com.backend.group.controller;

import java.io.*;
import com.backend.group.file.DefaultFileManager;
import com.backend.group.handler.AddHandler;
import com.backend.group.handler.DeleteHandler;
import com.backend.group.handler.QueryHandler;

public class InstructionParser {
    private AddHandler addHandler;
    private DeleteHandler deleteHandler;
    private QueryHandler queryHandler;

    public InstructionParser(AddHandler addHandler, DeleteHandler deleteHandler,
            QueryHandler queryHandler) {
        this.addHandler = addHandler;
        this.deleteHandler = deleteHandler;
        this.queryHandler = queryHandler;
    }

    public void parseInstructions(String instructionFile) {
        File file = new File(instructionFile == ""
                ? "/output/instructions.txt"
                : instructionFile);

        // Ensure file exists, if not, create it and inform the user
        if (!file.exists()) {
            try {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs(); // Ensure directories exist
                }
                file.createNewFile(); // Create the instructions file
                System.out.println(
                        "Instructions file not found. A new file has been created at: " + file.getAbsolutePath());
                return; // Exit since the newly created file is empty
            } catch (IOException e) {
                System.err.println("Error creating instructions file: " + e.getMessage());
                return;
            }
        }

        // Try reading and executing instructions from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Executing: " + line); // Log the current instruction being executed
                try {
                    processInstruction(line);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid command format: " + line + " | Error: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error processing command: " + line + " | Error: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Instructions file not found: " + instructionFile + " | Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading instructions file: " + e.getMessage());
        }
    }

    // Method to handle and process each instruction line
    private void processInstruction(String line) {
        if (line.startsWith("add")) {
            addHandler.handleInstruction(line);
        } else if (line.startsWith("delete")) {
            deleteHandler.handleInstruction(line);
        } else if (line.startsWith("query")) {
            queryHandler.handleInstruction(line);
        } else {
            throw new IllegalArgumentException("Unknown command: " + line);
        }
    }
}
