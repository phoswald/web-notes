package phoswald.webnotes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatastoreAccessor implements AutoCloseable {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");

    private final EntityManager em;

    public DatastoreAccessor() {
        em = factory.createEntityManager();
    }

    @Override
    public void close() {
        em.close();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void beginTransaction() {
        em.getTransaction().begin();
    }

    public void commitTransaction() {
        em.getTransaction().commit();
    }
}
