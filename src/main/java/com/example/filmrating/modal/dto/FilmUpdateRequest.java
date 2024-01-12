package com.example.filmrating.modal.dto;

import com.example.filmrating.modal.entity.Genre;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class FilmUpdateRequest {
    @NotNull(message = "Id không được để trống")
    @Positive(message = "Id phải lớn hơn 0")
    private int id;

    @NotBlank(message = "Tên phim không để trống")
    private String title;
    private int year;
    private String image;
    private Genre genre;
    private String description;
}
