package pl.beata.todolist.event;

import pl.beata.todolist.model.User;

public class UserRegisteredEvent {
	
	private User registeredUser;

	public UserRegisteredEvent(User registeredUser) {
		this.registeredUser = registeredUser;		
	}
	
	public User getRegisteredUser() {
		return registeredUser;
	}

}
