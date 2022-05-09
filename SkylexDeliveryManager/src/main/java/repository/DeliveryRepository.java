package repository;

import com.google.inject.Inject;
import data.Delivery;
import data.Driver;
import data.Vehicle;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class DeliveryRepository extends RepositoryBase<Delivery> implements IDeliveryRepository {
    @Inject
    public DeliveryRepository(Logger log, SessionFactory sessionsFactory) {
        super(log, sessionsFactory);
    }
    @Override
    public boolean update(int id, Driver driver, Vehicle vehicle) {
        var session = _sessionFactory.openSession();
        var delivery = getById(id);
        if (delivery == null)
            return false;

        delivery.setDriver(driver);
        delivery.setVehicle(vehicle);

        session.beginTransaction();
        session.update(delivery);
        session.getTransaction().commit();
        session.close();

        return true;
    }
}
