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

    public void delete(long id) {
        userStorage.delete(id);
    }

    public void addFriend(long userId, long friendId) {
        userStorage.findById(userId);
        userStorage.findById(friendId);
        friendStorage.add(userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        friendStorage.delete(userId, friendId);
    }

    public List<User> getFriends(long userId) {
        userStorage.findById(userId);
        List<User> friends = new ArrayList<>();
        Collection<Long> friendsId = friendStorage.findFriends(userId);
        try {
            if (!friendsId.isEmpty()) {
                for (long id : friendsId) {
                    friends.add(userStorage.findById(id));
                }
            }
        } catch (RuntimeException e) {
            //ignore
        }
        return friends;
    }

    public Collection<User> getMutualFriends(long userId, long otherId) {
        userStorage.findById(userId);
        userStorage.findById(otherId);
        Set<User> friends = new HashSet<>();
        Set<Long> friendsId = friendStorage.getMutualFriends(userId, otherId);
        try {
            if (!friendsId.isEmpty()) {
                for (long id : friendsId) {
                    friends.add(userStorage.findById(id));
                }
            }
        } catch (RuntimeException e) {
            //ignore
        }
        return friends;
    }
}