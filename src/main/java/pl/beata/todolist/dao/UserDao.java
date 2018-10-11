package pl.beata.todolist.dao;

import pl.beata.todolist.model.User;

public interface UserDao extends Dao<User> {
	
	/**
	 * Returns user found by email or null if not found email.
	 * 
	 * @param userEmail email to find.
	 */
	User findUserByEmail(String userEmail);
}
