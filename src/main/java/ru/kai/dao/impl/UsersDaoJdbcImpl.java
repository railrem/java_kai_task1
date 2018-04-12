package ru.kai.dao.impl;

import org.mindrot.jbcrypt.BCrypt;
import ru.kai.dao.UsersDao;
import ru.kai.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 04.04.2018
 * UsersDaoJdbcImpl
 *
 * @version v1.0
 */
public class UsersDaoJdbcImpl implements UsersDao {

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM users";

    //language=SQL
    private final String SQL_SELECT_ALL_BY_LOGIN =
            "SELECT * FROM users WHERE login = ?";
    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM users WHERE id = ?";

    private Connection connection;

    public UsersDaoJdbcImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> find(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = this.formatResultSet(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Integer save(User model) {
        return  -1;
    }


    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<User> findAll() {
        try {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                User user = this.formatResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Integer isExist(String login, String password) {
        for (User user : this.findAllByLogin(login)) {
            if (BCrypt.checkpw(password,user.getPasswordHash())) {
                return user.getId();
            }
        }
        return -1;
    }

    @Override
    public List<User> findAllByLogin(String login) {
        try {

            List<User> users = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = this.formatResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private User formatResultSet(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String login = resultSet.getString("login");
        String passwordHash = resultSet.getString("password_hash");
        LocalDateTime registerDatetime = resultSet.getTimestamp("registered_at").toLocalDateTime();
        return User.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .login(login)
                .passwordHash(passwordHash)
                .registerDatetime(registerDatetime)
                .build();
    }
}
