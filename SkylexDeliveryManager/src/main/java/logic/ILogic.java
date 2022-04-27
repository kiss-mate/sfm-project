package logic;

import data.Driver;

import java.util.List;

/**
 * Provides business and transaction logic for the project
 */
public interface ILogic {
     /**
     * Creates and saves one Driver object
     * @param name name of the driver
     */
     Driver addDriver(String name);

    /**
     * Updates one Driver object
     * @param id id of the driver
     * @param name name of the driver
     */
    void changeOneDriver(int id, String name);

    /**
     * Fetches one Driver object
     * @param id id of the driver
     * @return driver object with given id
     */
    Driver getOneDriver(int id);

    /**
     * Gets all teh Driver objects in a list
     * @return list of Driver objects
     */
    List<Driver> getAllDrivers();

    /**
     * Deletes one Driver object by id
     * @param id id of the driver
     */
    void deleteDriver(int id);
}
