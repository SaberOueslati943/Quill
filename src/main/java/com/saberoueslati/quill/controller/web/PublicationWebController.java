package com.saberoueslati.quill.controller.web;

import com.saberoueslati.quill.dto.PublicationDTO;
import com.saberoueslati.quill.entity.Publication;
import com.saberoueslati.quill.mapper.PublicationMapper;
import com.saberoueslati.quill.service.PublicationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/web/publications")
public class PublicationWebController {

    private final PublicationService publicationService;

    public PublicationWebController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public String showPublications(@RequestParam(value = "title", required = false) String title, Model model) {
        List<Publication> results;
        if (title != null && !title.isBlank()) {
            results = publicationService.searchPublicationsByTitle(title);
        } else {
            results = publicationService.getPublications(PageRequest.of(0, 100)).getContent();
        }

        List<PublicationDTO> dtos = results.stream()
                .map(PublicationMapper::toDTO)
                .toList();

        model.addAttribute("publications", dtos);
        model.addAttribute("searchTerm", title);
        return "publications";
    }
}

