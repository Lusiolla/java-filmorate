package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    private final static String FIND_BY_ID = "select * " +
            "from mpa " +
            "where mpa_id = ?";

    private final static String FIND_ALL = "select * " +
            "from mpa";

    @Override
    public Mpa findById(long id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(FIND_BY_ID, id);
        if (rs.next()) {
            return new Mpa(
                    rs.getInt("mpa_id"),
                    rs.getString("mpa_name")
            );
        } else {
            throw new MpaNotFoundException();
        }
    }

    @Override
    public Collection<Mpa> findAll() {
        return jdbcTemplate.query(FIND_ALL, (rs, rowNum) -> makeMpa(rs));
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        return new Mpa(
                rs.getInt("mpa_id"),
                rs.getString("mpa_name"));
    }
}
