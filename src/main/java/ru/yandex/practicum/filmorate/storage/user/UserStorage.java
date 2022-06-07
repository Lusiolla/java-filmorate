package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    public void create(User newUser) throws RuntimeException;

    public void update(User user) throws RuntimeException;

    public List<User> findAll();

    public User findUserById(long id) throws RuntimeException;
}
