package repository;

import com.google.inject.Inject;
import data.Package;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class PackageRepository extends RepositoryBase<Package> implements IPackageRepository {
    /**
     * Creates a new VehicleRepository instance
     * @param log logger for the repository
     * @param sessionFactory session factory
     */
    @Inject
    public PackageRepository(Logger log, SessionFactory sessionFactory) {
        super(log, sessionFactory);
    }

    @Override
    public boolean Update(int id, String content, int weight, String destination, Delivery delivery, boolean selected)
    {
        var session = _sessionFactory.openSession();
        var onePackage = getById(id);
        if (onePackage == null)
            return false;

        onePackage.setPlateNumber(content);
        onePackage.setInDelivery(weight);
        onePackage.setMaxCapacity(destination);
        onePackage.setCurrentLoad(delivery);
        onePackage.setCurrentLoad(selected);

        session.beginTransaction();
        session.update(onePackage);
        session.getTransaction().commit();
        session.close();

        return true;
    }
}