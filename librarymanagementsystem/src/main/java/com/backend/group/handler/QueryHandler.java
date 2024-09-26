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
            String[] parts = instruction.split(" ");
            String criteria = parts[1];
            String value = instruction.substring(instruction.indexOf(parts[2])).trim();

            List<Book> results = library.queryBooks(criteria, value);
            fileManager.saveQueryResults(instruction, results);
            System.out.println("Query results saved successfully.");
        } catch (Exception e) {
            System.err.println("Error processing 'query' instruction: " + e.getMessage());
        }
    }
}
