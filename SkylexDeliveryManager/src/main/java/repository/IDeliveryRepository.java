package repository;

import data.Delivery;
import data.Driver;
import data.Vehicle;

public interface IDeliveryRepository extends IRepositoryBase<Delivery> {
    boolean update(int id, Driver driver, Vehicle vehicle);
}
