package com.saberoueslati.quill.service.impl;

import com.saberoueslati.quill.entity.Publication;
import com.saberoueslati.quill.repository.PublicationRepository;
import com.saberoueslati.quill.service.PublicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public Page<Publication> getPublications(Pageable pageable) {
        return publicationRepository.findAll(pageable);
    }

    @Override
    public List<Publication> searchPublicationsByTitle(String title) {
        return publicationRepository.findByTitleContainingIgnoreCase(title);
    }
}
