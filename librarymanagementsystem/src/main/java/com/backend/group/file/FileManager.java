package com.backend.group.file;

import com.backend.group.controller.FileUtils;
import com.backend.group.model.Book;
import com.backend.group.model.Library;

import java.io.*;
import java.util.List;

public abstract class FileManager {
    protected String bookFile;
    protected String outputFile;
    protected String reportFile;
    protected String instructionFile;
    FileUtils util = new FileUtils(bookFile);

    public FileManager(String bookFile) {
        String projectDir = System.getProperty("user.dir");
        this.bookFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/" + bookFile;
        this.reportFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/report.txt";
        this.outputFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/output.txt";
    }

    public void logMessage(String message) {
        // Print to console
        System.out.println(message);

        // Write to output file
        File file = new File(outputFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // Append to the output file
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing log to output file: " + e.getMessage());
        }
    }

    public abstract void loadInitialData(Library library);

    // Update this part in FileManager class
    public void saveLibraryData(List<Book> books) {
        // Save the library data to output.txt
        File file = new File(bookFile);

        try {
            util.ensureFileAndDirectoryExists(file.getPath(), "books");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                for (Book book : books) {
                    writer.write("Title: " + book.getTitle());
                    writer.newLine();
                    writer.write("Author: " + book.getAuthor());
                    writer.newLine();
                    writer.write("ISBN: " + book.getIsbn());
                    writer.newLine();
                    writer.write("Genre: " + book.getGenre());
                    writer.newLine();
                    writer.write("Year: " + book.getYear());
                    writer.newLine();
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing to output file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error ensuring output file exists: " + e.getMessage());
        }
    }

    public void saveQueryResults(String instruction, List<Book> results) {
        File file = new File(reportFile);

        // Try-with-resources to ensure the writer is closed automatically
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Create the report file if it doesn't exist
            if (file.createNewFile()) {
                System.out.println("Report file created: " + reportFile);
            }

            // Write a separator for clarity
            writer.write("---------------------------------");
            writer.newLine();

            // Write the query instruction
            writer.write("Query: " + instruction);
            writer.newLine();
            writer.write("---------------------------------");
            writer.newLine();

            // Write the results
            if (results.isEmpty()) {
                writer.write("No books found for this query.");
                writer.newLine();
            } else {
                for (Book book : results) {
                    writer.write(book.toString());
                    writer.newLine();
                    writer.newLine(); // Extra line for spacing
                }
            }

            // Final separator
            writer.write("---------------------------------");
            writer.newLine();

            System.out.println("Query results successfully appended to " + reportFile);
        } catch (IOException e) {
            System.err.println("Error writing to report file: " + e.getMessage());
        }
    }

}
