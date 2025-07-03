package com.saberoueslati.quill.service;

import com.saberoueslati.quill.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublicationService {

    Page<Publication> getPublications(Pageable pageable);

    List<Publication> searchPublicationsByTitle(String title);
}
