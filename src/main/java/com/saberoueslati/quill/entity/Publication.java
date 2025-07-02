package com.saberoueslati.quill.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Publication {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private LocalDate publicationDate;
}
