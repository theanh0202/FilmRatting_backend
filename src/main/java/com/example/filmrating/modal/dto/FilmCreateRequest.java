package com.example.filmrating.modal.dto;

import com.example.filmrating.modal.entity.Genre;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FilmCreateRequest {
    @NotBlank(message = "Tên phim không để trống")
    private String title;

    private String image;
    private int year;
    private String description;
    private Genre genre;
}
