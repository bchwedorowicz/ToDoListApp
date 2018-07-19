package pl.beata.todolist.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.model.Note;
import pl.beata.todolist.model.Task;

public class NoteComponent extends Panel {

	private VerticalLayout tasksLayout = new VerticalLayout();

	public NoteComponent(Note note) {
		super(note.getTitle());
		List<Task> tasks = note.getTasks();
		List<Label> doneTasks = new ArrayList<>();
		List<Label> notDoneTasks = new ArrayList<>();
		for (Task task : tasks) {
			String content = task.getContent();
			boolean done = task.isDone();
			String taskRow = VaadinIcons.THIN_SQUARE.getHtml() + " ";
			Label taskLabel;

			if (done) {
				taskRow += "<s>" + content + "</s>";
				taskLabel = new Label(taskRow);
				doneTasks.add(taskLabel);
			} else {
				taskRow += content;
				taskLabel = new Label(taskRow);
				notDoneTasks.add(taskLabel);
			}
			taskLabel.setContentMode(ContentMode.HTML);	
		}
		for (Label label : notDoneTasks) {
			tasksLayout.addComponent(label);
		}
		for (Label label : doneTasks) {
			tasksLayout.addComponent(label);
		}
		setContent(tasksLayout);
		setSizeUndefined();

	}
}
