package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface FriendStorage {
    public void add(long userId, long friendId);

    public void delete(long userId, long friendId);

    public Collection<Long> findFriends(long id);

    public Collection<Long> getMutualFriends(long userId, long otherId);
}
