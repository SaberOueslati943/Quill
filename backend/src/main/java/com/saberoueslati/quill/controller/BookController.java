package com.saberoueslati.quill.controller;

import com.saberoueslati.quill.dto.BookDTO;
import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.entity.Book;
import com.saberoueslati.quill.mapper.BookMapper;
import com.saberoueslati.quill.service.AuthorService;
import com.saberoueslati.quill.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @PostMapping("/bulk")
    public List<BookDTO> addBooks(@Valid @RequestBody List<@Valid BookDTO> bookDTOs) {
        List<Book> books = bookDTOs.stream().map(dto -> {
            Author author = authorService.getAuthorById(dto.getAuthorId());
            return BookMapper.toEntity(dto, author);
        }).toList();
        List<Book> savedBooks = bookService.addBooks(books);
        return savedBooks
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @PostMapping
    public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) {
        Author author = authorService.getAuthorById(bookDTO.getAuthorId());
        Book saved = bookService.addBook(BookMapper.toEntity(bookDTO, author));
        return BookMapper.toDTO(saved);
    }

    @GetMapping("/isbn/{isbn}")
    public BookDTO getBookByIsbn(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        return BookMapper.toDTO(book);
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
