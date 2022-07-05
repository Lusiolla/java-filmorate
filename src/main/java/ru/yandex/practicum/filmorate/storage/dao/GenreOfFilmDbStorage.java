package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GenreOfFilmDbStorage {
    private final JdbcTemplate jdbcTemplate;
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

    protected Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
    }

    protected void setGenreOfFilm(Film film) {
        if (film.getGenres() == null) {
            return;
        }
        jdbcTemplate.update(DELETE_GENRE_OF_FILM, film.getId());
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(ADD_GENRE_OF_FILM, genre.getId(), film.getId());
        }
    }

    protected Set<Genre> findGenreByFilm(long filmId) {
        Set<Genre> genres =  new HashSet<>(jdbcTemplate.query(FIND_GENRE,
                (rs, rowNum) -> makeGenre(rs), filmId));
        //тесты требуют поле иногда с нулевой ссылкой, иногда с пустым списокм
        if (genres.isEmpty()) {
            genres = null;
        }
        return genres;
    }
}
