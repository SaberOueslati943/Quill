package com.saberoueslati.quill.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Nationality is required")
    private String nationality;
}
