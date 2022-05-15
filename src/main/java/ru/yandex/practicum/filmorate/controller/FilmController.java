package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class FilmController {

    private final HashMap<Long, Film> films = new HashMap<>();

    @GetMapping("/films")
    public List<Film> findAll() {
        List<Film> listUsers = new ArrayList<>();
        for (Map.Entry<Long, Film> entry : films.entrySet()) {
            listUsers.add(entry.getValue());
        }
        log.debug("Current number of films: {}", listUsers.size());
        return listUsers;
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) throws Exception {
        validate(film);
        log.debug("Added a film: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) throws Exception {
        validate(film);
        log.debug("Updated film information: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    private void validate(Film film) throws Exception {
       validateDateRealise(film.getReleaseDate());
       validateDescription(film.getDescription());
    }
    private void validateDateRealise(LocalDate releaseDate) throws Exception {
        if (releaseDate.isBefore(LocalDate.of(1895,12,28))) {
            log.warn("The film failed validation");
            throw new ValidationException("The film failed validation");
        }
    }
    private void validateDescription(String description) throws ValidationException {
        if (description.length() > 200) {
            log.warn("The film failed validation");
            throw new ValidationException("The film failed validation");
        }

    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> exceptionHandler(ValidationException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


