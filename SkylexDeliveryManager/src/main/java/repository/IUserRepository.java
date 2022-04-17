package repository;

import data.User;

public interface IUserRepository extends IRepositoryBase<User> {
    /**
     * Updates the User object with the given id
     * @param id id of User to update
     * @param username new username for User
     * @param password new password for User
     */
    void update(int id, String username, String password);
}
