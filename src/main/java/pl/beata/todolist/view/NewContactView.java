package pl.beata.todolist.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class NewContactView extends VerticalLayout implements View {
	
	private TextField email = new TextField("Email");
	private Button addContactBtn = new Button("Add");
	
	public NewContactView() {
		addComponents(email, addContactBtn);
	}
	
	public Button getAddContactBtn() {
		return addContactBtn;
	}
	
	public String getEmailValue() {
		return email.getValue();
	}
}
