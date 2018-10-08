package pl.beata.todolist.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.spring.events.EventBus.UIEventBus;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.builder.entities.DefaultNotification;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.MyUI;
import pl.beata.todolist.components.SharedContactsComponent;
import pl.beata.todolist.components.TaskComponent;
import pl.beata.todolist.dao.NoteDao;
import pl.beata.todolist.dao.NotificationDao;
import pl.beata.todolist.dao.TaskDao;
import pl.beata.todolist.event.CloseDeleteWindowEvent;
import pl.beata.todolist.event.DeleteNoteEvent;
import pl.beata.todolist.model.Note;
import pl.beata.todolist.model.BellNotification;
import pl.beata.todolist.model.Task;
import pl.beata.todolist.model.User;
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
	private Button deleteBtn = new Button("Delete");
	private Note note;
	private UserService userService;
	private NoteDao noteDao;
	private TaskDao taskDao;
	private NotificationDao notificationDao;
	private DeleteNoteView deleteNoteView;
	private UIEventBus eventBus;
	private SharedContactsComponent sharedContacts;

	@Autowired
	public EditNoteView(UserService userService, NoteDao noteDao, TaskDao taskDao, DeleteNoteView deleteNoteView,
			UIEventBus eventBus, SharedContactsComponent sharedContacts, NotificationDao notificationDao) {
		this.userService = userService;
		this.noteDao = noteDao;
		this.taskDao = taskDao;
		this.deleteNoteView = deleteNoteView;
		this.eventBus = eventBus;
		this.sharedContacts = sharedContacts;
		this.notificationDao = notificationDao;
		HorizontalLayout generalLayout = new HorizontalLayout();
		VerticalLayout vLayout = new VerticalLayout();
		HorizontalLayout hLayout = new HorizontalLayout(saveBtn, deleteBtn);
		vLayout.addComponents(owner, noteName, tasksLayout, addTaskBtn, hLayout);
		generalLayout.addComponents(vLayout, sharedContacts);
		setContent(generalLayout);
		createAddTaskBtnListener();
		createSaveBtnListener();
		openDeleteNoteWindowListener();
		createYesBtnListener();
		createNoBtnListener();
	}

	@Override
	@Transactional
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		String params = event.getParameters();
		sharedContacts.setSharedContacts(userService.getCurrentUser().getContacts());
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
			sharedContacts.selectContacts(note.getCoOwners());
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
			saveNoteToSharedUsers();
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

	private void openDeleteNoteWindowListener() {
		deleteBtn.addClickListener(event -> {
			MyUI.getCurrent().addWindow(deleteNoteView);
			deleteNoteView.center();
			deleteNoteView.setModal(true);
			deleteNoteView.setWidth(13, UNITS_CM);
		});
	}

	private void createYesBtnListener() {
		deleteNoteView.getYesBtn().addClickListener(event -> {
			DeleteNoteEvent deleteNoteEvent = new DeleteNoteEvent(note);
			eventBus.publish(this, deleteNoteEvent);
		});
	}

	private void createNoBtnListener() {
		deleteNoteView.getNoBtn().addClickListener(event -> {
			CloseDeleteWindowEvent closedeleteWindowEvent = new CloseDeleteWindowEvent();
			eventBus.publish(this, closedeleteWindowEvent);
		});
	}

	private void saveNoteToSharedUsers() {
		Set<User> sharedUsers = sharedContacts.getSharedUsers();
		note.setCoOwners(sharedUsers);
		
			BellNotification notification = new BellNotification();
			notification.setTitle("New Note");
			notification.setDescription("New note from " + userService.getCurrentUser().getfName() + userService.getCurrentUser().getlName());
			
			for (User user: sharedUsers) {
				notification.setUser(user);	
			}
			
			notificationDao.create(notification);
			
	}
}
