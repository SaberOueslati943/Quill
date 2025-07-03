package com.saberoueslati.quill.controller;

import com.saberoueslati.quill.dto.MagazineDTO;
import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.entity.Magazine;
import com.saberoueslati.quill.mapper.MagazineMapper;
import com.saberoueslati.quill.service.AuthorService;
import com.saberoueslati.quill.service.MagazineService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineService magazineService;
    private final AuthorService authorService;

    public MagazineController(MagazineService magazineService, AuthorService authorService) {
        this.magazineService = magazineService;
        this.authorService = authorService;
    }

    @PostMapping("/bulk")
    public List<MagazineDTO> addMagazines(@Valid @RequestBody List<@Valid MagazineDTO> magazineDTO) {
        List<Magazine> magazines = magazineDTO.stream().map(dto -> {
            List<Author> authors = dto.getAuthorIds()
                    .stream()
                    .map(authorService::getAuthorById)
                    .toList();
            return MagazineMapper.toEntity(dto, authors);
        }).toList();
        List<Magazine> savedMagazines = magazineService.addMagazines(magazines);
        return savedMagazines
                .stream()
                .map(MagazineMapper::toDTO)
                .toList();
    }

    @PostMapping
    public MagazineDTO addMagazine(@Valid @RequestBody MagazineDTO magazineDTO) {
        List<Author> authors = magazineDTO.getAuthorIds()
                .stream()
                .map(authorService::getAuthorById)
                .toList();
        Magazine saved = magazineService.addMagazine(MagazineMapper.toEntity(magazineDTO, authors));
        return MagazineMapper.toDTO(saved);
    }

    @GetMapping
    public List<MagazineDTO> getAllMagazines() {
        return magazineService.getAllMagazines()
                .stream()
                .map(MagazineMapper::toDTO)
                .toList();
    }
}
