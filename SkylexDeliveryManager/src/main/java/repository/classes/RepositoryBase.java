package repository.classes;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import common.constants.SqlConstants;
import org.hibernate.SessionFactory;
import repository.interfaces.IRepositoryBase;

import java.lang.reflect.ParameterizedType;
import java.util.List;
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
     * @exception NullPointerException log and sessionFactory cannot be null
     */
    @Inject
    public RepositoryBase(Logger log, SessionFactory sessionFactory) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_sessionFactory = sessionFactory) == null) throw new ArgumentNullException("sessionFactory");
        _classT = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public int insert(T entity) {
        var session = _sessionFactory.openSession();
        session.beginTransaction();
        var id = session.save(entity);
        session.getTransaction().commit();
        session.close();
        return (int)id;
    }

    @Override
    public List<T> getAll() {
        var session = _sessionFactory.openSession();
        var result = session.createQuery(SqlConstants.From + _classT.getName()).getResultList();
        session.close();
        return result;
    }

    @Override
    public T getById(int id) {
        var session = _sessionFactory.openSession();
        var result = session.get(_classT, id);
        session.close();
        return result;
    }

    @Override
    public boolean delete(int id) {
        var session = _sessionFactory.openSession();

        var oneDriver = getById(id);
        if (oneDriver == null)
            return false;

        delete(oneDriver);
        session.close();

        return true;
    }

    @Override
    public boolean delete(T entity) {
        var session = _sessionFactory.openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();

        return true;
    }
}
