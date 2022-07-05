package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    private final static String FIND_BY_ID = "select * " +
            "from genres " +
            "where genre_id = ?";

    private final static String FIND_ALL = "select * " +
            "from genres";


    @Override
    public Genre findById(long id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(FIND_BY_ID, id);
        if (rs.next()) {
            return new Genre(
                    rs.getInt("genre_id"),
                    rs.getString("genre_name")
            );
        } else {
            throw new GenreNotFoundException();
        }

    }

    @Override
    public Collection<Genre> findAll() {
        return jdbcTemplate.query(FIND_ALL, (rs, rowNum) -> makeGenre(rs));
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getInt("genre_id"),
                rs.getString("genre_name"));
    }
}
