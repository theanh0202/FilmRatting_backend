package com.example.filmrating.repository.specification;

import com.example.filmrating.modal.dto.FilmSearchRequest;
import com.example.filmrating.modal.entity.Film;
import org.springframework.data.jpa.domain.Specification;

public class FilmSpecification {
    public static Specification<Film> buildCondition (FilmSearchRequest request) {
        return Specification.where(buildConditionTitle(request)).and(buildConditionYear(request))
                .and(buildConditionRating(request)).and(buildConditionGenre(request));
    }

    public static Specification<Film> buildConditionTitle (FilmSearchRequest request) {
        if (request.getTitle() != null && request.getTitle() != "") {
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.like(root.get("title"), "%" + request.getTitle() + "%");
            };
        } else {
            return null;
        }
    }

    public static Specification<Film> buildConditionYear (FilmSearchRequest request) {
        if (request.getYear() > 0) {
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.equal(root.get("year"), request.getYear());
            };
        } else {
            return null;
        }
    }
    public static Specification<Film> buildConditionRating (FilmSearchRequest request) {
        if (request.getRating() > 0) {
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.equal(root.get("rating"), request.getRating());
            };
        } else {
            return null;
        }
    }

    public static Specification<Film> buildConditionGenre (FilmSearchRequest request) {
        if (request.getGenre() != null) {
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.equal(root.get("genre"), request.getGenre());
            };
        } else {
            return null;
        }
    }
}
