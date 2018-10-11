package pl.beata.todolist.dao;

/**
 * 
 * Base interface to all daos.
 *
 * @param <T>
 *            Entity type for which dao should work.
 */
public interface Dao<T> {
	/**
	 * Adds entity to database.
	 * 
	 * @param entity
	 *            to create.
	 */
	void create(T entity);

	/**
	 * Returns entity instance found by id.
	 * 
	 * @param id
	 *            of entity.
	 */
	T findById(int id);

	/**
	 * Updates already existing entity in database.
	 * 
	 * @param entity
	 *            to update.
	 */
	void update(T entity);

	/**
	 * Deletes entity form database.
	 * 
	 * @param entity
	 *            to delete.
	 */
	void delete(T entity);

}
