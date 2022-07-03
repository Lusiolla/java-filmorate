package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Data
@AllArgsConstructor
public class Film {
    private long id;
    @NotBlank
    @NotNull
    private final String name;
    @NotBlank
    @NotNull
    @Size(min = 1, max = 200)
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @Positive
    private final long duration;
    private int rate;
    @NotNull
    private final Mpa mpa;
    private Set<Genre> genres;



    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("film_name", name);
        values.put("description", description);
        values.put("release_date", releaseDate);
        values.put("duration", duration);
        values.put("rate", rate);
        values.put("mpa_id", mpa.getId());

        return values;
    }
}
