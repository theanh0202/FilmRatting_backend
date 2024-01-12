package com.example.filmrating.service;

import com.example.filmrating.modal.dto.ReviewCreateRequest;
import com.example.filmrating.modal.dto.ReviewSearchRequest;
import com.example.filmrating.modal.dto.ReviewUpdateRequest;
import com.example.filmrating.modal.entity.Film;
import com.example.filmrating.modal.entity.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IReviewService {
    List<Review> getAll();

    Review create(ReviewCreateRequest request);

    Review getById(int id);

    Review update(ReviewUpdateRequest request);

    void delete(int id);

    Page<Review> search(ReviewSearchRequest request);

    List<Review> findByFilm(int filmId);

    List<Review> findByAccount(int accountId);
}
