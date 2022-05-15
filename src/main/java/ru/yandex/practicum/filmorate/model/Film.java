package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
public class Film {
    @NotNull
    private final long id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @NotNull
    LocalDate releaseDate;
    @Positive
    long duration;
}
