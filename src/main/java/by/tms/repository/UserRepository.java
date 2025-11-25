package by.tms.repository;

import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("from users", User.class).getResultList(); //JPQL
    }

    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public User addUser(UserCreateDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setAge(userDto.getAge());
        user.setCreated(LocalDateTime.now());
        user.setChanged(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public void removeUserById(int id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(User.class, id));
        entityManager.getTransaction().commit();
    }

    public Optional<User> updateUser(User user) {
        Optional<User> userFromDatabase = getUserById(user.getId());
        if (userFromDatabase.isPresent()) {
            entityManager.getTransaction().begin();
            user.setCreated(userFromDatabase.get().getCreated());
            user.setChanged(LocalDateTime.now());
            Optional<User> updatedUser = Optional.ofNullable(entityManager.merge(user));
            entityManager.getTransaction().commit();
            return updatedUser;
        }
        return Optional.empty();
    }
}


