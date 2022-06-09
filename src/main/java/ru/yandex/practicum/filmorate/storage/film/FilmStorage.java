package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    public void create(Film film);

    public void update(Film film);

    public void delete(long id);

    public List<Film> findAll();

    public Film findById(long id);
}
