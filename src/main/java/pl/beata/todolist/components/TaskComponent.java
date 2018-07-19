package pl.beata.todolist.components;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import pl.beata.todolist.model.Task;

public class TaskComponent extends HorizontalLayout {
	
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

	public void setValuesFromComponents() {
		task.setContent(textField.getValue());
		task.setDone(checkBox.getValue());
	}
	
	public Button getDeleteTaskBtn() {
		return deleteTaskBtn;
	}
	
	public Task getTask() {
		return task;
	}
}
