package com.saberoueslati.quill.service;

import com.saberoueslati.quill.entity.Author;

import java.util.List;

public interface AuthorService {

    Author getAuthorById(Long authorId);

    Author addAuthor(Author author);

    List<Author> getAllAuthors();

    List<Author> addAuthors(List<Author> authors);
}
