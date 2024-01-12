package com.example.filmrating.repository;

import com.example.filmrating.modal.entity.Film;
import com.example.filmrating.modal.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
    List<Review> findAllByFilm_Id(int filmId);
    @Query(value = "select avg(r.rating) from Review r where r.film = :film")
    float abc(Film film);
    List<Review> findAllByAccount_Id(int accountId);
}
