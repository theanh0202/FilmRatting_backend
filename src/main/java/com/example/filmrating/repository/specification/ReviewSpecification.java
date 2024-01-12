package com.example.filmrating.repository.specification;

import com.example.filmrating.modal.dto.ReviewSearchRequest;
import com.example.filmrating.modal.entity.Account;
import com.example.filmrating.modal.entity.Film;
import com.example.filmrating.modal.entity.Review;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class ReviewSpecification {
    public static Specification<Review> buildCondition(ReviewSearchRequest request) {
        return Specification.where(buildConditionRating(request)).and(buildConditionAccount(request))
                .and(buildConditionFilm(request));
    }

    public static Specification<Review> buildConditionRating(ReviewSearchRequest request) {
        if (request.getRating() > 0) {
            return (root, query, criteriaBuilder) -> {
                return criteriaBuilder.equal(root.get("rating"), request.getRating());
            };
        } else {
            return null;
        }
    }

    public static Specification<Review> buildConditionAccount(ReviewSearchRequest request) {
        if (request.getAccountId() > 0) {
            return (root, query, criteriaBuilder) -> {
                Join<Review, Account> join = root.join("account");
                return criteriaBuilder.equal(join.get("id"), request.getAccountId());
            };
        } else {
            return null;
        }
    }

    public static Specification<Review> buildConditionFilm(ReviewSearchRequest request) {
        if (request.getFilmId() > 0) {
            return (root, query, criteriaBuilder) -> {
                Join<Review, Film> join = root.join("film");
                return criteriaBuilder.equal(join.get("id"), request.getFilmId());
            };
        } else {
            return null;
        }
    }
}
