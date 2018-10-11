package pl.beata.todolist.components;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.model.Note;
import pl.beata.todolist.model.Task;
import pl.beata.todolist.model.User;

/**
 * 
 * Component displaying single note.
 *
 */
public class NoteComponent extends Panel {

	private static final long serialVersionUID = -7774631785217460548L;
	private VerticalLayout tasksLayout = new VerticalLayout();

	public NoteComponent(Note note, VaadinIcons icon) {
		super(icon.getHtml() + " " + note.getTitle());
		List<Task> tasks = note.getTasks();
		List<Label> doneTasks = new ArrayList<>();
		List<Label> notDoneTasks = new ArrayList<>();
		for (Task task : tasks) {
			String content = task.getContent();
			boolean done = task.isDone();
			Label taskLabel;

			if (done) {
				String doneTaskRow = VaadinIcons.CHECK_SQUARE_O.getHtml() + " " + "<s>" + content + "</s>";
				taskLabel = new Label(doneTaskRow);
				doneTasks.add(taskLabel);
			} else {
				String taskRow = VaadinIcons.THIN_SQUARE.getHtml() + " " + content;
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
		showSharedUsersIcon(note);

		setContent(tasksLayout);
		setSizeUndefined();

	}

	private void showSharedUsersIcon(Note note) {
		Set<User> sharedUsers = note.getCoOwners();
		HorizontalLayout hLayout = new HorizontalLayout();
		for (User user : sharedUsers) {
		Label sharedUser = new Label();
		sharedUser.setIcon(VaadinIcons.USER);
		hLayout.addComponent(sharedUser);
		sharedUser.setDescription(user.getfName() + " " + user.getlName());
		}
		tasksLayout.addComponent(hLayout);
	}
}
