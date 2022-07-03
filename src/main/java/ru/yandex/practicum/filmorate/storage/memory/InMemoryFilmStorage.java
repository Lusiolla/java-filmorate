package ru.yandex.practicum.filmorate.storage.memory;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Data
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films;
    private long id;

    @Override
    public void create(Film newFilm) {
        if (newFilm.getId() != 0) {
            throw new FilmAlreadyExistException();
        }
        newFilm.setId(id + 1);
        id = id + 1;
        films.put(newFilm.getId(), newFilm);
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException();
        }
        films.put(film.getId(), film);
        return films.get(film.getId());
    }

    @Override
    public void delete(long id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException();
        }
        films.remove(id);
    }

    @Override
    public List<Film> findAll() {
        List<Film> listFilms = new ArrayList<>();
        if (!films.isEmpty()) {
            films.forEach((key, value) -> listFilms.add(value));
        }
        return listFilms;
    }

    @Override
    public Film findById(long id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException();
        }
        return films.get(id);
    }

    public List<Film> getMostPopular(int count) {
        List<Film> mostPopular = findAll();
        if (count > mostPopular.size()) {
            count = mostPopular.size();
        }
        return mostPopular.stream()
                .sorted(Comparator.comparing(Film::getRate).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

}
