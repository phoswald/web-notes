package phoswald.webnotes.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The domain class for users.
 * <p>
 * This class is used for:
 * <ul>
 * <li>JPA (using annotations on fields)
 * <li>JSON serialization (using property accessors)
 * <li>HTTP session state (requires Serializable)
 * </ul>
 */
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private Boolean active;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActive() {
        return active == null ? false : active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
