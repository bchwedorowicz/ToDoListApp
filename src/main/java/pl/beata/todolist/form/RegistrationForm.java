package pl.beata.todolist.form;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;

import pl.beata.todolist.view.MyProfileView;

@SpringComponent
@UIScope
public class RegistrationForm extends MyProfileView {

	public RegistrationForm() {
		super();
	}
	
	public Button getSaveBtn() {
		return saveBtn;
	}
	
	public String getEmailValue() {
		return email.getValue();
	}
	
	public String getPasswordValue() {
		return password1.getValue();
	}

}
