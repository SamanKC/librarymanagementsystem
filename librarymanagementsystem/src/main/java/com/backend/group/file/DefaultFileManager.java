package com.backend.group.file;

import com.backend.group.model.Book;
import com.backend.group.model.Library;

import java.io.*;

public class DefaultFileManager extends FileManager {

    public DefaultFileManager(String bookFile) {
        super(bookFile);
    }

    @Override
    public void loadInitialData(Library library) {
        File file = new File(bookFile);

        try {
            util.ensureFileAndDirectoryExists(bookFile, "books");
        } catch (IOException e) {
            System.err.println("Error creating book file: " + e.getMessage());
        }
        // Now load the initial data from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String title = null, author = null, isbn = null, genre = null;
            int year = 0;

            System.out.println("Reading books: ");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.trim().isEmpty()) {
                    // If we encounter an empty line, create a book if we have a title
                    if (title != null) {
                        Book book = new Book(title, author, isbn, genre, year);
                        library.addOrUpdateBook(book);
                        // Print the book details to the terminal
                        System.out.println("Loaded Book:");
                        System.out.println(book);
                        System.out.println(); // Add a blank line for better readability
                        // Reset for next book
                        title = author = isbn = genre = null;
                        year = 0;
                    }
                    continue; // Skip the empty line
                }

                // Split based on ';'
                String[] parts = line.split(";");
                for (String part : parts) {
                    part = part.trim();
                    if (part.startsWith("title ")) {
                        title = part.substring("title ".length()).trim();
                    } else if (part.startsWith("author ")) {
                        author = part.substring("author ".length()).trim();
                    } else if (part.startsWith("ISBN ")) {
                        isbn = part.substring("ISBN ".length()).trim();
                    } else if (part.startsWith("genre ")) {
                        genre = part.substring("genre ".length()).trim();
                    } else if (part.startsWith("year ")) {
                        try {
                            year = Integer.parseInt(part.substring("year ".length()).trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing year for title '" + title + "': " + e.getMessage());
                        }
                    }
                }
            }

            // Handle the last book in the file if not already added
            if (title != null) {
                Book book = new Book(title, author, isbn, genre, year);
                library.addOrUpdateBook(book);
                // Print the last book details to the terminal
                System.out.println("Loaded Book:");
                System.out.println(book);
                System.out.println(); // Add a blank line for better readability
            }

        } catch (IOException e) {
            System.err.println("Error reading from book file: " + e.getMessage());
        }

    }

}
