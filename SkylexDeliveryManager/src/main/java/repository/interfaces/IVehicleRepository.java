package repository.interfaces;

import data.Vehicle;

public interface IVehicleRepository extends IRepositoryBase<Vehicle> {
    /**
     * Updates a Vehicle object from the database according to the id
     * @param id Id of the vehicle to update
     * @param plateNumber Plate number of vehicle
     * @param inDelivery Is the vehicle taken for delivery?
     * @param maxCapacity Max load capacity of the vehicle (kg)
     * @param loadChange Current load change on the vehicle (kg)
     */
    boolean update(int id, String plateNumber, boolean inDelivery, double maxCapacity, double loadChange);
}