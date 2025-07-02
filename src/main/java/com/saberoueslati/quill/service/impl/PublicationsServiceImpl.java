package com.saberoueslati.quill.service.impl;

import com.saberoueslati.quill.entity.Publication;
import com.saberoueslati.quill.repository.PublicationRepository;
import com.saberoueslati.quill.service.PublicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationsServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationsServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public Page<Publication> getPublications(Pageable pageable) {
        return publicationRepository.findAll(pageable);
    }

    @Override
    public Page<Publication> searchPublicationsByTitle(String title, Pageable pageable) {
        return publicationRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    @Override
    public Publication addPublication(Publication publication) {
        return publicationRepository.save(publication);
    }
}
