package by.tms.repository;

import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final Session session;

    @Autowired
    public UserRepository(Session session) {
        this.session = session;
    }

    public List<User> getAllUsers() {
        return session.createQuery("from users", User.class).getResultList(); //JPQL
    }

    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(session.find(User.class, id));
    }

    public User addUser(UserCreateDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setAge(userDto.getAge());
        user.setCreated(LocalDateTime.now());
        user.setChanged(LocalDateTime.now());

        session.getTransaction().begin();
        session.persist(user);
        session.getTransaction().commit();
        return user;
    }

    public void removeUserById(int id) {
        session.getTransaction().begin();
        session.remove(session.find(User.class, id));
        session.getTransaction().commit();
    }

    public Optional<User> updateUser(User user) {
        Optional<User> userFromDatabase = getUserById(user.getId());
        if (userFromDatabase.isPresent()) {
            session.getTransaction().begin();
            user.setCreated(userFromDatabase.get().getCreated());
            user.setChanged(LocalDateTime.now());
            Optional<User> updatedUser = Optional.ofNullable(session.merge(user));
            session.getTransaction().commit();
            return updatedUser;
        }
        return Optional.empty();
    }
}


