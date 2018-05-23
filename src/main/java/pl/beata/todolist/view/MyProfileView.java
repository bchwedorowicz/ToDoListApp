package pl.beata.todolist.view;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

@SpringView(name = "myProfile")
@MenuCaption("My Profile")
@MenuIcon(VaadinIcons.USER)
public class MyProfileView extends FormLayout implements View {

	protected Image photo = new Image("Photo");
	protected TextField fName = new TextField("First Name");
	protected TextField lName = new TextField("Last Name");
	protected TextField email = new TextField("Email");
	protected PasswordField password1 = new PasswordField("Password");
	protected PasswordField password2 = new PasswordField("Retype password");
	protected Button saveBtn = new Button("Save");

	public MyProfileView() {
		addComponents(photo, fName, lName, email, password1, password2, saveBtn);
	}

}
