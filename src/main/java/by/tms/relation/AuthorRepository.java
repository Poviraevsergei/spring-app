package by.tms.relation;

import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Repository
public class AuthorRepository {

    public final SessionFactory sessionFactory;

    public AuthorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //CREATE
    public void save(Author author) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(author);
        session.getTransaction().commit();
    }

    //DELETE
    public void delete(Author author) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Author> criteriaDelete = cb.createCriteriaDelete(Author.class);
        Root<Author> root = criteriaDelete.from(Author.class);
        criteriaDelete.where(cb.equal(root.get("id"), author.getId()));

        session.beginTransaction();
        session.createMutationQuery(criteriaDelete).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    //READ
    public Author findById(int id) {
        Author author = null;
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cr = cb.createQuery(Author.class);
        Root<Author> root = cr.from(Author.class);
        cr.select(root).where(cb.equal(root.get("id"), id));

        List<Author> results = session.createQuery(cr).getResultList();
        if (results != null && !results.isEmpty()) {
            author = results.get(0);
        }
        session.close();
        return author;
    }

    //READ ALL
    public List<Author> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cr = cb.createQuery(Author.class);
        Root<Author> root = cr.from(Author.class);
        cr.select(root);

        List<Author> results = session.createQuery(cr).getResultList();
        session.close();
        return results;
    }

    //UPDATE
    public void update(Author author) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<Author> criteriaUpdate = cb.createCriteriaUpdate(Author.class);
        Root<Author> root = criteriaUpdate.from(Author.class);
        criteriaUpdate.set("authorName", author.getAuthorName());

        criteriaUpdate.where(cb.equal(root.get("id"), author.getId()));

        session.beginTransaction();
        session.createMutationQuery(criteriaUpdate).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}