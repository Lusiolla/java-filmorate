package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;


@Data
@AllArgsConstructor
public class User {
    @NonNull
    private final long id;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String login;
    private final String name;
    @NotNull
    @Past
    private final LocalDate birthday;

    public String getName() {
        if (name.isBlank()) {
            return login;
        } else {
            return name;
        }
    }
}
