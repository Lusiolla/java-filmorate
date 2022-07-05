package ru.yandex.practicum.filmorate.storage;

public interface LikeStorage {
    public void like(long filmId, long userId);

    public void dislike(long filmId, long userId);
}
