package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    private final LikeStorage likeStorage;

    public void create(Film film) throws RuntimeException {
        filmStorage.create(film);
    }

    public void update(Film film) throws RuntimeException {
        filmStorage.update(film);
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findFilmById(long id) throws RuntimeException {
        return filmStorage.findFilmById(id);
    }

    public void like(long filmId, long userId) throws RuntimeException {
        Film film = filmStorage.findFilmById(filmId);
        userStorage.findUserById(userId);
        if (!likeStorage.like(filmId, userId)) {
            film.setRate(film.getRate() + 1);
        }
    }

    public void dislike(long filmId, long userId) throws RuntimeException {
        likeStorage.dislike(filmId, userId);
        Film film = filmStorage.findFilmById(filmId);
        film.setRate(film.getRate() - 1);
    }

    public List<Film> getMostPopular(int count) {
        return filmStorage.getMostPopular(count);
    }
}
