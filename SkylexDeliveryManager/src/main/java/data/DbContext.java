package data;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides configuration for hibernate and a single SessionFactory instance
 */
public class DbContext {
    private static final Logger _log = Logger.getLogger(DbContext.class.getName());
    private static DbContext dbContextInstance;
    private SessionFactory _sessionFactoryInstance;

    /**
     * Provides the single DbContext instance which gives a configured SessionFactory
     * @return the DbContext for the project
     */
    public static DbContext getDbContextInstance() {
        if (dbContextInstance == null) {
            dbContextInstance = new DbContext();
        }

        return dbContextInstance;
    }

    /**
     * Gets the SessionFactory from DbContext instance and, if null, configures a new SessionFactory
     * @return the SessionFactory instance
     */
    public SessionFactory getSessionFactory(Map<String, String> settings) {
        if (_sessionFactoryInstance == null) {
            try {
                var serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build();

                var metadataSources = new MetadataSources(serviceRegistry);
                var metadata = metadataSources.buildMetadata();

                _sessionFactoryInstance = metadata
                        .getSessionFactoryBuilder()
                        .build();

            } catch (Exception ex) {
                _log.log(Level.SEVERE, "Error while getting SessionFactory instance" + ex.getMessage());
                throw new RuntimeException("Error while getting SessionFactory instance");
            }
        }
        return _sessionFactoryInstance;
    }

    public void setSessionFactory(SessionFactory sf) {
        _sessionFactoryInstance = sf;
    }
}