package ru.yandex.practicum.filmorate.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.SQLException;
import java.util.List;



@Service
@Component
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    private final LikeStorage likeStorage;

    public FilmService(@Qualifier("filmDB") FilmStorage filmStorage,
                       @Qualifier("userDB") UserStorage userStorage,
                       @Qualifier("likeDB") LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(long id) throws SQLException {
        return filmStorage.findById(id);
    }

    public void like(long filmId, long userId) throws SQLException {
        filmStorage.findById(filmId);
        userStorage.findById(userId);
        likeStorage.like(filmId, userId);
    }

    public void dislike(long filmId, long userId) throws SQLException {
        filmStorage.findById(filmId);
        userStorage.findById(userId);
        likeStorage.dislike(filmId, userId);
    }
    public List<Film> getMostPopular(int count) {
        return filmStorage.getMostPopular(count);

    }
}
