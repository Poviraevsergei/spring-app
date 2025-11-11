package by.tms.repository;

import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
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
    private static final String INSERT_USER_SQL = "INSERT INTO users(id, first_name, second_name, created, changed, age, email) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USERS_SQL = "SELECT * FROM users";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_USER_BY_USERNAME_SQL = "SELECT * FROM users WHERE username = ?";
    private static final String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

    private Connection connection;
    private final int ONE_LINE_FROM_DB = 1;

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

    public Optional<User> getUserById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSetToUser(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> getUserByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USERNAME_SQL);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSetToUser(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public List<User> parseResultSetToUserList(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            userList.add(fillUser(resultSet));
        }
        return userList;
    }

    public Optional<User> parseResultSetToUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(fillUser(resultSet));
        }
        return Optional.empty();
    }

    public boolean addUser(UserCreateDto user) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            statement.setInt(5, user.getAge());
            statement.setString(6, user.getEmail());

            return statement.executeUpdate() == ONE_LINE_FROM_DB;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public User fillUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setAge(resultSet.getInt("age"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setEmail(resultSet.getString("email"));
        user.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        user.setChanged(resultSet.getTimestamp("changed").toLocalDateTime());
        return user;
    }

    public boolean removeUserById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_USER_BY_ID);
            statement.setInt(1, id);
            return statement.executeUpdate() == ONE_LINE_FROM_DB;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}


