package phoswald.webnotes.framework;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class EntityTransaction implements AutoCloseable {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");

    private final EntityManager em;

    private boolean success;

    public EntityTransaction() {
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

    public <T> List<T> findQuery(Class<T> clazz, String string, Object... params) {
        TypedQuery<T> query = em.createQuery(string, clazz);
        for(int i = 0; i < params.length; i++) {
           query.setParameter(i+1, params[i]);
        }
        return query.getResultList();
    }

    public void persist(Object entity) {
        em.persist(entity); // insert
    }

    public <T> T merge(T entity) {
        return em.merge(entity); // works for both insert and update
    }

    public <T> void remove(Class<T> clazz, Object id) {
        T entity = em.find(clazz, id);
        if(entity != null) {
            em.remove(entity); // delete
        }
    }
}
