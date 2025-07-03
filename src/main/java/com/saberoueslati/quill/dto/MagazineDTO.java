package com.saberoueslati.quill.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MagazineDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Publication data is required")
    private LocalDate publicationDate;

    @Min(value = 1, message = "Issue number must be at least 1")
    private int issueNumber;

    @NotEmpty(message = "At least one author ID is required")
    private List<Long> authorIds;
}
