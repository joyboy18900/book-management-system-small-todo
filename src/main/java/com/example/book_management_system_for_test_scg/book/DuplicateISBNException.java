package com.example.book_management_system_for_test_scg.book;

public class DuplicateISBNException extends RuntimeException {
    public DuplicateISBNException(String message) {
        super(message);
    }
}