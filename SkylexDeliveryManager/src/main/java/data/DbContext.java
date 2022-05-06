package data;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.transaction.Synchronization;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides configuration for hibernate and a single SessionFactory instance
 */
public class DbContext {
    private DbContext() {}
    private static final Logger _log = Logger.getLogger(DbContext.class.getName());
    private static DbContext dbContextInstance;
    private SessionFactory sessionFactoryInstance;

    /**
     * Provides the single DbContext instance which gives a configured SessionFactory
     * Lazy loading
     * @return the DbContext for the project
     */
    public static synchronized DbContext getDbContextInstance() {
        if (dbContextInstance == null) {
            dbContextInstance = new DbContext();
        }

        return dbContextInstance;
    }

    /**
     * Gets the SessionFactory from DbContext instance and, if null, configures a new SessionFactory
     * Lazy loading
     * @return the SessionFactory instance
     */
    public synchronized SessionFactory getSessionFactory(Map<String, String> settings) {
        if (sessionFactoryInstance == null) {
            try {
                var serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build();

                var metadataSources = new MetadataSources(serviceRegistry);

                // add annotated classes
                metadataSources.addAnnotatedClass(Driver.class);
                metadataSources.addAnnotatedClass(Vehicle.class);
                metadataSources.addAnnotatedClass(User.class);

                var metadata = metadataSources.buildMetadata();

                sessionFactoryInstance = metadata
                        .getSessionFactoryBuilder()
                        .build();

            } catch (Exception ex) {
                _log.log(Level.SEVERE, "Error while getting SessionFactory instance" + ex.getMessage());
                throw new RuntimeException("Error while getting SessionFactory instance");
            }
        }
        return sessionFactoryInstance;
    }

    public void setSessionFactory(SessionFactory sf) {
        sessionFactoryInstance = sf;
    }
}