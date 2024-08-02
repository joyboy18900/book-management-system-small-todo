package com.example.book_management_system_for_test_scg.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book addBook(BookRequest bookRequest) {
        Optional<Book> existingBook = bookRepository.findByIsbn(bookRequest.getIsbn());
        if (existingBook.isPresent()) {
            throw new DuplicateISBNException("Book with the same ISBN already exists.");
        }
        Book book = new Book(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getIsbn(), bookRequest.getPublishedDate());
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setPublishedDate(bookDetails.getPublishedDate());
            return bookRepository.save(book);
        });
    }

    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}