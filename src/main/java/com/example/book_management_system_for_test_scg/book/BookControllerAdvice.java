package com.example.book_management_system_for_test_scg.book;

import com.example.book_management_system_for_test_scg.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BookControllerAdvice {

    @ExceptionHandler(DuplicateISBNException.class)
    public ResponseEntity<ErrorResponse> duplicateISBN(DuplicateISBNException e) {
        ErrorResponse error = new ErrorResponse();
        error.setCode("24000");
        error.setDescription("Book with the same ISBN already exists.");
        error.setStatus(false);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> bookNotFoundHandler(BookNotFoundException e) {
        ErrorResponse error = new ErrorResponse();
        error.setCode("25000");
        error.setDescription("Book not found");
        error.setStatus(false);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode("40000");
        error.setDescription("Validation failed for one or more fields.");
        error.setStatus(false);

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        error.setDescription(fieldErrors.toString());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}