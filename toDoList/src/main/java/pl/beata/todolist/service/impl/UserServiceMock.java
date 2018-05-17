package pl.beata.todolist.service.impl;

import org.springframework.stereotype.Service;

import pl.beata.todolist.service.UserService;

@Service
public class UserServiceMock implements UserService{
	
	@Override
	public boolean isUserLoggedIn() {
		return false;
	}

}
