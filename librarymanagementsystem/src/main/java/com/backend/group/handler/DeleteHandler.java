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
            if (parts.length < 3) {
                System.err.println("Error processing 'delete' instruction: Missing required arguments.");
                return;
            }

            String actionType = parts[1].toLowerCase();
            String identifier = instruction.substring(instruction.indexOf(parts[2])).trim();

            switch (actionType) {
                case "title":
                    library.deleteBookByTitle(identifier);
                    break;
                case "isbn":
                    library.deleteBookByIsbn(parts[2]);
                    break;
                default:
                    System.err.println("Error processing 'delete' instruction: Invalid field specified.");
                    return;
            }

            fileManager.saveLibraryData(library.getBooks());
            System.out.println("Record deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error processing 'delete' instruction: " + e.getMessage());
        }
    }
}
