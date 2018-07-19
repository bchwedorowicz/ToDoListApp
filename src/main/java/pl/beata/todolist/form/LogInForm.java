package pl.beata.todolist.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import pl.beata.todolist.MyUI;
import pl.beata.todolist.event.UserEvent;
import pl.beata.todolist.event.UserRegisteredEvent;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.UserService;
import pl.beata.todolist.view.MainView;

@SpringComponent
@UIScope
public class LogInForm extends FormLayout {

	private TextField email = new TextField("Email");
	private PasswordField password = new PasswordField("Password");
	private Button loginBtn = new Button("Login");
	private Button registrationBtn = new Button("Register");
	private UserService userService;
	private UIEventBus eventBus;
	private Window popup;
	
	@Autowired
	public LogInForm(RegistrationForm registrationForm, UserService userService, UIEventBus eventBus) {
		this.userService = userService;
		this.eventBus = eventBus;
		popup = new Window("Registration", registrationForm);
		popup.setModal(true);
		email.setValue("asia.pipiszka@gmail.com");
		password.setValue("a");
		addComponents(email, password, loginBtn, createRegistrationLayout());
		openRegistrationWindowListener();
		addLoginButtonListener();
		subscribeUserRegistrationEvent();
	}

	private HorizontalLayout createRegistrationLayout() {
		Label newCustomerLabel = new Label("New Customer?");
		HorizontalLayout registrationLayout = new HorizontalLayout();
		registrationLayout.addComponents(newCustomerLabel, registrationBtn);
		return registrationLayout;
	}

	private void openRegistrationWindowListener() {
		registrationBtn.addClickListener(event -> {
			UI.getCurrent().addWindow(popup);
		});
	}

	private void addLoginButtonListener() {
		loginBtn.addClickListener(e -> {
			if (userService.login(email.getValue(), password.getValue())) {
			UserEvent loggerEvent = new UserEvent();
			eventBus.publish(this, loggerEvent);
			} else {
				Notification.show("Wrong email or password");
			}
		});
	}
	
	private void subscribeUserRegistrationEvent() {
		eventBus.subscribe(new EventBusListener<UserRegisteredEvent>() {

			@Override
			public void onEvent(org.vaadin.spring.events.Event<UserRegisteredEvent> event) {
				User registeredUser = event.getPayload().getRegisteredUser();
				email.setValue(registeredUser.getEmail());
				password.setValue(registeredUser.getPassword());
				UI.getCurrent().removeWindow(popup);
			}
		});
	}
	
}
