package pl.beata.todolist.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.components.TaskComponent;
import pl.beata.todolist.dao.NoteDao;
import pl.beata.todolist.dao.TaskDao;
import pl.beata.todolist.model.Note;
import pl.beata.todolist.model.Task;
import pl.beata.todolist.service.UserService;

@SpringView(name = "note")
@MenuCaption("Add New Note")
@MenuIcon(VaadinIcons.PLUS_CIRCLE_O)
@SpringComponent
@UIScope
public class EditNoteView extends Panel implements View {

	private Label owner = new Label();
	private TextField noteName = new TextField("Note Name");
	private List<TaskComponent> tasksList = new ArrayList<>();
	private VerticalLayout tasksLayout = new VerticalLayout();
	private Button addTaskBtn = new Button("Add Task", VaadinIcons.PLUS_CIRCLE_O);
	private Button saveBtn = new Button("Save");
	private Note note;
	private UserService userService;
	private NoteDao noteDao;
	private TaskDao taskDao;

	@Autowired
	public EditNoteView(UserService userService, NoteDao noteDao, TaskDao taskDao) {
		this.userService = userService;
		this.noteDao = noteDao;
		this.taskDao = taskDao;
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.addComponents(owner, noteName, tasksLayout, addTaskBtn, saveBtn);
		setContent(vLayout);
		createAddTaskBtnListener();
		createSaveBtnListener();
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		String params = event.getParameters();
		if (params == null || params.equals("")) {
			note = new Note();
		} else {
			int id = Integer.parseInt(params);
			note = noteDao.findById(id);
			noteName.setValue(note.getTitle());
			List<Task> list = note.getTasks();
			for (Task task : list) {
				addTask(task);
			}
		}
	}

	private void createAddTaskBtnListener() {
		addTaskBtn.addClickListener(event -> {
			Task newTask = new Task();
			addTask(newTask);
			note.addTask(newTask);
		});
	}

	private void addTask(Task newTask) {
		TaskComponent emptyTaskComponent = createTaskComponent(newTask);
		tasksLayout.addComponent(emptyTaskComponent);
		tasksList.add(emptyTaskComponent);
	}

	private TaskComponent createTaskComponent(Task newTask) {
		TaskComponent taskComponent = new TaskComponent(newTask);
		createDeleteTaskBtnListener(taskComponent);
		return taskComponent;
	}

	private void createSaveBtnListener() {
		saveBtn.addClickListener(event -> {
			note.setTitle(noteName.getValue());
			note.setOwner(userService.getCurrentUser());
			for (TaskComponent taskComponent : tasksList) {
				taskComponent.setValuesFromComponents();
			}
			noteDao.update(note);
			Notification.show("Note saved");
		});
	}

	private void createDeleteTaskBtnListener(TaskComponent taskComponent) {
			taskComponent.getDeleteTaskBtn().addClickListener(event -> {			
				Task task = taskComponent.getTask();
				note.getTasks().remove(task);
				noteDao.update(note);
				tasksLayout.removeComponent(taskComponent);
				Notification.show("Task deleted");
			});
	}

}
