package by.tms.repository;

import by.tms.model.Role;
import by.tms.model.Security;
import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import by.tms.util.SqlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final Connection connection;
    private final int ONE_LINE_FROM_DB = 1;

    @Autowired
    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAllUsers() {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlList.SELECT_USERS_SQL);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSetToUserList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Optional<User> getUserById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlList.SELECT_USER_BY_ID_SQL);
            statement.setInt(1, id);
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
            PreparedStatement statement = connection.prepareStatement(SqlList.INSERT_USER_SQL);
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
            PreparedStatement statement = connection.prepareStatement(SqlList.REMOVE_USER_BY_ID);
            statement.setInt(1, id);
            return statement.executeUpdate() == ONE_LINE_FROM_DB;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updateUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlList.UPDATE_USER_BY_ID);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getId());
            return statement.executeUpdate() == ONE_LINE_FROM_DB;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}


