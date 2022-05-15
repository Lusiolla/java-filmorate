package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private final HashMap<Long, User> users = new HashMap<>();

    @GetMapping("/users")
    public ArrayList<User> findAll() {
        ArrayList<User> listUsers = new ArrayList<>();
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            listUsers.add(entry.getValue());
        }
        log.debug("Current number of users: {}", listUsers.size());
        return listUsers;
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user)  {
        log.debug("User added: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        log.debug("Updated user information: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> exceptionHandler(ValidationException e) {
        log.debug(e.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
