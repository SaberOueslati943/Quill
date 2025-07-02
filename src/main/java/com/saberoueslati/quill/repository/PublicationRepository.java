package com.saberoueslati.quill.repository;

import com.saberoueslati.quill.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByTitleContainingIgnoreCase(String title);

    Page<Publication> findAll(Pageable pageable);
}
