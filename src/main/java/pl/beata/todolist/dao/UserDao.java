package pl.beata.todolist.dao;

import pl.beata.todolist.model.User;

public interface UserDao extends Dao<User> {
	
	User findUserByEmail(String userEmail);
}
