package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    public void create(User newUser);

    public void update(User user);
    public void delete(long id);

    public List<User> findAll();

    public User findById(long id);
}
