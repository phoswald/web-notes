package phoswald.webnotes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.LocalDateTime;

@Entity
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // or: com.google.appengine.api.datastore.Key

    @Column
    private String name;

    @Column
    private String text;

    @Column
    // @Convert(converter = LocalDateTimeConverter.class)
    private String date; // should be LocalDateTime when JPA 2.1 will work

    public String getId() {
        return id == null ? "" : id.toString();
    }

    public void setId(Long i) {
        id = i;
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

    public LocalDateTime getDate() {
        return new LocalDateTimeConverter().convertToEntityAttribute(date);
    }

    public void setDate(LocalDateTime d) {
        date = new LocalDateTimeConverter().convertToDatabaseColumn(d);
    }
}
