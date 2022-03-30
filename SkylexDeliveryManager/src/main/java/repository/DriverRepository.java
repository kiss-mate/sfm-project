package repository;

import com.google.inject.Inject;
import data.Driver;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class DriverRepository extends RepositoryBase<Driver> implements IDriverRepository {
    /**
     * Creates the DriverRepository object
     * @param log            Logger object
     * @param sessionFactory SessionFactory instance
     * @throws NullPointerException log and sessionFactory cannot be null
     */
    @Inject
    public DriverRepository(Logger log, SessionFactory sessionFactory) {
        super(log, sessionFactory);
    }

    @Override
    public void update(int id, String name) {
        var session = _sessionFactory.openSession();
        var oneDriver = getById(id);
        oneDriver.setName(name);

        session.beginTransaction();
        session.update(oneDriver);
        session.getTransaction().commit();

        session.close();
    }
}
