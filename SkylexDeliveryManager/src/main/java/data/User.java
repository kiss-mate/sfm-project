package data;

import javax.persistence.*;

@Entity
@Table(name = "LoginDatabase")
public class User {
    /**
     * Key for a Sample entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * String value for a Sample entity
     */
    @Column(name = "userName")
    private String userName;

    /**
     * String value for a Sample entity
     */
    @Column(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    /**
     * Gets the value1 of a Sample object
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the value2 of a Sample object
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the value1 of a Sample object
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the value2 of a Sample object
     */
    public void setPassword(String password) {
        this.password = password;
    }


    // returns true if USERNAME and PW is set to "admin"
    public boolean isValidLogin_BETA(){
        return this.userName.equals("admin") && this.password.equals("admin");
    }


    // implementation to be done
    public boolean isValidLogin(){
        return false;
    }

}
