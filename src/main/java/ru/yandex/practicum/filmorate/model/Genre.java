package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
public class Genre {
    private long id;
    @NotNull
    private String name;
}
