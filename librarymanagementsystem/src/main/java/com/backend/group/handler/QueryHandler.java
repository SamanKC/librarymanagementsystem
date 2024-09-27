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

            // Split the instruction
            String[] parts = instruction.split(" ");
            if (parts.length < 3) {
                System.err.println("Error processing 'query' instruction: Invalid format.");
                return;
            }

            String criteria = parts[1].toLowerCase();
            String value = instruction.substring(instruction.indexOf(parts[2])).trim();

            System.out.println("Processing query: Criteria = " + criteria + ", Value = " + value); 

            List<Book> results = library.queryBooks(criteria, value);
            if (results.isEmpty()) {
                System.err.println("Warning: No books found for query: " + criteria + " = " + value);
            } else {
                System.out.println("Books found: " + results.size()); 
                for (Book book : results) {
                    System.out.println("Found Book: " + book); // Print each found book
                }
            }

            // fileManager.saveQueryResults(instruction, results);
            System.out.println("Query results saved successfully.");
        } catch (Exception e) {
            System.err.println("Error processing 'query' instruction: " + e.getMessage());
        }
    }

}
