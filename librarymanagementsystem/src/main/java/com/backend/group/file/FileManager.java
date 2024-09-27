package com.backend.group.file;

import com.backend.group.model.Book;
import com.backend.group.model.Library;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public abstract class FileManager {
    protected String bookFile;
    protected String outputFile;
    protected String reportFile;
    protected String instructionFile;

    public FileManager(String bookFile) {
        String projectDir = System.getProperty("user.dir");
        this.bookFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/" + bookFile;
        this.reportFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/report.txt";
        this.outputFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/output.txt";
    }

    protected void ensureFileAndDirectoryExists(String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        // Ensure parent directory exists
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("Output directory created: " + parentDir.getAbsolutePath());
        }

        // Ensure file exists, prompting for a new name if not
        if (!file.exists()) {
            file = createNewFile(parentDir);
        }
    }

    private File createNewFile(File parentDir) throws IOException {
        System.out.print("File not found. Please enter a name for the file to create (without extension): ");
        Scanner scanner = new Scanner(System.in);
        String newFileName = scanner.nextLine().trim();

        File newFile = new File(parentDir, newFileName + ".txt");
        newFile.createNewFile();
        System.out.println("File created: " + newFile.getAbsolutePath());
        return newFile;
    }

    public abstract void loadInitialData(Library library);

    // Update this part in FileManager class
    public void saveLibraryData(List<Book> books) {
        // Save the library data to output.txt
        File file = new File(outputFile);

        try {
            ensureFileAndDirectoryExists(file.getPath());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
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

    // private void writeBooksToFile(List<Book> books, String filePath) {
    // try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
    // for (Book book : books) {
    // writer.write("Title: " + book.getTitle());
    // writer.newLine();
    // writer.write("Author: " + book.getAuthor());
    // writer.newLine();
    // writer.write("ISBN: " + book.getIsbn());
    // writer.newLine();
    // writer.write("Genre: " + book.getGenre());
    // writer.newLine();
    // writer.write("Year: " + book.getYear());
    // writer.newLine();
    // writer.newLine();
    // }
    // } catch (IOException e) {
    // System.err.println("Error writing to book file: " + e.getMessage());
    // }
    // }

    // public void saveQueryResults(String instruction, List<Book> results) {
    // try {
    // ensureFileAndDirectoryExists(reportFile);
    // appendQueryResultsToFile(instruction, results, reportFile);
    // } catch (IOException e) {
    // System.err.println("Error ensuring report file exists: " + e.getMessage());
    // }
    // }

    // private void appendQueryResultsToFile(String instruction, List<Book> results,
    // String filePath) {
    // try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,
    // true))) { // Append to the file
    // writer.write("Query: " + instruction);
    // writer.newLine();
    // writer.write("---------------------------------");
    // writer.newLine();
    // for (Book book : results) {
    // writer.write(book.toString());
    // writer.newLine();
    // writer.newLine();
    // }
    // writer.write("---------------------------------");
    // writer.newLine();
    // } catch (IOException e) {
    // System.err.println("Error writing to report file: " + e.getMessage());
    // }
    // }
}
