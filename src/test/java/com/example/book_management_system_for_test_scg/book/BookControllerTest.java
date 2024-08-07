package com.example.book_management_system_for_test_scg.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "Title1", "Author1", "ISBN1", LocalDate.now()));
        when(bookService.getAllBooks()).thenReturn(bookList);

        ResponseEntity<BookResponse> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getBookList().size());
        assertEquals("Title1", response.getBody().getBookList().get(0).getTitle());
    }

    @Test
    public void testGetBookById() {
        Book book = new Book(1L, "Title1", "Author1", "ISBN1", LocalDate.now());
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<BookResponse> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Title1", response.getBody().getBookList().get(0).getTitle());
    }

    @Test
    public void testAddBookValidationHappyCase() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content("{\"title\":\"Title1\",\"author\":\"Author1\",\"isbn\":\"1234567890123\",\"publishedDate\":\"2023-10-10\"}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testAddBookValidationBadCase() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content("{\"title\":\"Title1\",\"author\":\"Author1\",\"isbn\":\"12345678901233\",\"publishedDate\":\"2023-10-10\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBook() {
        BookRequest bookRequest = new BookRequest("Title1", "Author1", "ISBN1", LocalDate.now());
        Book book = new Book(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getIsbn(), bookRequest.getPublishedDate());
        when(bookService.addBook(bookRequest)).thenReturn(book);

        ResponseEntity<BookResponse> response = bookController.addBook(bookRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Title1", response.getBody().getBookList().get(0).getTitle());
    }

    @Test
    public void testUpdateBook() {
        BookRequest bookRequest = new BookRequest("Title1", "Author1", "ISBN1", LocalDate.now());
        Book book = new Book(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getIsbn(), bookRequest.getPublishedDate());
        when(bookService.updateBook(1L, bookRequest)).thenReturn(Optional.of(book));

        ResponseEntity<BookResponse> response = bookController.updateBook(1L, bookRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Title1", response.getBody().getBookList().get(0).getTitle());
    }

    @Test
    public void testDeleteBook() {
        when(bookService.deleteBook(1L)).thenReturn(true);

        ResponseEntity<BookResponse> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}