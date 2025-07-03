package com.saberoueslati.quill.service;

import com.saberoueslati.quill.entity.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);

    Book getBookByIsbn(String isbn);

    List<Book> getAllBooks();

    List<Book> addBooks(List<Book> books);
}
