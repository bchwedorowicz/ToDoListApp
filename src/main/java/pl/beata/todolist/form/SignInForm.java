package pl.beata.todolist.form;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import pl.beata.todolist.view.MainView;

@SpringComponent
@UIScope
public class SignInForm extends FormLayout {

	private TextField email = new TextField("Email");
	private PasswordField password = new PasswordField("Password");
	private Button loginBtn = new Button("Login");
	private Button registrationBtn = new Button("Register");
	private Window popup;
	private RegistrationForm registrationForm;
	private MainView mainView;

	@Autowired
	public SignInForm(RegistrationForm registrationForm, MainView mainView) {
		this.mainView = mainView;
		this.registrationForm = registrationForm;
		addComponents(email, password, loginBtn, createRegistrationLayout());
		openRegistrationWindowListener();
		closeRegistrationWindowListener();
		setEmailPasswordValueListener();
		openMainViewListener();
	}

	private HorizontalLayout createRegistrationLayout() {
		Label newCustomerLabel = new Label("New Customer?");
		HorizontalLayout registrationLayout = new HorizontalLayout();
		registrationLayout.addComponents(newCustomerLabel, registrationBtn);
		return registrationLayout;
	}

	private void openRegistrationWindowListener() {
		registrationBtn.addClickListener(event -> {
			popup = openInModalWindow("Registration", registrationForm);
		});
	}

	private void closeRegistrationWindowListener() {
		registrationForm.getSaveBtn().addClickListener(e -> {
			UI.getCurrent().removeWindow(popup);
		});
	}

	private void setEmailPasswordValueListener() {
		registrationForm.getSaveBtn().addClickListener(e -> {
			String emailValue = registrationForm.getEmailValue();
			String passwordValue = registrationForm.getPasswordValue();
			email.setValue(emailValue);
			password.setValue(passwordValue);
		});
	}

	private Window openInModalWindow(String windowName, Component popupComponent) {

		Window popup = new Window(windowName, popupComponent);
		popup.setModal(true);
		UI.getCurrent().addWindow(popup);
		return popup;
	}
	
	private void openMainViewListener() {
		loginBtn.addClickListener(e -> {
			mainView.createAppView();
		});
	}
}
