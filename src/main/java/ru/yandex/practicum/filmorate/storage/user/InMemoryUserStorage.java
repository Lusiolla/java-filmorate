package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private HashMap<Long, User> users = new HashMap<>();

    private long id;


    @Override
    public void create(User newUser) {
        if (newUser.getId() != 0) {
            throw new UserAlreadyExistException();
        }
        newUser.setId(id + 1);
        id = id + 1;
        users.put(newUser.getId(), newUser);
    }

    @Override
    public void update(User user) throws RuntimeException {
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException();
        }
        users.put(user.getId(), user);
    }

    @Override
    public List<User> findAll() {
        List<User> listUsers = new ArrayList<>();
        if (!users.isEmpty()) {
            users.forEach((key, value) -> listUsers.add(value));
        }
        return listUsers;
    }

    @Override
    public User findUserById(long id) throws RuntimeException {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException();
        }
        return users.get(id);
    }

}
