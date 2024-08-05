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

    public Optional<Book> updateBook(Long id, BookRequest bookRequest) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookRequest.getTitle());
            book.setAuthor(bookRequest.getAuthor());
            book.setIsbn(bookRequest.getIsbn());
            book.setPublishedDate(bookRequest.getPublishedDate());
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