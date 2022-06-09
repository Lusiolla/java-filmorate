package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserStorage userStorage;

    @GetMapping
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @GetMapping("{id}")
    public User findById(@PathVariable long id) {
        return userStorage.findById(id);
    }

    @GetMapping("{id}/friends")
    public List<User> getFriends(@PathVariable long id) {
        List<User> f = userService.getFriends(id);
        log.debug("The user " + id + " has " + f.size() + " friend");
        return f;
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable long id, @PathVariable long otherId) {
        Collection<User> f = userService.getMutualFriends(id, otherId);
        log.debug("Mutual friends: " + f.size());
        return f;
    }

    @PostMapping
    public User create(@Valid @NotNull @RequestBody User user) {
        userStorage.create(user);
        log.debug("User added: {}", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @NotNull @RequestBody User user) {
        userStorage.update(user);
        log.debug("Updated user information: {}", user);
        return user;
    }

    @PutMapping("{id}/friends/{friendId}")
    public Long addFriends(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
        log.debug("The user " + id + " and user " + friendId + " became friends");
        return id;
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public Long deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
        log.debug("The user " + id + " deleted the friend " + friendId);
        return id;
    }

    @DeleteMapping("{id}")
    public Long delete(@PathVariable long id) {
        userStorage.delete(id);
        log.debug("The user " + id + " delete.");
        return id;
    }

}
