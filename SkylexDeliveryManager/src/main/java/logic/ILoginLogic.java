package logic;

import data.User;

import java.util.List;

public interface ILoginLogic {
    // add, change, get, getAll, delete

    /**
     * Creates a new user and stores it in the database
     * @param username username for User
     * @param password password for User
     */
    void addUser(String username, String password);

    /**
     * Changes properties of one user get by the id
     * @param id id of the User object
     * @param username possible new username
     * @param password possible new pasword
     */
    void changeOneUser(int id, String username, String password);

    /**
     * Loads one User object from the database, by the id
     * @param id id of the User object
     * @return the user or null if not found
     */
    User getOneUser(int id);

    /**
     * Gets all existing users from the database
     * @return list of User objects
     */
    List<User> getAllUsers();

    /**
     * Deletes one User object with the given id from the database
     * @param id id of the user we want to delete
     */
    void deleteUser(int id);
}
