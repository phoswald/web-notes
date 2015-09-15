package phoswald.webnotes;

import java.util.Date;
import java.util.List;

import phoswald.webnotes.entities.Greeting;

public class GreetingService {

    public GreetingService() { }

    @SuppressWarnings("unchecked")
    public List<Greeting> list() {
        try(DatastoreAccessor db = new DatastoreAccessor()) {
            db.beginTransaction();
            List<Greeting> list = db.getEntityManager().createQuery("select g from Greeting g").getResultList();
            db.commitTransaction();
            return list;
        }
    }

    public void add() {
        try(DatastoreAccessor db = new DatastoreAccessor()) {
            Greeting entity = new Greeting();
            entity.setName("Name");
            entity.setText("Created at " + new Date());
            db.beginTransaction();
            db.getEntityManager().persist(entity);
            db.getEntityManager().flush();
            db.commitTransaction();
        }
    }
}
