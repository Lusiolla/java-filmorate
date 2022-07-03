package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component("filmDB")
@RequiredArgsConstructor
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final static String FIND_BY_ID = "select " +
            "f.film_id, +" +
            "f.film_name, " +
            "f.description, " +
            "f.release_date, " +
            "f.duration, " +
            "f.rate, " +
            "f.mpa_id, " +
            "m.mpa_name, " +
            "from mpa as m " +
            "inner join films as f ON f.mpa_id = m.mpa_id where f.film_id = ?";

    private final static String FIND_ALL_FILMS = "select " +
            "f.film_id, +" +
            "f.film_name, " +
            "f.description, " +
            "f.release_date, " +
            "f.duration, " +
            "f.rate, " +
            "f.mpa_id, " +
            "m.mpa_name, " +
            "from mpa as m " +
            "inner join films as f ON f.mpa_id = m.mpa_id";
    private final static String UPDATE_FILM = "update " +
            "films set " +
            "film_name = ?, " +
            "description = ?, " +
            "release_date = ?, " +
            "duration = ?, " +
            "rate = ?, " +
            "mpa_id = ? " +
            "where film_id = ?";

    private final static String DELETE_FILM = "delete " +
            "from films " +
            "where film_id = ?";

    private final static String ADD_GENRE_OF_FILM = "insert " +
            "into genre_of_film " +
            "(genre_id, film_id) " +
            "values (?, ?)";
    private final static String DELETE_GENRE_OF_FILM = "delete " +
            "from genre_of_film " +
            "where film_id = ?";
    private final static String FIND_GENRE = "select " +
            "gf.genre_id, " +
            "g.genre_name, " +
            "from genres as g " +
            "inner join genre_of_film as gf on g.genre_id = gf.genre_id " +
            "where gf.film_id = ? " +
            "order by gf.genre_id asc";
    private final static String GET_POPULAR = "select " +
            "f.film_id, " +
            "f.film_name, " +
            "f.description, " +
            "f.release_date, " +
            "f.duration, " +
            "f.rate, " +
            "f.mpa_id, " +
            "m.mpa_name, " +
            "from mpa as m " +
            "inner join films as f ON f.mpa_id = m.mpa_id " +
            "order by f.rate desc " +
            "limit ?";


    @Override
    public void create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue());
        setGenreOfFilm(film);
    }

    @Override
    public Film update(Film film) throws SQLException {
        boolean answ = jdbcTemplate.update(UPDATE_FILM,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId()) < 1;
        if (answ) {
            throw new FilmNotFoundException("Что-то пошло не так. Не удалось обновить фильм " + film.getId());
        }
        setGenreOfFilm(film);
        Film filmFromStorage = findById(film.getId());
        //тесты требуют, чтобы поле genres иногда возвращалось с нулевой ссылкой, иногда с пустым списокм
        if (film.getGenres() != null && film.getGenres().isEmpty()) {
            filmFromStorage.setGenres(new HashSet<>());
        }
        return filmFromStorage;
    }

    @Override
    public void delete(long id) {
        if (jdbcTemplate.update(DELETE_FILM, id) < 1) {
            log.info("Что-то пошло не так, пользователь {} не был найден", id);
        }
    }

    @Override
    public List<Film> findAll() {
        return jdbcTemplate.query(FIND_ALL_FILMS, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film findById(long id) throws SQLException {
        Optional<Film> films = jdbcTemplate.query(FIND_BY_ID, (rs, rowNum) -> makeFilm(rs), id).stream().findFirst();
        if (films.isPresent()) {
            return films.get();
        } else {
            throw new FilmNotFoundException();
        }
    }

    @Override
    public List<Film> getMostPopular(int count) {
        return jdbcTemplate.query(GET_POPULAR, (rs, rowNum) -> makeFilm(rs), count);
    }

    private Film makeFilm(ResultSet resultSet) throws SQLException {
        int filmId = resultSet.getInt("film_id");
        Set<Genre> genres =  new HashSet<>(jdbcTemplate.query(FIND_GENRE,
                (rs, rowNum) -> makeGenre(rs), filmId));
        //тесты требуют поле иногда с нулевой ссылкой, иногда с пустым списокм
        if (genres.isEmpty()) {
            genres = null;
        }
        return new Film(filmId,
                resultSet.getString("film_name"),
                resultSet.getString("description"),
                resultSet.getDate("release_date").toLocalDate(),
                resultSet.getLong("duration"),
                resultSet.getInt("rate"),
                new Mpa(resultSet.getInt("mpa_id"), resultSet.getString("mpa_name")),
                genres
        );
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
    }

    private void setGenreOfFilm(Film film) {
        if (film.getGenres() == null) {
            return;
        }
        jdbcTemplate.update(DELETE_GENRE_OF_FILM, film.getId());
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(ADD_GENRE_OF_FILM, genre.getId(), film.getId());
        }
    }
}

