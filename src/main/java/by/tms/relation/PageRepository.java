package by.tms.relation;

import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PageRepository {

    public final SessionFactory sessionFactory;

    public PageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //CREATE
    public void save(Page page) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(page);
        session.getTransaction().commit();
    }

    //DELETE
    public void delete(Page page) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Page> criteriaDelete = cb.createCriteriaDelete(Page.class);
        Root<Page> root = criteriaDelete.from(Page.class);
        criteriaDelete.where(cb.equal(root.get("id"), page.getId()));

        session.beginTransaction();
        session.createMutationQuery(criteriaDelete).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    //READ
    public Page findById(int id) {
        Page page = null;
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Page> cr = cb.createQuery(Page.class);
        Root<Page> root = cr.from(Page.class);
        cr.select(root).where(cb.equal(root.get("id"), id));

        List<Page> results = session.createQuery(cr).getResultList();
        if (results != null && !results.isEmpty()) {
            page = results.get(0);
        }
        session.close();
        return page;
    }

    //READ ALL
    public List<Page> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Page> cr = cb.createQuery(Page.class);
        Root<Page> root = cr.from(Page.class);
        cr.select(root);

        List<Page> results = session.createQuery(cr).getResultList();
        session.close();
        return results;
    }

    //UPDATE
    public void update(Page page) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<Page> criteriaUpdate = cb.createCriteriaUpdate(Page.class);
        Root<Page> root = criteriaUpdate.from(Page.class);
        criteriaUpdate.set("text", page.getText());

        criteriaUpdate.where(cb.equal(root.get("id"), page.getId()));

        session.beginTransaction();
        session.createMutationQuery(criteriaUpdate).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}