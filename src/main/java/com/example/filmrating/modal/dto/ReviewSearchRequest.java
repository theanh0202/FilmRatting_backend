package com.example.filmrating.modal.dto;

import lombok.Data;

@Data
public class ReviewSearchRequest extends BaseRequest{
    private float rating;
    private int accountId;
    private int filmId;
}
