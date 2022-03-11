package repository;

import com.google.inject.Inject;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @inheritDoc
 */
public class RepositoryBase<T> implements IRepositoryBase<T> {
    /**
     * Helps the generic data access. Please ignore IDE warnings
     * @see "https://stackoverflow.com/a/3403976"
     */
    private final Class<T> _classT;
    protected final Logger _log;
    protected final SessionFactory _sessionFactory;

    /**
     * Creates the RepositoryBase object
     * @param log Logger object
     * @param sessionFactory SessionFactory instance
     * @exception NullPointerException
     */
    @Inject
    public RepositoryBase(Logger log, SessionFactory sessionFactory) {
        if ((_log = log) == null) throw new NullPointerException("log");
        if ((_sessionFactory = sessionFactory) == null) throw new NullPointerException("sessionFactory");
        _classT = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void insert(T entity) {
        try {
            var session = _sessionFactory.openSession();
            session.save(entity);
            session.close();
        } catch (HibernateException ex) {
            _log.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public List<T> getAll() {
        var session = _sessionFactory.openSession();

        // Todo: Implement method

        session.close();
        return null;
    }

    @Override
    public T getById(int id) {
        var session = _sessionFactory.openSession();

        // Todo: Implement method

        session.close();
        return null;
    }

    @Override
    public void delete(int id) {
        var session = _sessionFactory.openSession();

        // Todo: Implement method

        session.close();
    }

    @Override
    public void delete(T entity) {
        var session = _sessionFactory.openSession();

        // Todo: Implement method

        session.close();
    }
}
