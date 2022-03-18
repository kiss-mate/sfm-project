package repository;

import data.Driver;

/**
 * Provides data access to the drivers table
 */
public interface IDriverRepository extends IRepositoryBase<Driver> {
    /**
     * Updates one entry by id in the drivers table
     * @param id id of the updated driver object
     * @param name name param for driver object
     */
    void update(int id, String name);
}
