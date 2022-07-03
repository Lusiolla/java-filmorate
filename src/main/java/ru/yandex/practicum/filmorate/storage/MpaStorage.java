package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {

    public Mpa findById(long id);

    public Collection<Mpa> findAll();
}
