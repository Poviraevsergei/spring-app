package by.tms.util;

public interface SqlList {
    String INSERT_USER_SQL = "INSERT INTO users(id, first_name, second_name, created, changed, age, email) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    String SELECT_USERS_SQL = "SELECT * FROM users";
    String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    String SELECT_SECURITY_BY_USERNAME = "SELECT * FROM security WHERE username = ?";
    String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    String UPDATE_USER_BY_ID = "UPDATE users SET first_name = ?, second_name = ?, age = ?, email = ?, changed = NOW() WHERE id = ?";

}
