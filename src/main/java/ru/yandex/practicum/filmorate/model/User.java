package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class User {
    private long id;
    @NotBlank
    @Email
    @Pattern(regexp = "^\\S*$")
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

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("username", getName());
        values.put("birthday", birthday);
        return values;
    }
}
