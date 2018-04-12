package ru.kai.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kai.dao.UsersDao;
import ru.kai.models.Event;
import ru.kai.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 04.04.2018
 * UsersDaoJdbcTemplateImpl
 *
 * @version v1.0
 */
public class UsersDaoJdbcTemplateImpl implements UsersDao {

    private JdbcTemplate template;

    private Map<Integer, User> usersMap = new HashMap<>();

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM users";

    //language=SQL
    private final String SQL_SELECT_USER_WITH_EVENT =
            "SELECT users.*, event.id AS event_id, event.name FROM users LEFT JOIN event ON users.id = event.manager_id WHERE users.id = ?";


    //language=SQL
    private final String SQL_INSERT =
            "INSERT INTO users (login,name,surname,password_hash) VALUES(?,?,?,?) ";

    public UsersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper
            = (ResultSet resultSet, int i) -> {
        Integer id = resultSet.getInt("id");
        User user = null;

        if (!usersMap.containsKey(id)) {
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String login = resultSet.getString("login");
            String passwordHash = resultSet.getString("password_hash");
            LocalDateTime registerDatetime = resultSet.getTimestamp("register_at").toLocalDateTime();
            user = User.builder()
                    .id(id)
                    .name(name)
                    .surname(surname)
                    .login(login)
                    .passwordHash(passwordHash)
                    .registerDatetime(registerDatetime)
                    .build();
            usersMap.put(id, user);
        } else {
            user = usersMap.get(id);
        }

        Event event = Event.builder()
                .id(resultSet.getInt("event_id"))
                .name(resultSet.getString("name"))
                .manager(user)
                .build();

        usersMap.get(id).getEvents().add(event);

        return usersMap.get(id);
    };

    @Override
    public Optional<User> find(Integer id) {
        template.query(SQL_SELECT_USER_WITH_EVENT, userRowMapper, id);

        if (usersMap.containsKey(id)) {
            return Optional.of(usersMap.get(id));
        }
        return Optional.empty();
    }

    @Override
    public Integer save(User model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                    ps.setString(1, model.getLogin());
                    ps.setString(2, model.getName());
                    ps.setString(3, model.getSurname());
                    ps.setString(4, model.getPasswordHash());

                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<User> findAll() {
        return template.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void update(User model) {
    }

    @Override
    public void delete(Integer id) {
    }


    @Override
    public Integer isExist(String login, String password) {
        return -1;
    }

    @Override
    public List<User> findAllByLogin(String login) {
        return null;
    }
}
