package phoswald.webnotes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class DatastoreAccessor implements AutoCloseable {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");

    private final EntityManager em;

    private boolean success;

    public DatastoreAccessor() {
        em = factory.createEntityManager();
        em.getTransaction().begin();
    }

    @Override
    public void close() {
        if(success) {
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
        }
        em.close();
    }

    public void markSuccessful() {
        success = true;
    }

    public <T> T find(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(clazz);
        return em.createQuery(query.select(query.from(clazz))).getResultList();
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
