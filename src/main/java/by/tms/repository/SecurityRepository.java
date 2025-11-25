package by.tms.repository;

import by.tms.model.Role;
import by.tms.model.Security;
import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import by.tms.model.dto.UserRegistrationDto;
import by.tms.util.SqlList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    @Autowired
    public SecurityRepository(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    public Optional<Security> getSecurityByUsername(String username) {
        Query query = entityManager.createNativeQuery(SqlList.SELECT_SECURITY_BY_USERNAME, Security.class);
        query.setParameter("username", username);
        Object security = query.getSingleResultOrNull();
        return Optional.ofNullable((Security) security);
    }

    //TODO: rollback ?
    public boolean registration(UserRegistrationDto dto) {
        entityManager.getTransaction().begin();

        //TODO: rollback ?
        User user = userRepository.addUser(new UserCreateDto(dto.getFirstName(), dto.getSecondName(), dto.getAge(), dto.getEmail()));

        if (user.getId() == null) {
            return false;
        }

        Security security = new Security();
        security.setUserId(user.getId());
        security.setUsername(dto.getFirstName());
        security.setPassword(dto.getSecondName());
        security.setRole(Role.USER);

        entityManager.persist(security);
        entityManager.getTransaction().commit();
        return true;
    }
}
