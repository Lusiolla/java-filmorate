package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
public class Film {
    @NotNull
    private long id;
    @NotBlank
    private final String name;
    @NotBlank
    @Size(min = 1, max = 200)
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @Positive
    private final long duration;
    private int rate;

}
