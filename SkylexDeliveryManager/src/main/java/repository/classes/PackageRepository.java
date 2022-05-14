package repository.classes;

import com.google.inject.Inject;
import data.Delivery;
import data.Package;
import org.hibernate.SessionFactory;
import repository.interfaces.IPackageRepository;

import java.util.Date;
import java.util.logging.Level;
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
        _log.log(Level.INFO, "" + delivery + " ... " + selected);
        onePackage.setContent(Content);
        onePackage.setDestination(Destination);
        onePackage.setRegistrationTime(RegistrationTime);
        onePackage.setWeight(weight);
        onePackage.setSelected(selected);
        onePackage.setSelectedProp(selected);
        onePackage.setDelivery(delivery);

        session.beginTransaction();
        session.update(onePackage);
        session.getTransaction().commit();
        session.close();

        return true;
    }
}