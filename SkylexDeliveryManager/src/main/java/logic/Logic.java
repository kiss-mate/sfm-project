package logic;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import common.exceptions.BusinessException;
import data.Driver;
import data.Sample;
import enums.ErrorCodes;
import repository.IDriverRepository;
import repository.ISampleRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic implements ILogic {
    private final Logger _log;
    private final IDriverRepository _driverRepo;
    private final ISampleRepository _sampleRepo;

    /**
     * Creates Logic object for the project
     * @param log Logger log
     * @param sampleRepo ISampleRepository sampleRepo
     * @exception NullPointerException log cannot be null
     * @exception NullPointerException sampleRepo cannot be null
     */
    @Inject
    public Logic(Logger log, IDriverRepository driverRepo, ISampleRepository sampleRepo) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_driverRepo = driverRepo) == null) throw new ArgumentNullException("driverRepo");
        if ((_sampleRepo = sampleRepo) == null) throw new ArgumentNullException("sampleRepo");
    }

    //region SAMPLE RELATED LOGIC
    @Override
    public void addOneSample(String value1, int value2) {
        var newSample = new Sample();
        newSample.setValue1(value1);
        newSample.setValue2(value2);
        _sampleRepo.insert(newSample);
        _log.log(Level.INFO, "New sample object created");
    }
    //endregion

    //region DRIVER RELATED LOGIC
    @Override
    public void addDriver(String name) {
        if (name == null || name.isBlank() || name.isEmpty())
            throw new BusinessException("Driver name was null or whitespace");

        var newDriver = new Driver();
        newDriver.setName(name);
        _driverRepo.insert(newDriver);
        _log.log(Level.INFO, "New Driver object added to the database: " + newDriver);
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
