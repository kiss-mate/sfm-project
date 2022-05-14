package repository.classes;

import com.google.inject.Inject;
import data.Vehicle;
import org.hibernate.SessionFactory;
import repository.interfaces.IVehicleRepository;

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
    public boolean update(int id, String plateNumber, boolean inDelivery, double maxCapacity, double loadChange) {
        var session = _sessionFactory.openSession();
        var oneVehicle = getById(id);
        if (oneVehicle == null)
            return false;

        oneVehicle.setPlateNumber(plateNumber);
        oneVehicle.setInDelivery(inDelivery);
        oneVehicle.setInDeliveryProp(inDelivery);
        oneVehicle.setMaxCapacity(maxCapacity);
        oneVehicle.setCurrentLoad(oneVehicle.getCurrentLoad() + loadChange);

        session.beginTransaction();
        session.update(oneVehicle);
        session.getTransaction().commit();
        session.close();

        return true;
    }
}
