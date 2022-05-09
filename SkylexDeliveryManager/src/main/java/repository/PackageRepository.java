package repository;

import com.google.inject.Inject;
import data.Delivery;
import data.Package;
import org.hibernate.SessionFactory;

import java.util.Date;
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
    public boolean update(int id, String Content, String Destination, Date RegistrationTime, double weight, Delivery delivery, boolean selected)
    {
        var session = _sessionFactory.openSession();
        var onePackage = getById(id);
        if (onePackage == null)
            return false;

        onePackage.setContent(Content);
        onePackage.setDestination(Destination);
        onePackage.setRegistrationTime(RegistrationTime);
        onePackage.setWeight(weight);
        onePackage.setSelected(selected);
        onePackage.setDelivery(delivery);

        session.beginTransaction();
        session.update(onePackage);
        session.getTransaction().commit();
        session.close();

        return true;
    }
}