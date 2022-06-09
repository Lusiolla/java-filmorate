package ru.yandex.practicum.filmorate.storage.friend;

import java.util.Set;

public interface FriendStorage {
    public void add(long userId, long friendId);

    public void delete(long userId, long friendId);

    public Set<Long> findFriends(long id);

    public Set<Long> getMutualFriends(long userId, long otherId);
}
