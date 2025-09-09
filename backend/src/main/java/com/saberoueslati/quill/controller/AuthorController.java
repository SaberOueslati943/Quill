package com.saberoueslati.quill.controller;

import com.saberoueslati.quill.dto.AuthorDTO;
import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.mapper.AuthorMapper;
import com.saberoueslati.quill.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors()
                .stream()
                .map(AuthorMapper::toDTO)
                .toList();
    }

    @PostMapping("/bulk")
    public List<AuthorDTO> addAuthors(@Valid @RequestBody List<@Valid AuthorDTO> authorDTOs) {
        List<Author> authors = authorDTOs
                .stream()
                .map(AuthorMapper::toEntity)
                .toList();
        List<Author> saved = authorService.addAuthors(authors);
        return saved
                .stream()
                .map(AuthorMapper::toDTO)
                .toList();
    }

    @PostMapping
    public AuthorDTO addAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        System.out.print("Author received: " + authorDTO);
        Author saved = authorService.addAuthor(AuthorMapper.toEntity(authorDTO));
        return AuthorMapper.toDTO(saved);
    }

    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
