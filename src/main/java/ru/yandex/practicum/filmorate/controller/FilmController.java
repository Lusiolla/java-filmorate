package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("{id}")
    public Film findFilmById(@PathVariable Long id) throws RuntimeException {
        return filmService.findFilmById(id);
    }

    @GetMapping("popular")
    public List<Film> getMostPopular(
            @RequestParam(value = "count", defaultValue = "10", required = false) @Valid @Positive Integer count) {
        List<Film> f = filmService.getMostPopular(count);
        log.debug("Return " + f.size() + " most popular films.");
        return f;
    }

    @PostMapping
    public Film create(@Valid @NotNull @RequestBody Film film) throws RuntimeException {
        validate(film);
        filmService.create(film);
        log.debug("Added a film: {}", film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @NotNull @RequestBody Film film) throws RuntimeException {
        validate(film);
        filmService.update(film);
        log.debug("Updated film information: {}", film);
        return film;
    }

    @PutMapping("{id}/like/{userId}")
    public Long like(@PathVariable long id, @PathVariable long userId) throws RuntimeException {
        filmService.like(id, userId);
        log.debug("The user " + userId + " like " + id + " film.");
        return id;
    }

    @DeleteMapping("{id}/like/{userId}")
    public Long dislike(@PathVariable long id, @PathVariable long userId) throws RuntimeException {
        filmService.dislike(id, userId);
        log.debug("The user " + userId + " dislike " + id + " film.");
        return id;
    }

    private void validate(Film film) throws RuntimeException {
        validateDateRealise(film.getReleaseDate());
    }

    private void validateDateRealise(LocalDate releaseDate) throws RuntimeException {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException();
        }
    }

}


