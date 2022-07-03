package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.Collection;

@Component("friendDB")
@AllArgsConstructor
@Slf4j
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;
    private final static String ADD_FRIEND = "insert " +
            "into friends " +
            "(user_id, friend_id) " +
            "values (?, ?)";
    private final static String DELETE_FRIEND = "delete " +
            "from friends " +
            "where user_id = ? " +
            "and friend_id = ?";

    private final static String FIND_FRIENDS = "select " +
            "friend_id " +
            "from friends " +
            "where user_id = ?";

    private final static String MUTUAL_FRIENDS = "select " +
            "friend_id " +
            "from friends " +
            "where user_id = ? " +
            "intersect select friend_id " +
            "from friends " +
            "where user_id = ?";


    @Override
    public void add(long userId, long friendId) {
        jdbcTemplate.update(ADD_FRIEND, userId, friendId);
    }

    @Override
    public void delete(long userId, long friendId) {
        if (jdbcTemplate.update(DELETE_FRIEND, userId, friendId) < 1) {
            log.info("Что-то пошло не так, пара, пользователь {} и его друг {} , не была найдена", userId, friendId);
        }
    }

    @Override
    public Collection<Long> findFriends(long id) {
        return jdbcTemplate.query(FIND_FRIENDS, (rs, rowNum) -> rs.getLong("friend_id"), id);
    }

    @Override
    public Collection<Long> getMutualFriends(long userId, long otherId) {
        return jdbcTemplate.query(MUTUAL_FRIENDS, (rs, rowNum) -> rs.getLong("friend_id"), userId, otherId);
    }
}
