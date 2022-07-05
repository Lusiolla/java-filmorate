package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DbStorageFilmorateTests {
    @LocalServerPort
    private int port;
    private final TestRestTemplate restTemplate;

    @Autowired
    private final UserService userService;

    @Autowired
    private final FilmService filmService;


    @Test
    public void shouldCheckValidate() {
        //пробел в email
        User user5 = new User(0, "test2@ ya.ru", "l2", "n2",
                LocalDate.now().minusMonths(3));
        //пустой логин
        User user6 = new User(0, "test2@ya.ru", "", "n2",
                LocalDate.now().minusMonths(3));

        //дата рождения в будущем
        User user7 = new User(0, "test2@ya.ru", "l5", "n2",
                LocalDate.now().plusMonths(3));

        //отрицательная продолжительность фильма
        Film film3 = new Film(0, "f2", "f2", LocalDate.now(), -240, 0,
                new Mpa(1, null), null);
        //пустое описание
        Film film4 = new Film(0, "f4", "", LocalDate.now(), 240, 0,
                new Mpa(1, null), null);
        //пустое название фильма
        Film film5 = new Film(0, "", "d1", LocalDate.now(), 240, 0,
                new Mpa(1, null), null);

        this.restTemplate.postForObject("http://localhost:" + port + "/users", user5, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/users", user6, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/users", user7, String.class);
        this.restTemplate.put("http://localhost:" + port + "/users", user5, String.class);
        //пустой запрос
        this.restTemplate.put("http://localhost:" + port + "/users", null, String.class);


        this.restTemplate.postForObject("http://localhost:" + port + "/films", film3, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/films", film4, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/films", film5, String.class);
        this.restTemplate.put("http://localhost:" + port + "/films", film5, String.class);
        //пустой запрос
        this.restTemplate.put("http://localhost:" + port + "/films", null, String.class);

        final List<User> users = this.restTemplate.getForObject(("http://localhost:" + port + "/users"),
                ArrayList.class);
        final List<Film> films = this.restTemplate.getForObject(("http://localhost:" + port + "/films"),
                ArrayList.class);

        assertNotNull(users, "Пользователи на возвращаются.");
        assertEquals(3, users.size(), "Неверное количество пользователей.");
        assertNotNull(films, "Фильмы на вовращаются.");
        assertEquals(3, films.size(), "Неверное количество фильмов.");
    }

   @Test
    public void shouldAddFriends() throws SQLException {
        this.restTemplate.put("http://localhost:" + port + "/users/1/friends/2", String.class);
        this.restTemplate.put("http://localhost:" + port + "/users/1/friends/3", String.class);
        this.restTemplate.put("http://localhost:" + port + "/users/2/friends/3", String.class);
        final Collection<User> userFriends1 = userService.getFriends(1);
        final Collection<User> userFriends2 = userService.getFriends(2);
        final Collection<User> mutualFriends = userService.getMutualFriends(1, 2);

        assertEquals(2, userFriends1.size(), "Неверное количество друзей.");
        assertEquals(1, userFriends2.size(), "Неверное количество друзей.");
        assertEquals(1, mutualFriends.size(), "Неверное количество общих друзей.");
    }

    @Test
    public void shouldGetMostPopularFilms() {
        this.restTemplate.put("http://localhost:" + port + "/films/1/like/1", String.class);
        this.restTemplate.put("http://localhost:" + port + "/films/1/like/2", String.class);
        this.restTemplate.put("http://localhost:" + port + "/films/2/like/1", String.class);

        final List<Film> allPopular = filmService.getMostPopular(3);
        final List<Film> mostPopular = filmService.getMostPopular(1);
        Film film1 = this.restTemplate.getForObject("http://localhost:" + port + "/films/1", Film.class);

        assertEquals(3, allPopular.size(), "Неверное количество самых " +
                "популярных фильмов.");
        assertEquals(1, mostPopular.size(), "Неверное количество самых " +
                "популярных фильмов.");
        assertEquals(1, film1.getId(), "Вернулся не самы популярный фильм");
    }
}


