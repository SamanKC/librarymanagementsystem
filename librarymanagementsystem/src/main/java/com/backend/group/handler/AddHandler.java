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
            String[] parts = instruction.split(";");
            String title = parts[0].split(" ")[2].trim();
            String author = parts[1].split(" ")[2].trim();
            String isbn = parts[2].split(" ")[2].trim();
            String genre = parts[3].split(" ")[2].trim();
            int year = Integer.parseInt(parts[4].split(" ")[2].trim());

            Book book = new Book(title, author, isbn, genre, year);
            library.addOrUpdateBook(book);
            fileManager.saveLibraryData(library.getBooks());
            System.out.println("Record added/updated successfully.");
        } catch (Exception e) {
            System.err.println("Error processing 'add' instruction: " + e.getMessage());
        }
    }
}
