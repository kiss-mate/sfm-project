package repository;

import com.google.inject.Inject;
import data.Vehicle;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class VehicleRepository extends RepositoryBase<Vehicle> implements IVehicleRepository {
    /**
     * Creates a new VehicleRepository instance
     * @param log logger for the repository
     * @param sessionFactory session factory
     */
    @Inject
    public VehicleRepository(Logger log, SessionFactory sessionFactory) {
        super(log, sessionFactory);
    }

    @Override
    public boolean update(int id, String plateNumber, boolean inDelivery, double maxCapacity, double currentLoad) {
        var session = _sessionFactory.openSession();
        var oneVehicle = getById(id);
        if (oneVehicle == null)
            return false;

        oneVehicle.setPlateNumber(plateNumber);
        oneVehicle.setInDelivery(inDelivery);
        oneVehicle.setMaxCapacity(maxCapacity);
        oneVehicle.setCurrentLoad(currentLoad);

        session.beginTransaction();
        session.update(oneVehicle);
        session.getTransaction().commit();
        session.close();

        return true;
    }
}
