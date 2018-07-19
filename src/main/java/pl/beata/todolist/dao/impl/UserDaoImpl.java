package pl.beata.todolist.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import pl.beata.todolist.dao.AbstractDao;
import pl.beata.todolist.dao.UserDao;
import pl.beata.todolist.model.User;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

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
}
