package by.tms.repository;

import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
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
        return session.createQuery("from users", User.class).getResultList(); //HQL
    }

    public Optional<User> getUserById(int id) {
        Query<User> query = session.createQuery("from users where id = :id", User.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.uniqueResult());
    }

    public User addUser(UserCreateDto userDto) {
        // Не предназначено для добавления строк только для переноса из других таблиц
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
        session.createMutationQuery("delete from users where id = :id").setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
    }

    public Optional<User> updateUser(User user) {
        String hql = "from users where id = :id";
        Optional<User> beforeUpdateUserFromDatabase = Optional.ofNullable(session.createQuery(hql, User.class).setParameter("id", user.getId()).uniqueResult());

        if (beforeUpdateUserFromDatabase.isPresent()) {
            session.getTransaction().begin();
            session.evict(beforeUpdateUserFromDatabase.get()); //Очистка юзера из кэша
            MutationQuery query = session.createMutationQuery("update users set firstName=:firstName, secondName=:secondName, age=:age, email=:email, created=:created, changed=:changed where id=:id");
            query.setParameter("firstName", user.getFirstName());
            query.setParameter("secondName", user.getSecondName());
            query.setParameter("age", user.getAge());
            query.setParameter("email", user.getEmail());
            query.setParameter("created", beforeUpdateUserFromDatabase.get().getCreated());
            query.setParameter("changed", LocalDateTime.now());
            query.setParameter("id", user.getId());
            query.executeUpdate();

            User afterUpdateUserFromDatabase = session.createQuery(hql, User.class).setParameter("id", user.getId()).uniqueResult();
            session.getTransaction().commit();
            return Optional.ofNullable(afterUpdateUserFromDatabase);
        }
        return Optional.empty();
    }
}

