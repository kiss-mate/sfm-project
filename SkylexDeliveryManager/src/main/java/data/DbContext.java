package data;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides configuration for hibernate and a single SessionFactory instance
 */
public class DbContext {
    private static final Logger _log = Logger.getLogger(DbContext.class.getName());
    private static SessionFactory _sessionFactoryInstance;

    /**
     * Gets the SessionFactory instance's value and if null, configures new SessionFactory
     * @return the SessionFactory instance
     */
    public static SessionFactory getSessionFactory() {
        if(_sessionFactoryInstance == null) {
            try {
                _sessionFactoryInstance = new Configuration()
                        .configure()
                        .addAnnotatedClass(Sample.class)
                        .buildSessionFactory();
            } catch (Exception ex) {
                _log.log(Level.SEVERE, "Error while getting SessionFactory instance" + ex.getMessage());
            }
        }

        _log.log(Level.INFO, "SessionFactory getter", _sessionFactoryInstance);
        return _sessionFactoryInstance;
    }
}
