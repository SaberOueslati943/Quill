package com.saberoueslati.quill.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book extends Publication {
    private String isbn;

    @ManyToOne
    private Author author;
}
