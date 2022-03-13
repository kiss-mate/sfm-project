package repository;

import data.Sample;

/**
 * Provides CRUD data access to Sample entities
 */
public interface ISampleRepository extends IRepositoryBase<Sample> {
    /**
     * Updates the fields of the object with the given id
     * @param id id of the object to update
     * @param value1 Value1 of the object to update
     * @param value2 Value2 of the object to update
     */
    void update(int id, String value1, int value2);
}
