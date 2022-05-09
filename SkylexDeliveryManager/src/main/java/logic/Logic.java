package logic;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import common.exceptions.BusinessException;
import data.Delivery;
import data.Driver;
import data.Vehicle;
import data.Package;
import models.enums.ErrorCodes;
import repository.IDeliveryRepository;
import repository.IDriverRepository;
import repository.IPackageRepository;
import repository.IVehicleRepository;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic implements ILogic {
    private final Logger _log;
    private final IDriverRepository _driverRepo;
    private final IVehicleRepository _vehicleRepo;
    private final IPackageRepository _packageRepo;
    private final IDeliveryRepository _deliveryRepo;

    /**
     * Creates Logic object for the project
     * @param log Logger log
     * @exception NullPointerException log cannot be null
     * @exception NullPointerException driverRepo cannot be null
     * @exception NullPointerException vehicleRepo cannot be null
     */
    @Inject
    public Logic(Logger log, IDriverRepository driverRepo, IVehicleRepository vehicleRepo, IPackageRepository packageRepo, IDeliveryRepository deliveryRepo) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_driverRepo = driverRepo) == null) throw new ArgumentNullException("driverRepo");
        if ((_vehicleRepo = vehicleRepo) == null) throw new ArgumentNullException("vehicleRepo");
        if ((_packageRepo = packageRepo) == null) throw new ArgumentNullException("packageRepo");
        if ((_deliveryRepo = deliveryRepo) == null) throw new ArgumentNullException("deliveryRepo");
    }

    //region DRIVER RELATED LOGIC
    @Override
    public Driver addDriver(String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new BusinessException("Driver name was null or whitespace", ErrorCodes.DRIVER_NAME_EMPTY_OR_NULL, "{name="+name+"}");

        var newDriver = new Driver();
        newDriver.setName(name);
        newDriver.setInDelivery(false);
        _driverRepo.insert(newDriver);
        _log.log(Level.INFO, "New Driver object added to the database: " + newDriver);
        return newDriver;
    }

    @Override
    public void changeOneDriver(int id, String name, boolean inDelivery) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new BusinessException("Driver name was null or whitespace", ErrorCodes.DRIVER_NAME_EMPTY_OR_NULL);
        var driver = _driverRepo.getById(id);
        if (driver == null)
            throw new BusinessException("Driver object not found", ErrorCodes.DRIVER_NOT_FOUND);

        _driverRepo.update(id, name, inDelivery);
        _log.log(Level.INFO, "Updated Driver object: " + driver);
    }

    @Override
    public Driver getOneDriver(int id) {
        return _driverRepo.getById(id);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return _driverRepo.getAll();
    }

    @Override
    public boolean deleteDriver(int id) {
        var driver = _driverRepo.getById(id);
        if (driver == null)
            throw new BusinessException("Driver object not found", ErrorCodes.DRIVER_NOT_FOUND);
      
        if (_driverRepo.delete(driver)) {
            _log.log(Level.INFO, "Removed Driver object from the database: " + driver);
            return true;
        } else {
            _log.log(Level.WARNING, "Remove failed");
            return false;
        }
    }
  
    //endregion

    //region VEHICLE RELATED LOGIC
  
    @Override
    public List<Vehicle> getAllVehicles()
    {
        return _vehicleRepo.getAll();
    }

    @Override
    public Vehicle getOneVehicle(int id)
    {
        return _vehicleRepo.getById(id);
    }

    @Override
    public void addVehicle(String plateNumber, double maxCapacity)
    {
        if (plateNumber == null || plateNumber.isBlank() || plateNumber.isEmpty())
            throw new BusinessException("Vehicle plate number was null or whitespace");
        if (maxCapacity <= 0)
            throw new BusinessException("Capacity is zero or negative");

        var newVehicle = new Vehicle();
        newVehicle.setPlateNumber(plateNumber);
        newVehicle.setMaxCapacity(maxCapacity);
        newVehicle.setInDelivery(false);
        newVehicle.setCurrentLoad(0);
        _vehicleRepo.insert(newVehicle);
        _log.log(Level.INFO, "New Vehicle object added to the database: " + newVehicle);
    }

    @Override
    public void changeOneVehicle(int id, String plateNumber, double maxCapacity, double currentLoad, boolean inDelivery)
    {
        Vehicle vehicle = _vehicleRepo.getById(id);
        if (vehicle == null) throw new BusinessException("No such vehicle");
        if (vehicle.isInDelivery())
        {
            // Vehicle in delivery, can't change capacity or plateNumber
            if (maxCapacity != vehicle.getMaxCapacity() || !plateNumber.equals(vehicle.getPlateNumber()))
            {
                throw new BusinessException("Cannot change capacity or plate number of vehicle in delivery");
            }

            // Vehicle in delivery but we either take it out or changing packages
            _vehicleRepo.update(id, vehicle.getPlateNumber(), inDelivery, vehicle.getMaxCapacity(), currentLoad);
        }
        else
        {
            // Vehicle not in delivery, everything can change but its current load has to stay 0
            _vehicleRepo.update(id, plateNumber, inDelivery, maxCapacity, 0);
        }
    }

    @Override
    public boolean deleteVehicle(int id)
    {
        Vehicle vehicle = _vehicleRepo.getById(id);
        if (vehicle == null)
            throw new BusinessException("Cannot find vehicle, unable to remove it");
        if(vehicle.isInDelivery())
            throw new BusinessException("Cannot delete vehicle in delivery!");

        if (_vehicleRepo.delete(vehicle))
        {
            _log.log(Level.INFO, "Remove successful");
            return true;
        } else {
            _log.log(Level.WARNING, "Remove failed");
            return false;
        }
    }

    // endregion

    // region DELIVERY RELATED LOGIC

    @Override
    public List<Delivery> getAllDeliveries() {
        return _deliveryRepo.getAll();
    }

    @Override
    public Delivery getOneDelivery(int id) {
        return _deliveryRepo.getById(id);
    }

    @Override
    public void addDelivery(Driver driver, Vehicle vehicle) {
        if (driver == null)
            throw new BusinessException("Driver not selected for delivery!");
        if (vehicle == null)
            throw  new BusinessException("Vehicle not selected for delivery!");
        if (driver.isInDelivery())
            throw new BusinessException("Driver already in other delivery!");
        if (vehicle.isInDelivery())
            throw new BusinessException("Vehicle already in other delivery!");

        var newDelivery = new Delivery();
        newDelivery.setDriver(driver);
        newDelivery.setVehicle(vehicle);

        _deliveryRepo.insert(newDelivery);
        _driverRepo.update(driver.getId(), driver.getName(), true);
        _vehicleRepo.update(vehicle.getId(), vehicle.getPlateNumber(), true, vehicle.getMaxCapacity(), 0);
    }

    @Override
    public void changeOneDelivery(int id, Driver driver, Vehicle vehicle) {
        var delivery = _deliveryRepo.getById(id);
        if (delivery == null)
            throw new BusinessException("Cannot find delivery in database");
        if (driver == null)
            throw new BusinessException("Driver not selected for delivery!");
        if (vehicle == null)
            throw  new BusinessException("Vehicle not selected for delivery!");
        if (driver.isInDelivery() && delivery.getDriver().getId() != driver.getId())
            throw new BusinessException("Driver already in other delivery!");
        if (vehicle.isInDelivery() && delivery.getVehicle().getId() != vehicle.getId())
            throw new BusinessException("Vehicle already in other delivery!");

        double loadToTransfer = delivery.getVehicle().getCurrentLoad();
        if (loadToTransfer  > vehicle.getMaxCapacity())
            throw new BusinessException("Cannot transfer load to the new vehicle");

        _driverRepo.update(delivery.getDriver().getId(), delivery.getDriver().getName(), false);
        _vehicleRepo.update(
                delivery.getVehicle().getId(),
                delivery.getVehicle().getPlateNumber(),
                false,
                delivery.getVehicle().getMaxCapacity(),
                -loadToTransfer);

        _deliveryRepo.update(delivery.getId(), driver, vehicle);

        _driverRepo.update(driver.getId(), driver.getName(), true);
        _vehicleRepo.update(vehicle.getId(), vehicle.getPlateNumber(), true, vehicle.getMaxCapacity(), loadToTransfer);
    }

    @Override
    public boolean deleteDelivery(int id) {
        var delivery = _deliveryRepo.getById(id);
        if (delivery == null)
            throw new BusinessException("No such delivery, cannot remove");

        var driver = delivery.getDriver();
        var vehicle = delivery.getVehicle();
        var packages = delivery.getPackages();

        if (_deliveryRepo.delete(delivery)) {
            _driverRepo.update(driver.getId(), driver.getName(), false);
            _vehicleRepo.update(
                    vehicle.getId(),
                    vehicle.getPlateNumber(),
                    false,
                    vehicle.getMaxCapacity(),
                    -vehicle.getCurrentLoad()
            );

            for (var p : packages) {
                _packageRepo.update(p.getid(), p.getContent(), p.getDestination(), p.getRegistrationTime(), p.getWeight(), null, false);
            }

            return true;
        } else {
            return false;
        }
    }

    //endregion

    //region PACKAGE RELATED LOGIC
  
    @Override
    public List<Package> getAllPackages()
    {
        return _packageRepo.getAll();
    }

    @Override
    public Package getOnePackage(int id)
    {
        return _packageRepo.getById(id);
    }

    @Override
    public void addPackage(String content, String destination, double weight)
    {
        if (content == null || content.isBlank() || content.isEmpty())
            throw new BusinessException("Package content was null or whitespace", ErrorCodes.PACKAGE_CONTENT_EMPTY_OR_NULL);
        if (destination == null || destination.isBlank() || destination.isEmpty())
            throw new BusinessException("Package content was null or whitespace", ErrorCodes.PACKAGE_DESTINATION_EMPTY_OR_NULL);
        if (weight <= 0)
            throw new BusinessException("Weight is zero or negative", ErrorCodes.PACKAGE_INVALID_WEIGHT);

        var newPackage = new Package();
        newPackage.setContent(content);
        newPackage.setWeight(weight);
        newPackage.setDestination(destination);
        newPackage.setSelected(false);
        newPackage.setDelivery(null);
        newPackage.setRegistrationTime(new Date());
        _packageRepo.insert(newPackage);

        _log.log(Level.INFO, "New Package object added to the database: " + newPackage);
    }

    @Override
    public void changeOnePackage(int id, String content, String destination, double weight, Delivery delivery, boolean inSelection) {
        Package pack = _packageRepo.getById(id);
        if (pack == null)
            throw new BusinessException("No such package", ErrorCodes.VEHICLE_NOT_FOUND);

        var vehicle = pack.getDelivery().getVehicle();

        if (pack.isSelected() && !inSelection) {
            //take it out from delivery
            _packageRepo.update(pack.getid(), pack.getContent(), pack.getDestination(), pack.getRegistrationTime(), pack.getWeight(), null, false);
            _vehicleRepo.update(vehicle.getId(), vehicle.getPlateNumber(), vehicle.isInDelivery(), vehicle.getMaxCapacity(), -pack.getWeight());
        }

        if (!pack.isSelected() && inSelection) {
            //put it in given delivery
            double totalWeight = delivery.getVehicle().getCurrentLoad() + pack.getWeight();
            if (totalWeight > delivery.getVehicle().getMaxCapacity()) {
                throw new BusinessException("Cannot put package #" + pack.getid() +
                        " on this vehicle, total weight would exceed capacity by " +
                        (totalWeight - delivery.getVehicle().getMaxCapacity()) + "kg");
            }
            else {
                _packageRepo.update(pack.getid(), pack.getContent(), pack.getDestination(), pack.getRegistrationTime(), pack.getWeight(), delivery, true);

                var v = delivery.getVehicle();
                _vehicleRepo.update(v.getId(), v.getPlateNumber(), v.isInDelivery(), v.getMaxCapacity(), pack.getWeight());
            }
        }

        if (pack.isSelected() == inSelection) {
            if (pack.getDelivery() != null)
                throw new BusinessException("Cannot edit package in delivery");

            _packageRepo.update(pack.getid(), content, destination, pack.getRegistrationTime(), weight, null, false);
        }
    }

    @Override
    public boolean deletePackage(int id)
    {
        Package packages = _packageRepo.getById(id);
        if (packages == null)
            throw new BusinessException("Cannot find packages, unable to remove it", ErrorCodes.PACKAGE_NOT_FOUND);
        if(packages.isSelected())
            throw new BusinessException("Cannot delete packages in delivery!");

        if (_packageRepo.delete(packages))
        {
            _log.log(Level.INFO, "Remove successful");
            return true;
        } else {
            _log.log(Level.WARNING, "Remove failed");
            return false;
        }
    }

    //endregion
}
