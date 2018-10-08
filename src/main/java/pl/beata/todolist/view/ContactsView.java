package pl.beata.todolist.view;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ImageRenderer;

import pl.beata.todolist.MyUI;
import pl.beata.todolist.exceptions.ValidationException;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.UserService;
import pl.beata.todolist.streamResource.Base64StreamResource;

@SpringView(name = "contacts")
@MenuCaption("Contacts")
@MenuIcon(VaadinIcons.USERS)
@SpringComponent
@UIScope
public class ContactsView extends VerticalLayout implements View {

	private Button addBtn = new Button("Add new contact");
	private Button deleteBtn = new Button("Delete contact");
	private Grid<User> usersGrid = new Grid<>();
	private HorizontalLayout hLayout = new HorizontalLayout(addBtn, deleteBtn);
	private Window popup;
	private NewContactView newContactView;
	private UserService userService;

	@Autowired
	public ContactsView(NewContactView newContactView, UserService userService) {
		this.newContactView = newContactView;
		this.userService = userService;
		popup = new Window("New Contact", newContactView);
		popup.center();
		popup.setModal(true);
		openNewContactWindowListener();
		usersGrid.setSizeFull();
		addComponents(hLayout, usersGrid);
		createUsersGrid();
		createAddContactBtnListener();
		createDeleteBtnListener();
	}

	public Grid<User> getGrid() {
		return usersGrid;
	}

	private void createUsersGrid() {
		ValueProvider<User, Resource> function = (User f) -> new Base64StreamResource(f.getPhoto(), 30, 30);
		usersGrid.addColumn(function).setCaption("Photo").setRenderer(new ImageRenderer<>());
		usersGrid.addColumn(User::getfName).setCaption("First Name");
		usersGrid.addColumn(User::getlName).setCaption("Last Name");
		usersGrid.addColumn(User::getEmail).setCaption("Email");
		usersGrid.setSelectionMode(SelectionMode.MULTI);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		showContactsInGrid();
	}

	private void openNewContactWindowListener() {
		addBtn.addClickListener(event -> {
			MyUI.getCurrent().addWindow(popup);
		});
	}

	private void createAddContactBtnListener() {
		newContactView.getAddContactBtn().addClickListener(event -> {
			try {
				userService.setContact(newContactView.getEmailValue());
				showContactsInGrid();
				MyUI.getCurrent().removeWindow(popup);
			} catch (ValidationException e) {
				Notification.show(e.getMessage());
				e.printStackTrace();
			}
		});
	}

	private void showContactsInGrid() {
		Set<User> contacts = userService.getCurrentUser().getContacts();
		usersGrid.setItems(contacts);
	}

	private void createDeleteBtnListener() {
		deleteBtn.addClickListener(evetn -> {
			Set<User> selectedUsers = usersGrid.getSelectedItems();
			Set<String> contactsEmails = new HashSet<>();
			for (User user : selectedUsers) {
				contactsEmails.add(user.getEmail());
			}
			userService.deleteContact(contactsEmails);
			showContactsInGrid();
		});

	}
}
