package pl.beata.todolist.notification;

import com.github.appreciated.app.layout.builder.entities.DefaultNotification;

public class BellDefaultNotification extends DefaultNotification {
	
	private int id;

	public BellDefaultNotification(String title, String description, int id) {
		super(title, description);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
