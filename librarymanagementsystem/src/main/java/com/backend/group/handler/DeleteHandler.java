package com.backend.group.handler;

import com.backend.group.file.FileManager;
import com.backend.group.model.Library;

public class DeleteHandler extends InstructionHandler {

    public DeleteHandler(Library library, FileManager fileManager) {
        super(library, fileManager);
    }

    @Override
    public void handleInstruction(String instruction) {
        try {
            String[] parts = instruction.split(" ");
            if (parts[1].equalsIgnoreCase("title")) {
                String title = instruction.substring(instruction.indexOf(parts[2])).trim();
                library.deleteBookByTitle(title);
            } else if (parts[1].equalsIgnoreCase("isbn")) {
                String isbn = parts[2];
                library.deleteBookByIsbn(isbn);
            }
            fileManager.saveLibraryData(library.getBooks());
            System.out.println("Record deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error processing 'delete' instruction: " + e.getMessage());
        }
    }
}
