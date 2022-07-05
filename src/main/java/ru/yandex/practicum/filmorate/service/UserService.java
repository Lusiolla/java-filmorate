package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.SQLException;
import java.util.*;

@Service
@Component
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public  UserService(@Qualifier("userDB") UserStorage userStorage,
                       @Qualifier("friendDB") FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }
    public void addFriend(long userId, long friendId) throws SQLException {
        userStorage.findById(userId);
        userStorage.findById(friendId);
        friendStorage.add(userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        friendStorage.delete(userId, friendId);
    }

    public List<User> getFriends(long userId) throws SQLException {
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

    public Collection<User> getMutualFriends(long userId, long otherId) throws SQLException {
        userStorage.findById(userId);
        userStorage.findById(otherId);
        Set<User> friends = new HashSet<>();
        Collection<Long> friendsId = friendStorage.getMutualFriends(userId, otherId);
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