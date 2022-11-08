package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component("userDB")
@RequiredArgsConstructor
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final static String FIND_BY_ID = "select * " +
            "from users " +
            "where user_id = ?";
    private final static String FIND_ALL_USERS = "select * " +
            "from users";
    private final static String UPDATE_USER = "update " +
            "users set " +
            "email = ?, " +
            "login = ?, " +
            "username = ?, " +
            "birthday = ? " +
            "where user_id = ?";
    private final static String DELETE_USER = "delete " +
            "from users " +
            "where user_id = ?";


    @Override
    public void create(User newUser) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        newUser.setId(simpleJdbcInsert.executeAndReturnKey(newUser.toMap()).longValue());
    }

    @Override
    public void update(User user) {
        boolean answ = jdbcTemplate.update(UPDATE_USER,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()) > 0;
        if (!answ) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void delete(long id) {
        if (jdbcTemplate.update(DELETE_USER, id) < 1) {
            log.info("Что-то пошло не так, пользователь {} не был найден", id);
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_USERS, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User findById(long id) throws SQLException {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(FIND_BY_ID, id);
        if (userRows.next()) {
            return new User(
                    userRows.getInt("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("username"),
                    userRows.getDate("birthday").toLocalDate()
            );
        } else {
            throw new UserNotFoundException();
        }
    }

    private User makeUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("username");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return new User(id, email, login, name, birthday);
    }
}

