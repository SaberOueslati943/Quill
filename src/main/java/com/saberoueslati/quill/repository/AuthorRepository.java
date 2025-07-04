package com.saberoueslati.quill.repository;

import com.saberoueslati.quill.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> getAuthorById(Long id);
}
