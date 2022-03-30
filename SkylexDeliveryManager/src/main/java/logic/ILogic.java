package logic;

import data.Driver;

import java.util.List;

/**
 * Provides business and transaction logic for the project
 */
public interface ILogic {
    /**
     * Creates and adds one Sample object to the "sample" table
     * @param value1 value1
     * @param value2 value2
     */
    void addOneSample(String value1, int value2);

    /**
     * Creates and saves one Driver object
     * @param name name of the driver
     */
    void addDriver(String name);

    /**
     * Updates one Driver object
     * @param id id of the driver
     * @param name name of the driver
     */
    void changeOneDriver(int id, String name);

    /**
     * Fetches one Driver object
     * @param id id of the driver
     * @return
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
