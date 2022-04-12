package data;

import javax.persistence.*;

@Entity
@Table(name = "LoginDatabase")
public class User {
    /**
     * Key for a User entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Username
     */
    @Column(name = "userName")
    private String userName;

    /**
     * User password
     */
    @Column(name = "password")
    private String password;

    /**
     * Gets the id of a User
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
}
