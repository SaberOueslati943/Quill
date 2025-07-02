package com.saberoueslati.quill.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private LocalDate birthDate;
    private String nationality;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();
}
