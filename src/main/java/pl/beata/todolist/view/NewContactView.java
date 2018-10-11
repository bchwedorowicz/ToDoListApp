package pl.beata.todolist.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * Represents window view to adding new contact.
 *
 */
@SpringComponent
@UIScope
public class NewContactView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1531926693653295778L;
	private TextField email = new TextField("Email");
	private Button addContactBtn = new Button("Add");
	
	public NewContactView() {
		addComponents(email, addContactBtn);
	}
	
	/**
	 * 
	 * Gets button to add contact.
	 */
	public Button getAddContactBtn() {
		return addContactBtn;
	}
	
	/**
	 * 
	 * Gets provided email.
	 */
	public String getEmailValue() {
		return email.getValue();
	}
}
