package by.tms.repository;

import by.tms.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final String INSERT_USER_SQL = "INSERT INTO users(id, username, user_password, created, changed, age) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    private static final String SELECT_USERS_SQL = "SELECT * FROM users";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_USER_BY_USERNAME_SQL = "SELECT * FROM users WHERE username = ?";
    private static final String REMOVE_USERS_SQL = "CALL remove_users_by_username(?)";

    private Connection connection;

    @Autowired
    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAllUsers() {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USERS_SQL);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSetToUserList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public User getUserById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSetToUser(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new User();
    }

    public Optional<User> getUserByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USERNAME_SQL);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return Optional.of(parseResultSetToUser(resultSet));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public List<User> parseResultSetToUserList(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
            user.setChanged(resultSet.getTimestamp("changed").toLocalDateTime());
            user.setAge(resultSet.getInt("age"));
            userList.add(user);
        }
        return userList;
    }

    public User parseResultSetToUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        user.setChanged(resultSet.getTimestamp("changed").toLocalDateTime());
        user.setAge(resultSet.getInt("age"));

        return user;
        }
        return new User();
    }

    public int addUser(User user, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL);
        statement.setString(1, user.getUsername());
        statement.setString(2, password);
        statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        statement.setInt(5, user.getAge());

        return statement.executeUpdate();
    }
}


