package com.example.filmrating.modal.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "`Film`")
public class Film {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "year")
    private int year;

    @Column(name = "rating")
    private float rating;

    @Column(name = "description")
    private String description;

    @Column(name = "genre")
    private Genre genre;

}
