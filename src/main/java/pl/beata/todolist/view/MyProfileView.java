package pl.beata.todolist.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import pl.beata.todolist.dao.UserDao;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.UserService;

@SpringView(name = "myProfile")
@MenuCaption("My Profile")
@MenuIcon(VaadinIcons.USER)
@SpringComponent
@UIScope
public class MyProfileView extends FormLayout implements View {

	private Image photo = new Image("Photo");
	private TextField fName = new TextField("First Name");
	private TextField lName = new TextField("Last Name");
	private TextField email = new TextField("Email");
	private PasswordField password1 = new PasswordField("Password");
	private PasswordField password2 = new PasswordField("Retype password");
	private Button saveBtn = new Button("Save");
	private UserService userService;
	private UserDao userDao;
	

	@Autowired
	public MyProfileView(UserService userService, UserDao userDao) {
		this.userService = userService;
		this.userDao = userDao;
		addComponents(photo, fName, lName, email, password1, password2, saveBtn);
		createSaveButtonListener();
	}

	private void setUserValuesToMyProfileView() {

		User currentUser = userService.getCurrentUser();
		fName.setValue(currentUser.getfName());
		lName.setValue(currentUser.getlName());
		email.setValue(currentUser.getEmail());
		password1.setValue(currentUser.getPassword());
		password2.setValue(currentUser.getPassword());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setUserValuesToMyProfileView();
	}

	private void createSaveButtonListener() {
		saveBtn.addClickListener(e -> {
			updateUser();
		});
	}

	public void updateUser() {
		User updatedUser = userService.getCurrentUser();
		updatedUser.setfName(fName.getValue());
		updatedUser.setlName(lName.getValue());
		updatedUser.setEmail(email.getValue());
		if (password1.getValue().equals(password2.getValue())) {
			updatedUser.setPassword(password1.getValue());
		}
		userDao.update(updatedUser);
	}

}
