package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final HashMap<Long, User> users = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        List<User> listUsers = new ArrayList<>();
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            listUsers.add(entry.getValue());
        }
        return listUsers;
    }

    @PostMapping
    public User create(@Valid @NotNull @RequestBody User user) throws ValidationException {
        log.debug("User added: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @NotNull @RequestBody User user) throws ValidationException {
        log.debug("Updated user information: {}", user);
        users.put(user.getId(), user);
        return user;
    }


}
