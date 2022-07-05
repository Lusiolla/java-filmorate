package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaStorage mpaStorage;

    @GetMapping
    public Collection<Mpa> findAll() {
        return mpaStorage.findAll();
    }

    @GetMapping("{id}")
    public Mpa findById(@PathVariable Long id) {
        Mpa mpa = mpaStorage.findById(id);
        log.info("Найден рейтинг: {} {}", mpa.getId(),
                mpa.getName());
        return mpa;
    }
}
