package com.saberoueslati.quill.mapper;

import com.saberoueslati.quill.dto.PublicationDTO;
import com.saberoueslati.quill.entity.Book;
import com.saberoueslati.quill.entity.Magazine;
import com.saberoueslati.quill.entity.Publication;

public class PublicationMapper {

    public static PublicationDTO toDTO(Publication publication) {
        String type;

        if (publication instanceof Book) {
            type = "BOOK";
        } else if (publication instanceof Magazine) {
            type = "MAGAZINE";
        } else {
            type = "UNKNOWN";
        }

        return new PublicationDTO(
                publication.getId(),
                publication.getTitle(),
                publication.getPublicationDate(),
                type
        );
    }
}
