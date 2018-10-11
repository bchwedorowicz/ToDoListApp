package pl.beata.todolist.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.event.UserRegisteredEvent;
import pl.beata.todolist.exceptions.ValidationException;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.UserService;
import pl.beata.todolist.uploader.PhotoUploader;
/**
 * 
 * Component with registration form.
 *
 */
@SpringComponent
@UIScope
public class RegistrationForm extends FormLayout {

	private static final long serialVersionUID = -6723985974693619938L;
	private PhotoUploader photoUploader;
	private Upload upload = new Upload();
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
		upload.setButtonCaption("Pick your photo");
		photoUploader = new PhotoUploader(upload);
		VerticalLayout vLayout = new VerticalLayout(photoUploader, upload, fName, lName, email, password1, password2,
				saveBtn);
		for (Component component : vLayout) {
			component.setWidth(7, Unit.CM);
		}
		vLayout.setMargin(true);
		addComponent(vLayout);
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
		user.setPhoto(photoUploader.getBytes());
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
