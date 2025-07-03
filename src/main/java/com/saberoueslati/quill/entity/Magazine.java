package com.saberoueslati.quill.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Magazine extends Publication {
    private int issueNumber;

    @ManyToMany
    private List<Author> authors = new ArrayList<>();
}
