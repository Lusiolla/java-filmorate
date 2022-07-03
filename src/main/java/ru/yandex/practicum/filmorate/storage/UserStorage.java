package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserStorage {
    void create(User newUser);

    void update(User user);

    void delete(long id);

    List<User> findAll();

    User findById(long id) throws SQLException;

}
