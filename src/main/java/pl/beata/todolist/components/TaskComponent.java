package pl.beata.todolist.components;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import pl.beata.todolist.model.Task;

/**
 * 
 * Component displaying single task.
 *
 */
public class TaskComponent extends HorizontalLayout {
	
	private static final long serialVersionUID = 8876660463475759796L;
	private CheckBox checkBox = new CheckBox();
	private TextField textField = new TextField();
	private Button deleteTaskBtn = new Button(VaadinIcons.CLOSE_CIRCLE_O);
	private Task task;
	
	public TaskComponent(Task task) {
		this.task = task;
		addComponents(checkBox, textField, deleteTaskBtn);
		String value = task.getContent() == null ? "" : task.getContent();
		textField.setValue(value);
		checkBox.setValue(task.isDone());
	}
	
	/**
	 * Gets the values from textfield (task content) 
	 * and checkbox (check whether task is done).
	 * Sets it to task model.
	 * 
	 */
	public void setValuesFromComponents() {
		task.setContent(textField.getValue());
		task.setDone(checkBox.getValue());
	}
	
	/**
	 * 
	 * Gets button to delete tasks.
	 */
	public Button getDeleteTaskBtn() {
		return deleteTaskBtn;
	}
	
	/**
	 * 
	 * Gets the task.
	 */
	public Task getTask() {
		return task;
	}
}
