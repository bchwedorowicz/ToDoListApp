package pl.beata.todolist.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import pl.beata.todolist.event.LogInEvent;
import pl.beata.todolist.event.UserRegisteredEvent;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.UserService;

@SpringComponent
@UIScope
public class LogInForm extends VerticalLayout {

	private TextField email = new TextField("Email");
	private PasswordField password = new PasswordField("Password");
	private Button loginBtn = new Button("Login");
	private Button registrationBtn = new Button("Register");
	private HorizontalLayout registrationLayout = new HorizontalLayout();
	private UserService userService;
	private UIEventBus eventBus;
	private Window popup;

	@Autowired
	public LogInForm(RegistrationForm registrationForm, UserService userService, UIEventBus eventBus) {
		this.userService = userService;
		this.eventBus = eventBus;
		email.setWidth(7, UNITS_CM);
		password.setWidth(7, UNITS_CM);
		addComponents(email, password, loginBtn, createRegistrationLayout());
		this.setComponentAlignment(email, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(loginBtn, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(registrationLayout, Alignment.MIDDLE_CENTER);
		popup = new Window("Registration", registrationForm);
		popup.setModal(true);
		popup.setHeight(15, UNITS_CM);
		popup.setWidth(10, UNITS_CM);
		popup.setResponsive(true);
		email.setValue("asia.pipiszka@gmail.com");
		password.setValue("a");
		openRegistrationWindowListener();
		addLoginButtonListener();
		subscribeUserRegistrationEvent();
	}

	private HorizontalLayout createRegistrationLayout() {
		Label newCustomerLabel = new Label("New Customer?");
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
				LogInEvent loggerEvent = new LogInEvent();
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
