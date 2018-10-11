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
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.dao.UserDao;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.UserService;
import pl.beata.todolist.uploader.PhotoUploader;

/**
 * 
 * Represents view with current user data.
 *
 */
@SpringView(name = "myProfile")
@MenuCaption("My Profile")
@MenuIcon(VaadinIcons.USER)
@SpringComponent
@UIScope
public class MyProfileView extends FormLayout implements View {

	private static final long serialVersionUID = -4693929681119337774L;
	private PhotoUploader photoUploader;
	private Upload addEditPhotoBtn = new Upload();
	private Button deletePhotoBtn = new Button("Delete Photo");
	private TextField fName = new TextField("First Name");
	private TextField lName = new TextField("Last Name");
	private TextField email = new TextField("Email");
	private PasswordField password1 = new PasswordField("Password");
	private PasswordField password2 = new PasswordField("Retype password");
	private Button saveBtn = new Button("Save");
	private UserService userService;
	private UserDao userDao;
	private User updatedUser;

	@Autowired
	public MyProfileView(UserService userService, UserDao userDao) {
		this.userService = userService;
		this.userDao = userDao;
		updatedUser = userService.getCurrentUser();
		photoUploader = new PhotoUploader(addEditPhotoBtn);
		addEditPhotoBtn.setButtonCaption("Add/Edit Photo");
		HorizontalLayout photoButtonsLayout = new HorizontalLayout(addEditPhotoBtn, deletePhotoBtn);
		VerticalLayout photoLayout = new VerticalLayout(photoUploader, photoButtonsLayout);
		VerticalLayout vLayout = new VerticalLayout(fName, lName, email, password1, password2, saveBtn);
		HorizontalLayout hLayout = new HorizontalLayout(vLayout, photoLayout);
		for (Component component : vLayout) {
			component.setWidth(7, Unit.CM);
		}
		hLayout.setMargin(true);
		addComponent(hLayout);
		createSaveButtonListener();
		deletePhoto();
	}

	private void setUserValuesToMyProfileView() {

		User currentUser = userService.getCurrentUser();
		fName.setValue(currentUser.getfName());
		lName.setValue(currentUser.getlName());
		email.setValue(currentUser.getEmail());
		password1.setValue(currentUser.getPassword());
		password2.setValue(currentUser.getPassword());
		photoUploader.showPhoto(currentUser.getPhoto());
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

	/**
	 * Save current state to database.
	 */
	public void updateUser() {
		updatedUser.setfName(fName.getValue());
		updatedUser.setlName(lName.getValue());
		updatedUser.setEmail(email.getValue());
		updatedUser.setPhoto(photoUploader.getBytes());
		if (password1.getValue().equals(password2.getValue())) {
			updatedUser.setPassword(password1.getValue());
		}
		userDao.update(updatedUser);
	}

	private void deletePhoto() {
		deletePhotoBtn.addClickListener(event -> {
			updatedUser.setPhoto(null);
			photoUploader.showPhoto(null);
		});
	}

}
