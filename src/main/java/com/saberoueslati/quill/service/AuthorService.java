package com.saberoueslati.quill.service;

import com.saberoueslati.quill.entity.Author;

import java.util.List;

public interface AuthorService {
    
    Author addAuthor(Author author);

    List<Author> getAllAuthors();
}
