package repository;

import data.Package;

import repository.IRepositoryBase;

import java.util.Date;

public interface IPackageRepository extends IRepositoryBase<Package> {
    /**
     * Updates a Vehicle object from the database according to the id
     * @param id Id of the package to update
     * @param Content Contents of the package
     * @param Destination Destination of the package
     * @param RegistrationTime When the package was registered
     * @param weight Weight of the package
     * @param inDelivery If package is being delivered or not
     */
    boolean update(int id, String Content, String Destination, Date RegistrationTime, double weight, boolean inDelivery);
}
