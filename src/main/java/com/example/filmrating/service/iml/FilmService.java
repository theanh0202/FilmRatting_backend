package com.example.filmrating.service.iml;

import com.example.filmrating.exception.AppException;
import com.example.filmrating.exception.ErrorResponseEnum;
import com.example.filmrating.modal.dto.FilmCreateRequest;
import com.example.filmrating.modal.dto.FilmSearchRequest;
import com.example.filmrating.modal.dto.FilmUpdateRequest;
import com.example.filmrating.modal.entity.Film;
import com.example.filmrating.modal.entity.Review;
import com.example.filmrating.repository.FilmRepository;
import com.example.filmrating.repository.ReviewRepository;
import com.example.filmrating.repository.specification.FilmSpecification;
import com.example.filmrating.service.IFilmService;
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
public class FilmService implements IFilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film create(FilmCreateRequest request) {
        Film film = new Film();
        BeanUtils.copyProperties(request, film);
        film.setRating(0);
        return filmRepository.save(film);
    }

    @Override
    public Film getById(int id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if (filmOptional.isPresent()){
            return filmOptional.get();
        } else {
            throw new AppException(ErrorResponseEnum.FILM_NOT_FOUND);
        }
    }

    @Override
    public Film update(FilmUpdateRequest request) {
        Film film = getById(request.getId());
        if (film != null) {
            BeanUtils.copyProperties(request, film);
            return filmRepository.save(film);
        } else{
            throw new AppException(ErrorResponseEnum.FILM_NOT_FOUND);
        }
    }

    @Override
    public void delete(int id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if (filmOptional != null) {
            filmRepository.deleteById(id);
        } else {
            throw new AppException(ErrorResponseEnum.FILM_NOT_FOUND);
        }
    }

    @Override
    public Page<Film> search(FilmSearchRequest request) {
        PageRequest pageRequest = null;
        if ("DESC".equals(request.getSortType())) {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getSortField()).descending());
        } else {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getSortField()).ascending());
        }
        Specification<Film> condition = FilmSpecification.buildCondition(request);
        return filmRepository.findAll(condition, pageRequest);
    }
}
