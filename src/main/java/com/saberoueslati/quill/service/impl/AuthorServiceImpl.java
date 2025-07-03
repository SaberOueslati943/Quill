package com.saberoueslati.quill.service.impl;

import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.repository.AuthorRepository;
import com.saberoueslati.quill.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getAuthorById(Long authorId) {
        return authorRepository.getAuthorById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + authorId));
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> addAuthors(List<Author> authors) {
        return authorRepository.saveAll(authors);
    }
}
