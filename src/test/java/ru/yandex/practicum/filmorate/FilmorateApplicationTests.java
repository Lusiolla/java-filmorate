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

	@Test
	public void shouldCheckValidate() throws Exception {
		User user1 = new User(1, "test1@ya.ru", "l1", "",
				LocalDate.now().minusMonths(1));
		User user2 = new User(2, "test2@ya.ru", "l2", "n2",
				LocalDate.now().minusMonths(3));

		//пробел в email
		User user3 = new User(3, "test2@ ya.ru", "l2", "n2",
				LocalDate.now().minusMonths(3));
		//пустой логин
		User user4 = new User(4, "test2@ya.ru", "", "n2",
				LocalDate.now().minusMonths(3));

		//дата рождения в будущем
		User user5 = new User(2, "test2@ya.ru", "l5", "n2",
				LocalDate.now().plusMonths(3));


		Film film1 = new Film(1, "f1", "d1", LocalDate.now(), 240);
		Film film2 = new Film(2, "f2", "f2", LocalDate.now(), 240);

		//отрицательная продолжительность фильма
		Film film3 = new Film(3, "f2", "f2", LocalDate.now(), -240);
		//пустое описание
		Film film4 = new Film(4, "f4", "", LocalDate.now(), 240);
		//пустое название фильма
		Film film5 = new Film(1, "", "d1", LocalDate.now(), 240);

		this.restTemplate.postForObject("http://localhost:" + port + "/users", user1, String.class);
		this.restTemplate.postForObject("http://localhost:" + port + "/users", user2, String.class);
		this.restTemplate.postForObject("http://localhost:" + port + "/users", user3, String.class);
		this.restTemplate.postForObject("http://localhost:" + port + "/users", user4, String.class);
		this.restTemplate.put("http://localhost:" + port + "/users", user5, String.class);
		//пустой запрос
		this.restTemplate.put("http://localhost:" + port + "/users", null, String.class);

		this.restTemplate.postForObject("http://localhost:" + port + "/films", film1, String.class);
		this.restTemplate.postForObject("http://localhost:" + port + "/films", film2, String.class);
		this.restTemplate.postForObject("http://localhost:" + port + "/films", film3, String.class);
		this.restTemplate.postForObject("http://localhost:" + port + "/films", film4, String.class);
		this.restTemplate.put("http://localhost:" + port + "/films", film5, String.class);
		//пустой запрос
		this.restTemplate.put("http://localhost:" + port + "/films", null, String.class);

		final List<User> users = this.restTemplate.getForObject(("http://localhost:" + port + "/users"),
				ArrayList.class);
		final List<Film> films = this.restTemplate.getForObject(("http://localhost:" + port + "/users"),
				ArrayList.class);

		assertNotNull(users, "Задачи на возвращаются.");
		assertEquals(2, users.size(), "Неверное количество задач.");
		assertNotNull(films, "Задачи на возвращаются.");
		assertEquals(2, films.size(), "Неверное количество задач.");

	}
}