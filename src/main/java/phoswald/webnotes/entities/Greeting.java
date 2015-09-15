package phoswald.webnotes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;

    @Column
    private String name;

    @Column
    private String text;

    public Key getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
    }
}
