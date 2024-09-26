package com.backend.group.file;

import com.backend.group.model.Book;
import com.backend.group.model.Library;

import java.io.*;
import java.util.List;

// Abstract class defining common methods for file operations
public abstract class FileManager {
    protected String bookFile;
    protected String outputFile;
    protected String reportFile;

    public FileManager(String bookFile, String outputFile, String reportFile) {
        this.bookFile = (bookFile != null && !bookFile.isEmpty()) ? bookFile : "/librarymanagementsystem/src/main/java/com/backend/group/output/output/books.txt"; // Default book file
        this.outputFile = (outputFile != null && !outputFile.isEmpty()) ? outputFile : "/librarymanagementsystem/src/main/java/com/backend/group/output/output.txt"; 
        this.reportFile = (reportFile != null && !reportFile.isEmpty()) ? reportFile : "/librarymanagementsystem/src/main/java/com/backend/group/output/report.txt"; 
    }

    // Abstract methods to be implemented by child classes
    public abstract void loadInitialData(Library library);

    public abstract void saveLibraryData(List<Book> books);

    public abstract void saveQueryResults(String instruction, List<Book> results);

    // Utility method to ensure the existence of a file, creating it if necessary
    protected void ensureFileExists(File file) throws IOException {
        if (!file.exists()) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs(); // Ensure directories exist
            }
            file.createNewFile();
            System.out.println("File not found, created new file: " + file.getAbsolutePath());
        }
    }
}
