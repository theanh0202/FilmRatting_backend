package com.example.filmrating.service;

import com.example.filmrating.modal.dto.FilmCreateRequest;
import com.example.filmrating.modal.dto.FilmSearchRequest;
import com.example.filmrating.modal.dto.FilmUpdateRequest;
import com.example.filmrating.modal.entity.Film;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFilmService {
    List<Film> getAll();

    Film create(FilmCreateRequest request);

    Film getById(int id);

    Film update(FilmUpdateRequest request);

    void delete(int id);

    Page<Film> search(FilmSearchRequest request);
}
