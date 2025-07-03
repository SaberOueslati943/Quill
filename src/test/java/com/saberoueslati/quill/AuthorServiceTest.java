package com.saberoueslati.quill;

import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.repository.AuthorRepository;
import com.saberoueslati.quill.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author sampleAuthor;

    @BeforeEach
    void setUp() {
        sampleAuthor = new Author();
        sampleAuthor.setId(1L);
        sampleAuthor.setName("Isaac Asimov");
        sampleAuthor.setBirthDate(LocalDate.of(1920, 1, 2));
        sampleAuthor.setNationality("American");
        sampleAuthor.setBooks(new ArrayList<>());
    }

    @Test
    void addAuthor_shouldSaveAndReturnAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(sampleAuthor);

        Author result = authorService.addAuthor(sampleAuthor);

        assertNotNull(result);
        assertEquals("Isaac Asimov", result.getName());
        verify(authorRepository, times(1)).save(sampleAuthor);
    }

    @Test
    void getAllAuthors_shouldReturnListOfAuthors() {
        List<Author> authors = List.of(sampleAuthor);
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.getAllAuthors();

        assertEquals(1, result.size());
        assertEquals("Isaac Asimov", result.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void getAuthorById_existingId_shouldReturnAuthor() {
        when(authorRepository.getAuthorById(1L)).thenReturn(Optional.of(sampleAuthor));

        Author result = authorService.getAuthorById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(authorRepository, times(1)).getAuthorById(1L);
    }

    @Test
    void getAuthorById_nonExistingId_shouldThrow() {
        when(authorRepository.getAuthorById(999L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> authorService.getAuthorById(999L));

        assertTrue(exception.getMessage().contains("Author not found"));
        verify(authorRepository, times(1)).getAuthorById(999L);
    }

    @Test
    void addAuthors_shouldSaveAndReturnAll() {
        List<Author> authors = List.of(sampleAuthor);
        when(authorRepository.saveAll(authors)).thenReturn(authors);

        List<Author> result = authorService.addAuthors(authors);

        assertEquals(1, result.size());
        verify(authorRepository, times(1)).saveAll(authors);
    }
}
