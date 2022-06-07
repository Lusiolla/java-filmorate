package ru.yandex.practicum.filmorate.storage.friend;

import java.util.Set;

public interface FriendStorage {
    public void addFriend(long userId, long friendId);

    public void deleteFriend(long userId, long friendId);

    public Set<Long> getFriends(long userId);

    public Set<Long> getMutualFriends(long userId, long otherId);
}
