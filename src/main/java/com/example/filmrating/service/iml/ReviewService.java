package com.example.filmrating.service.iml;

import com.example.filmrating.exception.AppException;
import com.example.filmrating.exception.ErrorResponseEnum;
import com.example.filmrating.modal.dto.ReviewCreateRequest;
import com.example.filmrating.modal.dto.ReviewSearchRequest;
import com.example.filmrating.modal.dto.ReviewUpdateRequest;
import com.example.filmrating.modal.entity.Account;
import com.example.filmrating.modal.entity.Film;
import com.example.filmrating.modal.entity.Review;
import com.example.filmrating.repository.AccountRepository;
import com.example.filmrating.repository.FilmRepository;
import com.example.filmrating.repository.ReviewRepository;
import com.example.filmrating.repository.specification.ReviewSpecification;
import com.example.filmrating.service.IReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService implements IReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review    create(ReviewCreateRequest request) {
        Optional<Account> accountOptional = accountRepository.findById(request.getAccountId());
        Optional<Film> filmOptional = filmRepository.findById(request.getFilmId());
        if (accountOptional.isEmpty()) {
            throw new AppException(ErrorResponseEnum.ACCOUNT_NOT_FOUND);
        }
        if (filmOptional.isEmpty()) {
            throw new AppException(ErrorResponseEnum.FILM_NOT_FOUND);
        }
        Account account = accountOptional.get();
        Film film = filmOptional.get();
        Review review = new Review();
        review.setAccount(account);
        review.setFilm(film);
        review.setContent(request.getContent());
        review.setRating(request.getRating());
        review = reviewRepository.save(review);
        updateRatingFilm(film);
        return review;
    }

    private void updateRatingFilm(Film film){
        try {
            film.setRating(reviewRepository.abc(film));
        } catch (Exception e){
            film.setRating(0);
        }
    }

    @Override
    public Review getById(int id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            return reviewOptional.get();
        } else {
            throw new AppException(ErrorResponseEnum.REVIEW_NOT_FOUND);
        }
    }

    @Override
    public Review update(ReviewUpdateRequest request) {
        Review review = getById(request.getId());
        if (review != null) {
            BeanUtils.copyProperties(request, review);
            review = reviewRepository.save(review);
            updateRatingFilm(review.getFilm());
            return review;
        } else {
            throw new AppException(ErrorResponseEnum.REVIEW_NOT_FOUND);
        }
    }

    @Override
    public void delete(int id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            reviewRepository.deleteById(id);
            updateRatingFilm(reviewOptional.get().getFilm());
        } else {
            throw new AppException(ErrorResponseEnum.REVIEW_NOT_FOUND);
        }
    }

    @Override
    public Page<Review> search(ReviewSearchRequest request) {
        PageRequest pageRequest = null;
        if ("DESC".equals(request.getSortType())) {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getSortField()).descending());
        } else {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getSortField()).descending());
        }
        Specification<Review> condition = ReviewSpecification.buildCondition(request);
        return reviewRepository.findAll(condition, pageRequest);
    }

    @Override
    public List<Review> findByFilm(int filmId) {
        return reviewRepository.findAllByFilm_Id(filmId);
    }

    public List<Review> findByAccount(int accountId) {
        return reviewRepository.findAllByAccount_Id(accountId);
    }
}
