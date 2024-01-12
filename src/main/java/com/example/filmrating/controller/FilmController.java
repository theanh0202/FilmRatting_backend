package com.example.filmrating.controller;

import com.example.filmrating.modal.dto.FilmCreateRequest;
import com.example.filmrating.modal.dto.FilmSearchRequest;
import com.example.filmrating.modal.dto.FilmUpdateRequest;
import com.example.filmrating.modal.entity.Film;
import com.example.filmrating.service.iml.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@CrossOrigin("*")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping("/get-all")
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @PostMapping("/create")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Film create(@RequestBody FilmCreateRequest request) {
        return filmService.create(request);
    }

    @GetMapping("/get-by-id")
    public Film getById(@RequestParam int id){
        return filmService.getById(id);
    }

    @PutMapping("/update")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Film update(@RequestBody FilmUpdateRequest request) {
        return filmService.update(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String delete(@PathVariable int id){
        filmService.delete(id);
        return "Đã xóa thành công";
    }

    @PostMapping("/search")
    public Page<Film> search(@RequestBody FilmSearchRequest request) {
        return filmService.search(request);
    }


}
