package com.backend.group.model;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    // Adds or updates a book in the library
    public void addOrUpdateBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(book.getIsbn())) {
                books.set(i, book); // Update existing book
                return;
            }
        }
        books.add(book); // Add new book if it doesn't exist
    }

    // Deletes a book by its title
    public void deleteBookByTitle(String title) {
        boolean deleted = books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
        if (!deleted) {
            System.err.println("Warning: No book found with the title: " + title);
        }
    }

    // Deletes a book by its ISBN
    public void deleteBookByIsbn(String isbn) {
        boolean deleted = books.removeIf(book -> book.getIsbn().equals(isbn));
        if (!deleted) {
            System.err.println("Warning: No book found with the ISBN: " + isbn);
        }
    }

    // Queries books based on specified criteria
    public List<Book> queryBooks(String criteria, String value) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            switch (criteria.toLowerCase()) {
                case "title":
                    if (book.getTitle().equalsIgnoreCase(value)) {
                        result.add(book);
                    }
                    break;
                case "author":
                    if (book.getAuthor().equalsIgnoreCase(value)) {
                        result.add(book);
                    }
                    break;
                case "genre":
                    if (book.getGenre().equalsIgnoreCase(value)) {
                        result.add(book);
                    }
                    break;
                case "isbn":
                    if (book.getIsbn().equals(value)) {
                        result.add(book);
                    }
                    break;
                default:
                    System.err.println("Warning: Invalid query criteria - " + criteria);
                    return result; // Return an empty result list for invalid criteria
            }
        }
        return result;
    }

    // Returns the list of all books in the library
    public List<Book> getBooks() {
        return new ArrayList<>(books); // Return a copy to prevent external modification
    }
}
