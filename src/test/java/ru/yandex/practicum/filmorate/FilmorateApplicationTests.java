package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmorateApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    User user1 = new User(0, "test1@ya.ru", "l1", "",
            LocalDate.now().minusMonths(1));
    User user2 = new User(0, "test2@ya.ru", "l2", "n2",
            LocalDate.now().minusMonths(3));

    User user3 = new User(0, "test2@ya.ru", "l3", "n3",
            LocalDate.now().minusMonths(4));
    User user4 = new User(0, "test2@ya.ru", "l4", "n4",
            LocalDate.now().minusMonths(5));

    Film film1 = new Film(0, "f1", "d1", LocalDate.now(), 240, 0);
    Film film2 = new Film(0, "f2", "f2", LocalDate.now(), 240, 0);

    @Test
    public void shouldCheckValidate() throws Exception {
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
        Film film3 = new Film(0, "f2", "f2", LocalDate.now(), -240, 0);
        //пустое описание
        Film film4 = new Film(0, "f4", "", LocalDate.now(), 240, 0);
        //пустое название фильма
        Film film5 = new Film(0, "", "d1", LocalDate.now(), 240, 0);

        this.restTemplate.postForObject("http://localhost:" + port + "/users", user1, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/users", user2, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/users", user3, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/users", user4, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/users", user5, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/users", user6, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/users", user7, String.class);
        this.restTemplate.put("http://localhost:" + port + "/users", user5, String.class);
        //пустой запрос
        this.restTemplate.put("http://localhost:" + port + "/users", null, String.class);

        this.restTemplate.postForObject("http://localhost:" + port + "/films", film1, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/films", film2, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/films", film3, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/films", film4, String.class);
        this.restTemplate.postForObject("http://localhost:" + port + "/films", film5, String.class);
        this.restTemplate.put("http://localhost:" + port + "/films", film5, String.class);
        //пустой запрос
        this.restTemplate.put("http://localhost:" + port + "/films", null, String.class);

        Film filmFromStorage = this.restTemplate.getForObject(("http://localhost:" + port
                + "/films/1"), Film.class);
        //уже существует
        this.restTemplate.postForObject("http://localhost:" + port + "/films", filmFromStorage, String.class);

        final List<User> users = this.restTemplate.getForObject(("http://localhost:" + port + "/users"),
                ArrayList.class);
        final List<Film> films = this.restTemplate.getForObject(("http://localhost:" + port + "/films"),
                ArrayList.class);

        assertNotNull(users, "Пользователи на возвращаются.");
        assertEquals(4, users.size(), "Неверное количество пользователей.");
        assertNotNull(films, "Фильмы на вовращаются.");
        assertEquals(2, films.size(), "Неверное количество фильмов.");

    }

    @Test
    public void shouldAddFriends() {
        this.restTemplate.put("http://localhost:" + port + "/users/1/friends/2", String.class);
        this.restTemplate.put("http://localhost:" + port + "/users/1/friends/3", String.class);
        this.restTemplate.put("http://localhost:" + port + "/users/4/friends/2", String.class);
        final List<Film> userFriends1 = this.restTemplate.getForObject(("http://localhost:" + port
                + "/users/1/friends"), ArrayList.class);
        final List<Film> userFriends2 = this.restTemplate.getForObject(("http://localhost:" + port
                + "/users/2/friends"), ArrayList.class);
        final List<Film> mutualFriends = this.restTemplate.getForObject(("http://localhost:" + port
                + "/users/1/friends/common/2"), ArrayList.class);

        assertEquals(2, userFriends1.size(), "Неверное количество друзей.");
        assertEquals(2, userFriends2.size(), "Неверное количество друзей.");
        assertEquals(0, mutualFriends.size(), "Неверное количество общих друзей.");
    }

    @Test
    public void shouldGetMostPopularFilms() {
        this.restTemplate.put("http://localhost:" + port + "/films/1/like/1", String.class);
        this.restTemplate.put("http://localhost:" + port + "/films/1/like/2", String.class);
        this.restTemplate.put("http://localhost:" + port + "/films/2/like/1", String.class);

        final List<Film> allPopular = this.restTemplate.getForObject(("http://localhost:" + port
                + "/films/popular"), ArrayList.class);
        final List<Film> mostPopular = this.restTemplate.getForObject(("http://localhost:" + port
                + "/films/popular?count=1"), ArrayList.class);

        assertEquals(2, allPopular.size(), "Неверное количество самых " +
                "популярных фильмов.");
        assertEquals(1, mostPopular.size(), "Неверное количество самых " +
                "популярных фильмов.");
    }
}