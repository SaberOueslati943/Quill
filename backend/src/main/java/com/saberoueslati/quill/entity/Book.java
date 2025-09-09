package com.saberoueslati.quill.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("BOOK")
public class Book extends Publication {
    private String isbn;

    @ManyToOne
    private Author author;
}
