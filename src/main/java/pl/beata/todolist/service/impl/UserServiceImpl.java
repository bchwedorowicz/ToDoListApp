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

	/**
	 * Returns true if user is logged in.
	 */
	@Override
	public boolean isUserLoggedIn() {
		return sessionService.getCurrentUserId() != null;
	}

	/**
	 * Returns true if email and password are correct. Sets user to session, if
	 * email and password is correct.
	 * 
	 * @param email
	 *            to login.
	 * @param password
	 *            to login.
	 */
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

	/**
	 * Logs out the user.
	 */
	@Override
	public void logout() {
		sessionService.setCurrentUserId(null);
	}

	/**
	 * Registers the user by adding user entity to database. Validates if given user
	 * can be registered.
	 * 
	 * @param user
	 *            to register.
	 * @throws ValidationException
	 *             if given user can not be registered.
	 */
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

	/**
	 * Returns currently logged user.
	 */
	@Override
	public User getCurrentUser() {
		int id = sessionService.getCurrentUserId();
		return userDao.findById(id);
	}

	/**
	 * Adds new contact to contacts of currently logged user.
	 * 
	 * @param contactEmail
	 *            contact email to add.
	 */
	@Transactional
	public void addContact(String contactEmail) throws ValidationException {
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

	/**
	 * Delete contact form contacts of currently logged user.
	 * 
	 * @param contactsEmails
	 *            contacts emails to delete.
	 */
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
