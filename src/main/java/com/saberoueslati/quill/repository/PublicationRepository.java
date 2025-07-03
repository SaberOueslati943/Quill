package com.saberoueslati.quill.repository;

import com.saberoueslati.quill.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByTitleContainingIgnoreCase(String title);
}
