package com.saberoueslati.quill.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Publication date is required")
    private LocalDate publicationDate;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Author ID is required")
    private Long authorId;
}
