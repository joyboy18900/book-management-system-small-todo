package com.example.book_management_system_for_test_scg.book;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<BookResponse> getAllBooks() {
        List<Book> bookList = bookService.getAllBooks();

        BookResponse bookResponse = new BookResponse();
        bookResponse.setCode(String.valueOf(HttpStatus.OK.value()));
        bookResponse.setBookList(bookList);
        bookResponse.setStatus(true);

        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new BookNotFoundException(""));

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        BookResponse bookResponse = new BookResponse();
        bookResponse.setCode(String.valueOf(HttpStatus.OK.value()));
        bookResponse.setBookList(bookList);
        bookResponse.setStatus(true);

        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest bookRequest) {
        Book book = bookService.addBook(bookRequest);

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        BookResponse bookResponse = new BookResponse();
        bookResponse.setCode(String.valueOf(HttpStatus.CREATED.value()));
        bookResponse.setBookList(bookList);
        bookResponse.setStatus(true);

        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {
        Optional<Book> updatedBook = bookService.updateBook(id, bookRequest);

        if (updatedBook.isPresent()) {
            List<Book> bookList = new ArrayList<>();
            bookList.add(updatedBook.get());

            BookResponse bookResponse = new BookResponse();
            bookResponse.setCode(String.valueOf(HttpStatus.OK.value()));
            bookResponse.setBookList(bookList);
            bookResponse.setStatus(true);

            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponse> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);

        if (isDeleted) {
            BookResponse bookResponse = new BookResponse();
            bookResponse.setCode(String.valueOf(HttpStatus.NO_CONTENT.value()));
            bookResponse.setStatus(true);

            return new ResponseEntity<>(bookResponse, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}