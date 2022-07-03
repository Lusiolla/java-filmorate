package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;
import java.util.List;

public interface FilmStorage {

    void create(Film film);

    Film update(Film film) throws SQLException;

    void delete(long id);

    List<Film> findAll();

    Film findById(long id) throws SQLException;

    List<Film> getMostPopular(int count);


}
