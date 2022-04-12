package repository;

import java.util.List;

/**
 * Provides the generic CR(U)D data access for T type entities. Update is entity dependent because of different fields.
 * @param <T> type of entity
 */
public interface IRepositoryBase<T> {
    /**
     * Inserts an entity of type T into the corresponding table
     * @param entity The entity to insert into table
     */
    int insert(T entity);

    /**
     * Gets all T type entities from the given table
     * @return List of T type entities
     */
    List<T> getAll();

    /**
     * Gets one entity of type T by id from the given table
     * @param id id of the requested entity
     * @return Entity with given id
     */
    T getById(int id);

    /**
     * Deletes the entity with the given id from the table
     * @param id id of the entity to delete
     */
    boolean delete(int id);

    /**
     * Deletes the entity with the given id from the table
     */
    boolean delete(T entity);
}
