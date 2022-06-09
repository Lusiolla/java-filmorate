package ru.yandex.practicum.filmorate.storage.like;

public interface LikeStorage {
    public boolean like(long filmId, long userId);

    public void dislike(long filmId, long userId);
}
