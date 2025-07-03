package com.saberoueslati.quill.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PublicationDTO {

    private Long id;
    private String title;
    private LocalDate publicationDate;
    private String type;
}
