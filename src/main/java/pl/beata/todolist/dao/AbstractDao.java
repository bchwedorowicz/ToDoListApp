package pl.beata.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import pl.beata.todolist.model.User;

public abstract class AbstractDao<T> implements Dao<T> {

	@PersistenceContext
	protected EntityManager em;
	private Class<T> clazz;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	@Transactional
	public void create(T entity) {
		em.persist(entity);
	}

	@Override
	public T findById(int id) {
		return em.find(clazz, id);
	}

	@Override
	@Transactional
	public void update(T entity) {
		em.merge(entity);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}

}
