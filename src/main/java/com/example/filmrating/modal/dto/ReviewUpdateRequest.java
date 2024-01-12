package com.example.filmrating.modal.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ReviewUpdateRequest {
    @NotNull(message = "Id không được để trống")
    @Positive(message = "Id phải lớn hơn 0")
    private int id;

    @NotBlank(message = "Không được để trống nội dung")
    private String content;

    @NotNull(message = "Không được để trống rating")
    @PositiveOrZero(message = "Rating không hợp lệ")
    private float rating;

}
