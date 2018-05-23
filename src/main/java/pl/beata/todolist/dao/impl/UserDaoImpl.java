package pl.beata.todolist.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.beata.todolist.dao.UserDao;
import pl.beata.todolist.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public User findUserByEmail(String userEmail) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User AS u WHERE u.email = :userEmail", User.class);
		query.setParameter("userEmail", userEmail);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	@Transactional
	public void createUser(User user) {
		em.persist(user);
	}

}
