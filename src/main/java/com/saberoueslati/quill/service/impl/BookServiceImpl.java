package com.saberoueslati.quill.service.impl;

import com.saberoueslati.quill.entity.Book;
import com.saberoueslati.quill.repository.BookRepository;
import com.saberoueslati.quill.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
