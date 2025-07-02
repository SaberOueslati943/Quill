package com.saberoueslati.quill.repository;

import com.saberoueslati.quill.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface bookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
}
