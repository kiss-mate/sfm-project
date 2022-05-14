package repository.classes;

import com.google.inject.Inject;
import data.User;
import org.hibernate.SessionFactory;
import repository.interfaces.IUserRepository;

import java.util.logging.Logger;

public class UserRepository extends RepositoryBase<User> implements IUserRepository {
    /**
     * Creates the UserRepository object
     *
     * @param log            Logger object
     * @param sessionFactory SessionFactory instance
     * @throws NullPointerException log and sessionFactory cannot be null
     */
    @Inject
    public UserRepository(Logger log, SessionFactory sessionFactory) {
        super(log, sessionFactory);
    }

    @Override
    public void update(int id, String username, String password, boolean isDefault) {
        var session = _sessionFactory.openSession();

        var oneUser = getById(id);
        oneUser.setUsername(username);
        oneUser.setPassword(password);
        oneUser.setDefault(isDefault);

        session.beginTransaction();
        session.update(oneUser);
        session.getTransaction().commit();

        session.close();
    }
}
