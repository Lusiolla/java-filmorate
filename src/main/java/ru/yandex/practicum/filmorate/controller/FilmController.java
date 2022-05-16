package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final HashMap<Long, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {
        List<Film> listUsers = new ArrayList<>();
        for (Map.Entry<Long, Film> entry : films.entrySet()) {
            listUsers.add(entry.getValue());
        }
        return listUsers;
    }

    @PostMapping
    public Film create(@Valid @NotNull @RequestBody Film film) throws RuntimeException {
        validate(film);
        log.debug("Added a film: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @NotNull @RequestBody Film film) throws RuntimeException {
        validate(film);
        log.debug("Updated film information: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    private void validate(Film film) throws RuntimeException {
        validateDateRealise(film.getReleaseDate());
    }

    private void validateDateRealise(LocalDate releaseDate) throws RuntimeException {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("The film failed validation");
            throw new ValidationException("The film failed validation");
        }
    }

}


