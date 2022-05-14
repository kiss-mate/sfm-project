package repository.interfaces;

import data.Delivery;
import data.Package;

import java.util.Date;

public interface IPackageRepository extends IRepositoryBase<Package> {
    /**
     * Updates a Vehicle object from the database according to the id
     * @param id Id of the package to update
     * @param Content Contents of the package
     * @param Destination Destination of the package
     * @param RegistrationTime When the package was registered
     * @param weight Weight of the package
     */
    boolean update(int id, String Content, String Destination, Date RegistrationTime, double weight, Delivery delivery, boolean selected);
}
