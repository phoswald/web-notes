package phoswald.webnotes;

import java.util.List;

import phoswald.webnotes.entities.Greeting;

/**
 * Facade to work with JPA.
 */
public class GreetingService {

    public GreetingService() { }

    public List<Greeting> list() {
        try(DatastoreAccessor db = new DatastoreAccessor()) {
            return db.findAll(Greeting.class); // select
        }
    }

    public Greeting get(Long id) {
        try(DatastoreAccessor db = new DatastoreAccessor()) {
            return db.find(Greeting.class, id); // select
        }
    }

    public void put(Greeting greeting) {
        try(DatastoreAccessor db = new DatastoreAccessor()) {
            if(greeting.getId() == null) {
                db.getEntityManager().persist(greeting); // insert
            } else {
                db.getEntityManager().merge(greeting); // works for both insert and update
            }
            db.markSuccessful();
        }
    }

    public void delete(Long id) {
        try(DatastoreAccessor db = new DatastoreAccessor()) {
            db.getEntityManager().remove(db.find(Greeting.class, id)); // delete
            db.markSuccessful();
        }
    }
}
