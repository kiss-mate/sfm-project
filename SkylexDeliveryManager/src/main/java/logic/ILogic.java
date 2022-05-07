package logic;

import data.Driver;
import data.Vehicle;
import data.Package;

import java.util.Date;
import java.util.List;

/**
 * Provides business and transaction logic for the project
 */
public interface ILogic {

    //region DRIVER RELATED LOGIC

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
     * Gets all the Driver objects in a list
     * @return list of Driver objects
     */
    List<Driver> getAllDrivers();

    /**
     * Deletes one Driver object by id
     * @param id id of the driver
     */
    boolean deleteDriver(int id);

    //endregion

    //region VEHICLE RELATED LOGIC

    /**
     * Gets all the Vehicle objects in a list
     * @return list of Vehicle objects
     */
    List<Vehicle> getAllVehicles();

    /**
     * Fetches one Vehicle object
     * @param id id of the vehicle
     * @return vehicle object with given id
     */
    Vehicle getOneVehicle(int id);

    /**
     * Creates and saves one Vehicle object
     * @param plateNumber plateNumber of the vehicle
     * @param maxCapacity maxCapacity of the vehicle
     */
    void addVehicle(String plateNumber, double maxCapacity);

    /**
     * Updates one Vehicle object
     * @param id id of the vehicle
     * @param plateNumber plateNumber of the vehicle
     * @param maxCapacity maxCapacity of the vehicle
     * @param inDelivery inDelivery of the vehicle
     */
    void changeOneVehicle(int id, String plateNumber, double maxCapacity, double currentLoad, boolean inDelivery);

    /**
     * Deletes one Vehicle object by id
     * @param id id of the vehicle
     */
    boolean deleteVehicle(int id);

    //endregion


    //region PACKAGE RELATED LOGIC
    /**
     * Gets all the Package objects in a list
     * @return list of Package objects
     */
    List<Package> getAllPackages();

    /**
     * Fetches one Package object
     * @param id id of the Package
     * @return Package object with given id
     */
    Package getOnePackage(int id);

    /**
     * Creates and saves one Package object
     * @param Content Content of the Package
     * @param Destination Destination of the Package
     * @param weight weight of the Package
     */
    void addPackage(String Content, String Destination, double weight);


    /**
     * Deletes one Package object by id
     * @param id id of the Package
     */
    boolean deletePackage(int id);



    //endregion
}
