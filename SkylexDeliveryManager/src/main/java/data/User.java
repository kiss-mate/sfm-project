package data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserData")
public class User implements Serializable {
    /**
     * Key for a User entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private int id;

    /**
     * Username
     */
    @Column(name = "username")
    @JsonProperty("username")
    private String username;

    /**
     * User password
     */
    @Column(name = "password")
    @JsonProperty("password")
    private String password;

    /**
     * Affects the remember-me logic at login
     */
    @Column(name = "rememberUserName")
    @JsonProperty("rememberUserName")
    private boolean rememberUserName;

    public boolean rememberUserName() {
        return rememberUserName;
    }

    public void setDefault(boolean rememberUserName) {
        this.rememberUserName = rememberUserName;
    }

    /**
     * Gets the id of a User
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Converts the User object to a human-readable form
     * @return A string form of User object
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
