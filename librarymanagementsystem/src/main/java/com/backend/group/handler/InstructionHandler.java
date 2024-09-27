package com.backend.group.handler;

import com.backend.group.file.FileManager;
import com.backend.group.model.Library;

public abstract class InstructionHandler {
    protected Library library; // Library instance for managing books
    protected FileManager fileManager; // FileManager instance for file operations

    // Constructor to initialize library and file manager
    public InstructionHandler(Library library, FileManager fileManager) {
        this.library = library;
        this.fileManager = fileManager;
    }

    // Abstract method to handle instructions, to be implemented by subclasses
    public abstract void handleInstruction(String instruction) throws Exception;
}
