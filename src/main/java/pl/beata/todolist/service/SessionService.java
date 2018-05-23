package pl.beata.todolist.service;

public interface SessionService {
	
	Integer getCurrentUserId();
	void setCurrentUserId(Integer userId);

}
