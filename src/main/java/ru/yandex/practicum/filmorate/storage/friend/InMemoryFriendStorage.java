package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.UserFriendNotFoundException;

import java.util.*;

@Component
public class InMemoryFriendStorage implements FriendStorage {

    private HashMap<Long, Set<Long>> friends = new HashMap<>();

    @Override
    public void add(long userId, long friendId) {
        if (friends.containsKey(userId)) {
            friends.get(userId).add(friendId);
        } else {
            Set<Long> f = new HashSet<>();
            f.add(friendId);
            friends.put(userId, f);
        }

        if (friends.containsKey(friendId)) {
            friends.get(friendId).add(userId);
        } else {
            Set<Long> f = new HashSet<>();
            f.add(userId);
            friends.put(friendId, f);
        }
    }

    @Override
    public void delete(long userId, long friendId) {
        if (!friends.containsKey(userId)) {
            throw new UserFriendNotFoundException();
        }
        if (!friends.containsKey(friendId)) {
            throw new UserFriendNotFoundException();
        }
        friends.get(userId).remove(friendId);
        friends.get(friendId).remove(userId);
    }

    @Override
    public Set<Long> findFriends(long userId) {
        if (!friends.containsKey(userId)) {
            return new HashSet<>();
        } else {
            return friends.get(userId);
        }
    }
    
    @Override
    public Set<Long> getMutualFriends(long userId, long otherId) {
        if (!friends.containsKey(userId) && !friends.containsKey(otherId)) {
            return new HashSet<>();
        } else {
            Set<Long> mutualFriends = new HashSet<>(friends.get(userId));
            Set<Long> otherFriends = friends.get(otherId);
            mutualFriends.retainAll(otherFriends);
            return mutualFriends;
        }
    }
}
