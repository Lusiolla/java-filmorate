package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public void create(User newUser) throws RuntimeException {
        userStorage.create(newUser);
    }

    public void update(User user) throws RuntimeException {
        userStorage.update(user);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User findUserById(long id) throws RuntimeException {
        return userStorage.findUserById(id);
    }

    public void addFriend(long userId, long friendId) throws RuntimeException {
        findUserById(userId);
        findUserById(friendId);
        friendStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) throws RuntimeException {
        friendStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(long userId) throws RuntimeException {
        findUserById(userId);
        List<User> friends = new ArrayList<>();
        Collection<Long> friendsId = friendStorage.getFriends(userId);
        if (!friendsId.isEmpty()) {
            for (long id : friendsId) {
                friends.add(findUserById(id));
            }
        }
        return friends;
    }

    public Collection<User> getMutualFriends(long userId, long otherId) throws RuntimeException {
        findUserById(userId);
        findUserById(otherId);
        Set<User> friends = new HashSet<>();
        Set<Long> friendsId = friendStorage.getMutualFriends(userId, otherId);
        if (!friendsId.isEmpty()) {
            for (long id : friendsId) {
                friends.add(findUserById(id));
            }
        }
        return friends;
    }

}