package pl.beata.todolist.dao;

public interface Dao<T> {
	
	void create(T entity);
	T findById(int id);
	void update(T entity);
	void delete(T entity);

}
