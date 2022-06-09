package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.FilmNotFoundException;

import java.util.*;

@Component
public class InMemoryLikeStorage implements LikeStorage {

    private Map<Long, Set<Long>> likes = new HashMap<>();

    @Override
    public boolean like(long filmId, long userId) {
        boolean presence = false;
        if (likes.containsKey(filmId)) {
            presence = likes.get(filmId).add(userId);
        } else {
            Set<Long> l = new HashSet<>();
            l.add(userId);
            likes.put(filmId, l);
        }
        return presence;
    }

    @Override
    public void dislike(long filmId, long userId) {
        if (!likes.containsKey(filmId)) {
            throw new FilmNotFoundException();
        }
        likes.get(filmId).remove(userId);
    }
}
