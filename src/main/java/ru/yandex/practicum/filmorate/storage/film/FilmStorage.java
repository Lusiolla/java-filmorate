package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    public void create(Film film) throws RuntimeException;

    public void update(Film film) throws RuntimeException;

    public List<Film> findAll();

    public Film findFilmById(long id) throws RuntimeException;

    public List<Film> getMostPopular(int count);
}
