package com.backend.group.handler;

import com.backend.group.file.FileManager;
import com.backend.group.model.Library;

public abstract class InstructionHandler {
    protected Library library;
    protected FileManager fileManager;
    

    public InstructionHandler(Library library, FileManager fileManager) {
        this.library = library;
        this.fileManager = fileManager;
    }

    public abstract void handleInstruction(String instruction) throws Exception;
}
