package com.backend.group.file;

import com.backend.group.model.Book;
import com.backend.group.model.Library;

import java.io.*;
import java.util.List;

public class DefaultFileManager extends FileManager {

    public DefaultFileManager(String bookFile, String outputFile, String reportFile) {
        super(bookFile, outputFile, reportFile);
    }

    @Override
    public void loadInitialData(Library library) {
        File file = new File(bookFile);

        try {
            // Ensure the file exists (create if necessary)
            ensureFileExists(file);

            // Now load the initial data from the file
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                String title = null, author = null, isbn = null, genre = null;
                int year = 0;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        if (title != null) {
                            Book book = new Book(title, author, isbn, genre, year);
                            library.addOrUpdateBook(book);
                            title = author = isbn = genre = null;
                            year = 0;
                        }
                        continue;
                    }

                    if (line.startsWith("Title: ")) {
                        title = line.split(": ")[1].trim();
                    } else if (line.startsWith("Author: ")) {
                        author = line.split(": ")[1].trim();
                    } else if (line.startsWith("ISBN: ")) {
                        isbn = line.split(": ")[1].trim();
                    } else if (line.startsWith("Genre: ")) {
                        genre = line.split(": ")[1].trim();
                    } else if (line.startsWith("Year: ")) {
                        try {
                            year = Integer.parseInt(line.split(": ")[1].trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing year for title '" + title + "': " + e.getMessage());
                        }
                    }
                }

                // Add the last book (if any) to the library
                if (title != null) {
                    Book book = new Book(title, author, isbn, genre, year);
                    library.addOrUpdateBook(book);
                }

            } catch (IOException e) {
                System.err.println("Error reading from book file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error ensuring file exists: " + e.getMessage());
        }
    }

    @Override
    public void saveLibraryData(List<Book> books) {
        File file = new File(outputFile);

        try {
            // Ensure the file exists (create if necessary)
            ensureFileExists(file);

            // Write library data to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Book book : books) {
                    writer.write(book.toString());
                    writer.newLine();
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing to output file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error ensuring file exists: " + e.getMessage());
        }
    }

    @Override
    public void saveQueryResults(String instruction, List<Book> results) {
        File file = new File(reportFile);

        try {
            // Ensure the file exists (create if necessary)
            ensureFileExists(file);

            // Write query results to the report file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write("Query: " + instruction);
                writer.newLine();
                writer.write("---------------------------------");
                writer.newLine();
                for (Book book : results) {
                    writer.write(book.toString());
                    writer.newLine();
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing to report file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error ensuring file exists: " + e.getMessage());
        }
    }
}
