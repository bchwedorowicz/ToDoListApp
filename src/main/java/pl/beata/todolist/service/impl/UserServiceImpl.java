package pl.beata.todolist.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.beata.todolist.dao.UserDao;
import pl.beata.todolist.exceptions.ValidationException;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.SessionService;
import pl.beata.todolist.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private SessionService sessionService;

	@Autowired
	public UserServiceImpl(UserDao userDao, SessionService sessionService) {
		this.userDao = userDao;
		this.sessionService = sessionService;
	}

	@Override
	public boolean isUserLoggedIn() {
		return sessionService.getCurrentUserId() != null;
	}

	@Override
	public boolean login(String email, String password) {
		User user = userDao.findUserByEmail(email);
		boolean isPasswordCorrect = user != null && password.equals(user.getPassword());
		if (isPasswordCorrect) {
			saveUserToSession(user);			
		}
		return isPasswordCorrect;
	}

	private void saveUserToSession(User user) {
		int userId = user.getId();
		sessionService.setCurrentUserId(userId);
	}
	
	@Override
	public void logout() {
		sessionService.setCurrentUserId(null);
	}

	@Override
	public void register(User user) throws ValidationException {
		if (user.getEmail() == null) {
			throw new ValidationException("Give your email");
		}
		User foundUser = userDao.findUserByEmail(user.getEmail());
		if (foundUser != null) {
			throw new ValidationException("User with this email already exists");
		}
		if (user.getPassword() == null) {
			throw new ValidationException("Your password must contain at least 1 character");
		}
		userDao.create(user);
	}

	@Override
	public User getCurrentUser() {
		int id = sessionService.getCurrentUserId();
		return userDao.findById(id);
	}
	
	@Transactional
	public void setContact(String contactEmail) throws ValidationException {
		User contact = userDao.findUserByEmail(contactEmail);
		User currentUser = getCurrentUser();
		if (currentUser.equals(contact)) {
			throw new ValidationException("That`s your email adress. You can not add your email as your contact");
		}
		if (contact == null) {
			throw new ValidationException("This email doesn`t exist");
		}
		if (currentUser.getContacts().contains(contact)) {
			throw new ValidationException("This email is already added to your contacts");
		}
		currentUser.getContacts().add(contact);
	}
	
	@Transactional
	public void deleteContact(Set<String> contactsEmails) {
		Set<User> contacts = new HashSet<>();
		for (String email : contactsEmails) {
			User contactUser = userDao.findUserByEmail(email);
			contacts.add(contactUser);
		}
		getCurrentUser().getContacts().removeAll(contacts);
	}

}
