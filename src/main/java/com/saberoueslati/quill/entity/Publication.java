package com.saberoueslati.quill.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Publication {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private LocalDate publicationDate;
}
