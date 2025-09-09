package com.saberoueslati.quill.controller;

import com.saberoueslati.quill.dto.PublicationDTO;
import com.saberoueslati.quill.entity.Publication;
import com.saberoueslati.quill.mapper.PublicationMapper;
import com.saberoueslati.quill.service.PublicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/search")
    public List<PublicationDTO> getPublicationsByTitle(
            @RequestParam(required = false) String title
    ) {
        if (title != null && !title.isBlank()) {
            return publicationService
                    .searchPublicationsByTitle(title)
                    .stream()
                    .map(PublicationMapper::toDTO)
                    .toList();
        }
        return new ArrayList<>();
    }

    @GetMapping
    public Page<PublicationDTO> getPublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Publication> publications = publicationService.getPublications(pageable);
        return publications.map(PublicationMapper::toDTO);
    }
}
