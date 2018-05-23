package pl.beata.todolist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.beata.todolist.dao.UserDao;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.SessionService;
import pl.beata.todolist.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	//TODO: SPrawdzic, czy zapisuje id do sesji

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
	public boolean register(User user) {
		if ((userDao.findUserByEmail(user.getEmail())).equals(null)) {
			return false;
		} else {
			userDao.createUser(user);
		}
		return true;
	}
}
