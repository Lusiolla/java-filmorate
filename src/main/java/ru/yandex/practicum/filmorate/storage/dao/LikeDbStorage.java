package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

@Component("likeDB")
public class LikeDbStorage implements LikeStorage {

    private final static String INSERT_LIKE = "insert " +
            "into likes " +
            "(film_id, user_id) " +
            "values (?, ?)";
    private final static String DELETE_LIKE = "delete " +
            "from likes " +
            "where film_id = ? " +
            "and user_id = ?";
    private final static String UPDATE_RATE_FILM_PLUS = "update " +
            "films set " +
            "rate = rate + 1 " +
            "where film_id = ?";
    private final static String UPDATE_RATE_FILM_MINUS = "update " +
            "films set " +
            "rate = rate - 1 " +
            "where film_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void like(long filmId, long userId) {
        jdbcTemplate.update(INSERT_LIKE, filmId, userId);
        jdbcTemplate.update(UPDATE_RATE_FILM_PLUS, filmId);
    }

    @Override
    public void dislike(long filmId, long userId) {
        jdbcTemplate.update(DELETE_LIKE, filmId, userId);
        jdbcTemplate.update(UPDATE_RATE_FILM_MINUS, filmId);
    }
}
