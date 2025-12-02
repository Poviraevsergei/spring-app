package by.tms.repository;

import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.CriteriaDefinition;
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
        //Создание Criteria Builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        //Создание Criteria
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    public Optional<User> getUserById(int id) {
        //Создание Criteria Builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        //Создание Criteria
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));  //SELECT * FROM users WHERE id=?

        Query<User> query = session.createQuery(criteriaQuery);
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
        //Создание Criteria Builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        //Создание Criteria
        CriteriaDelete<User> criteriaQuery = criteriaBuilder.createCriteriaDelete(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        session.getTransaction().begin();
        session.createMutationQuery(criteriaQuery).setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
    }

    public Optional<User> updateUser(User user) {
        //Создание Criteria Builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        //Создание Criteria
        CriteriaQuery<User> getQuery = criteriaBuilder.createQuery(User.class);
        CriteriaUpdate<User> updateQuery = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> rootUpdate = updateQuery.from(User.class);
        Root<User> rootGet = getQuery.from(User.class);

        getQuery.select(rootGet).where(criteriaBuilder.equal(rootGet.get("id"), user.getId()));

        Optional<User> beforeUpdateUserFromDatabase = Optional.ofNullable(session.createQuery(getQuery).uniqueResult());

        if (beforeUpdateUserFromDatabase.isPresent()) {
            session.getTransaction().begin();
            session.evict(beforeUpdateUserFromDatabase.get()); //Очистка юзера из кэша
            updateQuery.set(rootUpdate.get("firstName"), user.getFirstName());
            updateQuery.set(rootUpdate.get("secondName"), user.getSecondName());
            updateQuery.set(rootUpdate.get("age"), user.getAge());
            updateQuery.set(rootUpdate.get("changed"), LocalDateTime.now());
            updateQuery.set(rootUpdate.get("created"), beforeUpdateUserFromDatabase.get().getCreated());
            updateQuery.set(rootUpdate.get("email"), user.getEmail());
            updateQuery.where(criteriaBuilder.equal(rootUpdate.get("id"), user.getId()));

            session.createMutationQuery(updateQuery).executeUpdate();

            User afterUpdateUserFromDatabase = session.createQuery(getQuery).uniqueResult();
            session.getTransaction().commit();
            return Optional.ofNullable(afterUpdateUserFromDatabase);
        }
        return Optional.empty();
    }
}

