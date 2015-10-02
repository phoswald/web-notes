package phoswald.webnotes.notes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.LocalDateTime;

import phoswald.webnotes.persistence.LocalDateTimeConverter;

/**
 * The domain class for notes.
 * <p>
 * This class is used for:
 * <ul>
 * <li>JPA (using annotations on fields)
 * <li>JSON serialization (using property accessors)
 * </ul>
 */
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @Column
    private Long userId;

    @Column
    private String name;

    @Column
    private String content;

    @Column
    // @Convert(converter = LocalDateTimeConverter.class)
    private String timestamp; // should be LocalDateTime when JPA 2.1 will work

    public long getNoteId() {
        return noteId == null ? 0 : noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId == 0 ? null : noteId;
    }

    public long getUserId() {
        return userId == null ? 0 : userId;
    }

    public void setUserId(long userId) {
        this.userId = userId == 0 ? null : userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return new LocalDateTimeConverter().convertToEntityAttribute(timestamp);
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = new LocalDateTimeConverter().convertToDatabaseColumn(timestamp);
    }
}
