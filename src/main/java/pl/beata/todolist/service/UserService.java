package pl.beata.todolist.service;

import java.util.Set;

import pl.beata.todolist.exceptions.ValidationException;
import pl.beata.todolist.model.User;

public interface UserService {

	boolean isUserLoggedIn();
	boolean login(String email, String password);
	void register(User user) throws ValidationException;
	User getCurrentUser();
	void setContact(String contactEmail) throws ValidationException;
	void deleteContact(Set<String> contactsEmails);
	void logout();

}
