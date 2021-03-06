package pl.beata.todolist.service.impl;

import org.springframework.stereotype.Service;

import com.vaadin.server.VaadinSession;

import pl.beata.todolist.MyUI;
import pl.beata.todolist.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

	private static final String CURRENT_USER_ID = "currentUserId";

	private VaadinSession getSessionObject() {
		return MyUI.getCurrent().getSession();
	}

	/**
	 * Returns the id number of the current user.
	 */
	@Override
	public Integer getCurrentUserId() {
		return (Integer) getSessionObject().getAttribute(CURRENT_USER_ID);
	}

	/**
	 * Sets current user id to session.
	 */
	@Override
	public void setCurrentUserId(Integer userId) {
		getSessionObject().setAttribute(CURRENT_USER_ID, userId);
	}

}
