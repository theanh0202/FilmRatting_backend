package com.example.filmrating.controller;

import com.example.filmrating.modal.dto.ReviewCreateRequest;
import com.example.filmrating.modal.dto.ReviewSearchRequest;
import com.example.filmrating.modal.dto.ReviewUpdateRequest;
import com.example.filmrating.modal.entity.Film;
import com.example.filmrating.modal.entity.Review;
import com.example.filmrating.service.iml.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@CrossOrigin("*")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/get-all")
    public List<Review> getAll() {
        return reviewService.getAll();
    }

    @PostMapping("/create")
    @PreAuthorize(value = "hasAuthority('USER')")
    public Review create(@RequestBody ReviewCreateRequest request) {
        return reviewService.create(request);
    }

    @GetMapping("/{id}")
    public Review getById(@PathVariable int id) {
        return reviewService.getById(id);
    }

    @PutMapping("/update")
    @PreAuthorize(value = "hasAuthority('USER')")
    public Review update(@RequestBody ReviewUpdateRequest request) {
        return reviewService.update(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    public String delete(@PathVariable int id) {
        reviewService.delete(id);
        return "Đã xóa thành công!";
    }

    @PostMapping("/search")
    public Page<Review> search(@RequestBody ReviewSearchRequest request) {
        return reviewService.search(request);
    }

    @GetMapping("/find-by-film")
    public List<Review> findByFilm(@RequestParam int filmId) {
        return reviewService.findByFilm(filmId);
    }

    @GetMapping("/find-by-account")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    public List<Review> findByAccount(@RequestParam int accountId) {
        return reviewService.findByAccount(accountId);
    }
}
