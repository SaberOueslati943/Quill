package com.saberoueslati.quill;

import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.entity.Book;
import com.saberoueslati.quill.repository.BookRepository;
import com.saberoueslati.quill.service.AuthorService;
import com.saberoueslati.quill.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book sampleBook;
    private Author sampleAuthor;

    @BeforeEach
    void setUp() {
        sampleAuthor = new Author();
        sampleAuthor.setId(1L);
        sampleAuthor.setName("George Orwell");
        sampleAuthor.setBirthDate(LocalDate.of(1903, 6, 25));
        sampleAuthor.setNationality("British");

        sampleBook = new Book();
        sampleBook.setId(10L);
        sampleBook.setTitle("1984");
        sampleBook.setPublicationDate(LocalDate.of(1949, 6, 8));
        sampleBook.setIsbn("9780451524935");
        sampleBook.setAuthor(sampleAuthor);
    }

    @Test
    void addBook_shouldReturnSavedBook() {
        when(bookRepository.save(sampleBook)).thenReturn(sampleBook);

        Book result = bookService.addBook(sampleBook);

        assertNotNull(result);
        assertEquals("1984", result.getTitle());
        verify(bookRepository, times(1)).save(sampleBook);
    }

    @Test
    void getBookByIsbn_existing_shouldReturnBook() {
        when(bookRepository.findByIsbn("9780451524935")).thenReturn(Optional.of(sampleBook));

        Book result = bookService.getBookByIsbn("9780451524935");

        assertNotNull(result);
        assertEquals("1984", result.getTitle());
        verify(bookRepository, times(1)).findByIsbn("9780451524935");
    }

    @Test
    void getBookByIsbn_nonExisting_shouldThrowException() {
        when(bookRepository.findByIsbn("bad-isbn")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            bookService.getBookByIsbn("bad-isbn");
        });

        assertTrue(exception.getMessage().contains("Book not found"));
        verify(bookRepository).findByIsbn("bad-isbn");
    }

    @Test
    void getAllBooks_shouldReturnList() {
        when(bookRepository.findAll()).thenReturn(List.of(sampleBook));

        List<Book> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitle());
        verify(bookRepository).findAll();
    }

    @Test
    void addBooks_shouldReturnSavedList() {
        List<Book> books = List.of(sampleBook);
        when(bookRepository.saveAll(books)).thenReturn(books);

        List<Book> result = bookService.addBooks(books);

        assertEquals(1, result.size());
        verify(bookRepository).saveAll(books);
    }
}