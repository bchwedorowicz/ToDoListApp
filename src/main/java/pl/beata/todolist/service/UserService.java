package pl.beata.todolist.service;

import pl.beata.todolist.model.User;

public interface UserService {

	boolean isUserLoggedIn();
	boolean login(String email, String password);
	boolean register(User user);

}
