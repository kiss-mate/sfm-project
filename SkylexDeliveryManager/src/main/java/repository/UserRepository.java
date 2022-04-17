package repository;

import data.User;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class UserRepository extends RepositoryBase<User> implements IUserRepository{
    /**
     * Creates the UserRepository object
     *
     * @param log            Logger object
     * @param sessionFactory SessionFactory instance
     * @throws NullPointerException log and sessionFactory cannot be null
     */
    public UserRepository(Logger log, SessionFactory sessionFactory) {
        super(log, sessionFactory);
    }

    @Override
    public void update(int id, String username, String password) {
        var session = _sessionFactory.openSession();

        var oneUser = getById(id);
        oneUser.setUserName(username);
        oneUser.setPassword(password);

        session.beginTransaction();
        session.update(oneUser);
        session.getTransaction().commit();

        session.close();
    }
}
