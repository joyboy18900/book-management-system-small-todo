package com.example.book_management_system_for_test_scg.book;

import com.example.book_management_system_for_test_scg.errors.MyError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookControllerAdvice {

    @ExceptionHandler(DuplicateISBNException.class)
    public ResponseEntity<MyError> duplicateISBN(DuplicateISBNException e) {
        MyError error = new MyError();
        error.setCode("24000");
        error.setDescription("Book with the same ISBN already exists.");
        error.setStatus(false);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<MyError> bookNotFoundHandler(BookNotFoundException e) {
        MyError error = new MyError();
        error.setCode("25000");
        error.setDescription("Book not found");
        error.setStatus(false);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}