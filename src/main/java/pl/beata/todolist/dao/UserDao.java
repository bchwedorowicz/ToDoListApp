package pl.beata.todolist.dao;

import pl.beata.todolist.model.User;

public interface UserDao {
	
	User findUserByEmail(String userEmail);
	void createUser(User user);

}
