package com.example.filmrating.modal.dto;

import com.example.filmrating.modal.entity.Genre;
import lombok.Data;

import java.util.Set;

@Data
public class FilmSearchRequest extends BaseRequest {
    private String title;
    private int year;
    private float rating;
    private Genre genre;
}
