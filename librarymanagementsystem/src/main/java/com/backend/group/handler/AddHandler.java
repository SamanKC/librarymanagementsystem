package com.backend.group.handler;

import com.backend.group.file.FileManager;
import com.backend.group.model.Book;
import com.backend.group.model.Library;

public class AddHandler extends InstructionHandler {

    public AddHandler(Library library, FileManager fileManager) {
        super(library, fileManager);
    }

    @Override
    public void handleInstruction(String instruction) {
        try {
           

            // Log instruction for debugging
            System.out.println("Processing add instruction: " + instruction);

            // Extract the parts based on ';'
            String[] parts = instruction.substring(4).split(";");
            System.out.println("Parts extracted: " + java.util.Arrays.toString(parts));

            if (parts.length != 5) {
                System.err.println("Error processing 'add' instruction: Missing or extra fields.");
                return;
            }

            // Use the updated method to extract fields
            String title = extractField(parts[0], "title");
            String author = extractField(parts[1], "author");
            String isbn = extractField(parts[2], "ISBN");
            String genre = extractField(parts[3], "genre");
            int year = Integer.parseInt(extractField(parts[4], "year"));

            // Log extracted values
            System.out.println("Extracted Values - Title: " + title + ", Author: " + author + ", ISBN: " + isbn
                    + ", Genre: " + genre + ", Year: " + year);

            // Create a new book object
            Book book = new Book(title, author, isbn, genre, year);
            library.addOrUpdateBook(book);
            // Save the updated library data
          fileManager.saveLibraryData(library.getBooks());

            System.out.println("Record added/updated successfully.");
            fileManager.logMessage("Record added/updated successfully");
        } catch (Exception e) {
            System.err.println("Error processing 'add' instruction: " + e.getMessage());
        }
    }

    private String extractField(String part, String fieldName) {
        // Split based on the first occurrence of a space
        String[] keyValue = part.trim().split(" ", 2);
        if (keyValue.length != 2 || !keyValue[0].equalsIgnoreCase(fieldName)) {
            System.err.println("Invalid or missing " + fieldName + " field in part: '" + part + "'");
            throw new IllegalArgumentException("Invalid or missing " + fieldName + " field.");
        }
        return keyValue[1].trim();
    }

}
