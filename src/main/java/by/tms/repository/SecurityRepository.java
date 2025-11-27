package by.tms.repository;

import by.tms.model.Role;
import by.tms.model.Security;
import by.tms.model.User;
import by.tms.model.dto.UserRegistrationDto;
import by.tms.util.SqlList;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Repository
public class SecurityRepository {

    private final Session session;

    @Autowired
    public SecurityRepository(Session session) {
        this.session = session;
    }

    public Optional<Security> getSecurityByUsername(String username) {
        Query query = session.createNativeQuery(SqlList.SELECT_SECURITY_BY_USERNAME, Security.class);
        query.setParameter("username", username);
        Object security = query.getSingleResultOrNull();
        return Optional.ofNullable((Security) security);
    }

    public boolean registration(UserRegistrationDto dto) {
        try {
            log.info("Registering user {}", dto.getUsername());
            session.getTransaction().begin();
            User user = new User();
            user.setEmail(dto.getEmail());
            user.setFirstName(dto.getFirstName());
            user.setSecondName(dto.getSecondName());
            user.setAge(dto.getAge());
            user.setCreated(LocalDateTime.now());
            user.setChanged(LocalDateTime.now());
            session.persist(user);

            if (user.getId() == null) {
                session.getTransaction().rollback();
                return false;
            }

            Security security = new Security();
            security.setUserId(user.getId());
            security.setUsername(dto.getUsername());
            security.setPassword(dto.getPassword());
            security.setRole(Role.USER);

            session.persist(security);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
            return false;
        }
    }
}
