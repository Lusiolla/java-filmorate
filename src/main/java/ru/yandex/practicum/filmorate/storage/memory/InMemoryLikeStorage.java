package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.FilmNotFoundException;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.*;

@Component
public class InMemoryLikeStorage implements LikeStorage {

    private Map<Long, Set<Long>> likes = new HashMap<>();

    @Override
    public void like(long filmId, long userId) {
        if (likes.containsKey(filmId)) {
            likes.get(filmId).add(userId);
        } else {
            Set<Long> l = new HashSet<>();
            l.add(userId);
            likes.put(filmId, l);
        }

    }

    @Override
    public void dislike(long filmId, long userId) {
        if (!likes.containsKey(filmId)) {
            throw new FilmNotFoundException();
        }
        likes.get(filmId).remove(userId);
    }
}
