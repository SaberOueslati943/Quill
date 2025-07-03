package com.saberoueslati.quill.mapper;

import com.saberoueslati.quill.dto.AuthorDTO;
import com.saberoueslati.quill.entity.Author;

public class AuthorMapper {
    public static AuthorDTO toDTO(Author author) {
        if (author == null) return null;

        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        );
    }

    public static Author toEntity(AuthorDTO dto) {
        if (dto == null) return null;

        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        author.setBirthDate(dto.getBirthDate());
        author.setNationality(dto.getNationality());
        return author;
    }
}
