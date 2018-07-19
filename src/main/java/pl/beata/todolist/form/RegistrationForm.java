package pl.beata.todolist.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import pl.beata.todolist.event.UserRegisteredEvent;
import pl.beata.todolist.exceptions.ValidationException;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.UserService;

@SpringComponent
@UIScope
public class RegistrationForm extends FormLayout {

	private Image photo = new Image("Photo");
	private TextField fName = new TextField("First Name");
	private TextField lName = new TextField("Last Name");
	private TextField email = new TextField("Email");
	private PasswordField password1 = new PasswordField("Password");
	private PasswordField password2 = new PasswordField("Retype password");
	private Button saveBtn = new Button("Save");
	private UserService userService;
	private UIEventBus eventBus;

	@Autowired
	public RegistrationForm(UserService userService, UIEventBus eventBus) {
		this.userService = userService;
		this.eventBus = eventBus;
		addComponents(photo, fName, lName, email, password1, password2, saveBtn);
		addRegisterSaveButtonListener();
		setEmailPasswordValueListener();
	}

	private void addRegisterSaveButtonListener() {
		saveBtn.addClickListener(event -> {

			try {
				User user = createUser();
				userService.register(user);
				UserRegisteredEvent registrationEvent = new UserRegisteredEvent(user);
				eventBus.publish(this, registrationEvent);
			} catch (ValidationException e) {
				Notification.show(e.getMessage());
			}
		});
	}

	private User createUser() throws ValidationException {
		User user = new User();
		user.setEmail(email.getValue());
		user.setfName(fName.getValue());
		user.setlName(lName.getValue());
		if (password1.getValue().equals(password2.getValue())) {
			user.setPassword(password1.getValue());
		} else {
			throw new ValidationException("Password and Retyped Password must be identical!");
		}
		return user;
	}

	private void setEmailPasswordValueListener() {
		saveBtn.addClickListener(e -> {
			String emailValue = email.getValue();
			String passwordValue = password1.getValue();
			email.setValue(emailValue);
			password1.setValue(passwordValue);
		});
	}

}
