package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final FilmStorage filmStorage;

    public FilmController(FilmService filmService,
                          @Qualifier("filmDB") FilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("{id}")
    public Film findById(@PathVariable Long id) throws SQLException {
        Film film = filmService.findById(id);
                log.info("Найден фильм: {} {}", film.getId(),
                        film.getName());
        return film;
    }

    @GetMapping("popular")
    public List<Film> getMostPopular(
            @RequestParam(defaultValue = "10", required = false) @Valid @Positive Integer count) {
        List<Film> f = filmService.getMostPopular(count);
        log.debug("Return " + f.size() + " most popular films.");
        return f;
    }

    @PostMapping
    public Film create(@Valid @NotNull @RequestBody Film film) {
        validate(film);
        filmStorage.create(film);
        log.debug("Added a film: {}", film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @NotNull @RequestBody Film film) throws SQLException {
        validate(film);
        Film filmFromStorage = filmStorage.update(film);
        log.debug("Updated film information: {}", film);
        return filmFromStorage;
    }

    @PutMapping("{id}/like/{userId}")
    public Long like(@PathVariable long id, @PathVariable long userId) throws SQLException {
        filmService.like(id, userId);
        log.debug("The user " + userId + " like " + id + " film.");
        return id;
    }

    @DeleteMapping("{id}/like/{userId}")
    public Long dislike(@PathVariable long id, @PathVariable long userId) throws SQLException {
        filmService.dislike(id, userId);
        log.debug("The user " + userId + " dislike " + id + " film.");
        return id;
    }

    @DeleteMapping("{id}")
    public Long delete(@PathVariable long id) {
        filmStorage.delete(id);
        log.debug("The film " + id + " delete.");
        return id;
    }

    private void validate(Film film) {
        validateMpa(film.getMpa());
        validateDateRealise(film.getReleaseDate());
    }

    private void validateDateRealise(LocalDate releaseDate) {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException();
        }
    }

    private void validateMpa(Mpa mpa) {
        if (mpa.getId() <= 0) {
            throw new ValidationException();
        }
    }

}


