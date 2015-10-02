package phoswald.webnotes;

import java.util.List;

import phoswald.webnotes.entities.Greeting;
import phoswald.webnotes.persistence.EntityTransaction;

/**
 * Facade to work with JPA.
 */
@Deprecated
public class GreetingService {

    public GreetingService() { }

    public List<Greeting> list() {
        try(EntityTransaction db = new EntityTransaction()) {
            return db.findAll(Greeting.class); // select
        }
    }

    public Greeting get(Long id) {
        try(EntityTransaction db = new EntityTransaction()) {
            return db.find(Greeting.class, id); // select
        }
    }

    public void put(Greeting greeting) {
        try(EntityTransaction db = new EntityTransaction()) {
            if(greeting.getId() == null) {
                db.persist(greeting); // insert
            } else {
                db.merge(greeting); // works for both insert and update
            }
            db.markSuccessful();
        }
    }

    public void delete(Long id) {
        try(EntityTransaction db = new EntityTransaction()) {
            db.remove(Greeting.class, id);
            db.markSuccessful();
        }
    }
}
