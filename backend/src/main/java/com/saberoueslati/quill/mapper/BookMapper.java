package com.saberoueslati.quill.mapper;

import com.saberoueslati.quill.dto.BookDTO;
import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.entity.Book;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        if (book == null) return null;

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPublicationDate(),
                book.getIsbn(),
                book.getAuthor() != null ? book.getAuthor().getId() : null
        );
    }

    public static Book toEntity(BookDTO dto, Author author) {
        if (dto == null) return null;

        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setPublicationDate(dto.getPublicationDate());
        book.setIsbn(dto.getIsbn());
        book.setAuthor(author);
        return book;
    }
}
