package by.tms.repository;

import by.tms.model.Role;
import by.tms.model.Security;
import by.tms.model.User;
import by.tms.model.dto.UserRegistrationDto;
import by.tms.util.SqlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class SecurityRepository {
    private final Connection connection;

    @Autowired
    public SecurityRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<Security> getSecurityByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(SqlList.SELECT_SECURITY_BY_USERNAME);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSetToSecurity(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Security> parseResultSetToSecurity(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Security security = new Security();
            security.setId(resultSet.getInt("id"));
            security.setUsername(resultSet.getString("username"));
            security.setPassword(resultSet.getString("password"));
            security.setUserId(resultSet.getInt("user_id"));
            security.setRole(Role.valueOf(resultSet.getString("role")));

            return Optional.of(security);
        }
        return Optional.empty();
    }

    public boolean registration(UserRegistrationDto dto) {
        int userId = -1;
        try {
            connection.setAutoCommit(false);
            PreparedStatement userStatement = connection.prepareStatement(SqlList.INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, dto.getFirstName());
            userStatement.setString(2, dto.getSecondName());
            userStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            userStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            userStatement.setInt(5, dto.getAge());
            userStatement.setString(6, dto.getEmail());
            int affectedRows = userStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = userStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
            }

            if (userId == -1) {
                connection.rollback();
                return false;
            }

            PreparedStatement securityStatement = connection.prepareStatement(SqlList.INSERT_SECURITY_SQL);
            securityStatement.setString(1, dto.getPassword());
            securityStatement.setString(2, Role.USER.toString());
            securityStatement.setInt(3, userId);
            securityStatement.setString(4, dto.getUsername());
            affectedRows = securityStatement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
