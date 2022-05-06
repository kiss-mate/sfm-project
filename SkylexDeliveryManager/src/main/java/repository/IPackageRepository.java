package repository;

import data.Package;

import repository.IRepositoryBase;

public interface IPackageRepository extends IRepositoryBase<Package> {
    /**
     * Updates a Vehicle object from the database according to the id
     * @param id Id of the package to update
     * @param content Contents of the package
     * @param weight Weight of the package
     * @param destination Destination of the package
     * @param delivery
     * @param selected
     */
    boolean Update(int id, String content, int weight, String destination, Delivery delivery, boolean selected);
}
