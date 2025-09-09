package com.saberoueslati.quill.mapper;

import com.saberoueslati.quill.dto.MagazineDTO;
import com.saberoueslati.quill.entity.Author;
import com.saberoueslati.quill.entity.Magazine;

import java.util.List;

public class MagazineMapper {
    public static MagazineDTO toDTO(Magazine magazine) {
        if (magazine == null) return null;

        List<Long> authorIds = magazine.getAuthors()
                .stream()
                .map(Author::getId)
                .toList();

        return new MagazineDTO(
                magazine.getId(),
                magazine.getTitle(),
                magazine.getPublicationDate(),
                magazine.getIssueNumber(),
                authorIds
        );
    }

    public static Magazine toEntity(MagazineDTO dto, List<Author> authors) {
        if (dto == null) return null;

        Magazine magazine = new Magazine();
        magazine.setId(dto.getId());
        magazine.setTitle(dto.getTitle());
        magazine.setPublicationDate(dto.getPublicationDate());
        magazine.setIssueNumber(dto.getIssueNumber());
        magazine.setAuthors(authors);
        return magazine;
    }
}
