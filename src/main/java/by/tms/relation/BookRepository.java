package by.tms.relation;

import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    public final SessionFactory sessionFactory;

    public BookRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //CREATE
    public void save(Book book) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(book);
        session.getTransaction().commit();
    }

    //DELETE
    public void delete(Book book) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Book> criteriaDelete = cb.createCriteriaDelete(Book.class);
        Root<Book> root = criteriaDelete.from(Book.class);
        criteriaDelete.where(cb.equal(root.get("id"), book.getId()));

        session.beginTransaction();
        session.createMutationQuery(criteriaDelete).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    //READ
    public Book findById(int id) {
        Book book = null;
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> cr = cb.createQuery(Book.class);
        Root<Book> root = cr.from(Book.class);
        cr.select(root).where(cb.equal(root.get("id"), id));

        List<Book> results = session.createQuery(cr).getResultList();
        if (results != null && !results.isEmpty()) {
            book = results.get(0);
        }
        session.close();
        return book;
    }

    //READ ALL
    public List<Book> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> cr = cb.createQuery(Book.class);
        Root<Book> root = cr.from(Book.class);
        cr.select(root);

        List<Book> results = session.createQuery(cr).getResultList();
        session.close();
        return results;
    }

    //UPDATE
    public void update(Book book) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<Book> criteriaUpdate = cb.createCriteriaUpdate(Book.class);
        Root<Book> root = criteriaUpdate.from(Book.class);
        criteriaUpdate.set("bookName", book.getBookName());

        criteriaUpdate.where(cb.equal(root.get("id"), book.getId()));

        session.beginTransaction();
        session.createMutationQuery(criteriaUpdate).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}