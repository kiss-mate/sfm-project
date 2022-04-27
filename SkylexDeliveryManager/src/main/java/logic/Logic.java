package logic;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import common.exceptions.BusinessException;
import data.Driver;
import enums.ErrorCodes;
import repository.IDriverRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic implements ILogic {
    private final Logger _log;
    private final IDriverRepository _driverRepo;

    /**
     * Creates Logic object for the project
     * @param log Logger log
     * @exception NullPointerException log cannot be null
     * @exception NullPointerException driverRepo cannot be null
     */
    @Inject
    public Logic(Logger log, IDriverRepository driverRepo) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_driverRepo = driverRepo) == null) throw new ArgumentNullException("driverRepo");
    }

    //region DRIVER RELATED LOGIC
    @Override
    public Driver addDriver(String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new BusinessException("Driver name was null or whitespace");

        var newDriver = new Driver();
        newDriver.setName(name);
        _driverRepo.insert(newDriver);
        _log.log(Level.INFO, "New Driver object added to the database: " + newDriver);
        return newDriver;
    }

    @Override
    public void changeOneDriver(int id, String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new BusinessException("Driver name was null or whitespace");
        var driver = _driverRepo.getById(id);
        if (driver == null)
            throw new BusinessException("Driver object not found", ErrorCodes.NOT_FOUND_IN_DB);
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
    public void deleteDriver(int id) {
        var driver = _driverRepo.getById(id);
        if (driver == null)
            throw new BusinessException("Driver object not found", ErrorCodes.NOT_FOUND_IN_DB);
        _driverRepo.delete(driver);
        _log.log(Level.INFO, "Removed Driver object from the database: " + driver);
    }
    //endregion
}
