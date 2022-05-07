package logic;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import common.exceptions.BusinessException;
import data.Driver;
import data.Vehicle;
import data.Package;
import models.enums.ErrorCodes;
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

    /**
     * Creates Logic object for the project
     * @param log Logger log
     * @exception NullPointerException log cannot be null
     * @exception NullPointerException driverRepo cannot be null
     * @exception NullPointerException vehicleRepo cannot be null
     */
    @Inject
    public Logic(Logger log, IDriverRepository driverRepo, IVehicleRepository vehicleRepo, IPackageRepository packageRepo) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_driverRepo = driverRepo) == null) throw new ArgumentNullException("driverRepo");
        if ((_vehicleRepo = vehicleRepo) == null) throw new ArgumentNullException("vehicleRepo");
        if ((_packageRepo = packageRepo) == null) throw new ArgumentNullException("packageRepo");
    }

    //region DRIVER RELATED LOGIC
    @Override
    public Driver addDriver(String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new BusinessException("Driver name was null or whitespace", ErrorCodes.DRIVER_NAME_EMPTY_OR_NULL);

        var newDriver = new Driver();
        newDriver.setName(name);
        _driverRepo.insert(newDriver);
        _log.log(Level.INFO, "New Driver object added to the database: " + newDriver);
        return newDriver;
    }

    @Override
    public void changeOneDriver(int id, String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new BusinessException("Driver name was null or whitespace", ErrorCodes.DRIVER_NAME_EMPTY_OR_NULL);
        var driver = _driverRepo.getById(id);
        if (driver == null)
            throw new BusinessException("Driver object not found", ErrorCodes.DRIVER_NOT_FOUND);
        _driverRepo.update(id, name);
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
        _driverRepo.delete(driver);
        _log.log(Level.INFO, "Removed Driver object from the database: " + driver);
            throw new BusinessException("Driver object not found", ErrorCodes.NOT_FOUND_IN_DB);
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
    public void addPackage(String Content, String Destination, double weight)
    {
        if (Content == null || Content.isBlank() || Content.isEmpty())
            throw new BusinessException("Package content was null or whitespace");
        if (weight <= 0)
            throw new BusinessException("Weight is zero or negative");

        var newPackage = new Package();
        newPackage.setContent(Content);
        newPackage.setWeight(weight);
        newPackage.setDestination(Destination);
        newPackage.setInDelivery(false);
        newPackage.setRegistrationTime(new Date());
        _packageRepo.insert(newPackage);
        _log.log(Level.INFO, "New Package object added to the database: " + newPackage);
    }


    @Override
    public boolean deletePackage(int id)
    {
        Package packages = _packageRepo.getById(id);
        if (packages == null)
            throw new BusinessException("Cannot find packages, unable to remove it");
        if(packages.isInDelivery())
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
