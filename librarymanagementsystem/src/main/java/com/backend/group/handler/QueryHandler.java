package com.backend.group.handler;

import com.backend.group.file.FileManager;
import com.backend.group.model.Book;
import com.backend.group.model.Library;

import java.util.List;

public class QueryHandler extends InstructionHandler {

    public QueryHandler(Library library, FileManager fileManager) {
        super(library, fileManager);
    }

    @Override
    public void handleInstruction(String instruction) {
        try {
            if (!instruction.startsWith("query")) {
                String errorMessage = "Error processing 'query' instruction: Invalid format.";
                System.err.println(errorMessage);
                fileManager.logMessage(errorMessage);
                return;
            }

            // Split the instruction
            String[] parts = instruction.split(" ");
            if (parts.length < 3) {
                String errorMessage = "Error processing 'query' instruction: Missing criteria and value.";
                System.err.println(errorMessage);
                fileManager.logMessage(errorMessage);
                return;
            }

            String criteria = parts[1].toLowerCase();
            // Check if the part index is valid
            if (parts.length < 3) {
                System.err.println("Error processing 'query' instruction: Missing value.");
                return;
            }
            String value = instruction.substring(instruction.indexOf(parts[2])).trim();

            // Debugging log
            System.out.println("Query criteria: " + criteria + ", value: " + value);

            // Perform the query
            List<Book> results = library.queryBooks(criteria, value);

            // Save results to the report file
            fileManager.saveQueryResults(instruction, results);

            if (results.isEmpty()) {
                System.err.println("Warning: No books found for query: " + criteria + " = " + value);
                fileManager.logMessage("Warning: No books found for query: " + criteria + " = " + value);
            }
        } catch (Exception e) {
            System.err.println("Error processing 'query' instruction: " + e.getMessage());
        }
    }

}
