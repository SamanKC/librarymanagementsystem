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
                System.err.println("Error processing 'query' instruction: Invalid format.");
                return;
            }

            // Split the instruction
            String[] parts = instruction.split(" ");
            if (parts.length < 3) {
                System.err.println("Error processing 'query' instruction: Invalid format, not enough parts.");
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
            }
        } catch (Exception e) {
            System.err.println("Error processing 'query' instruction: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for better debugging
        }
    }

}
