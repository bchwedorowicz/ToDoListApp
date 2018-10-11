package pl.beata.todolist.components;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.dao.UserDao;
import pl.beata.todolist.dto.UserDto;
import pl.beata.todolist.model.User;

/**
 * 
 * Represents component used for sharing notes with contacts.
 *
 */
@SpringComponent
@UIScope
public class SharedContactsComponent extends VerticalLayout {

	private static final long serialVersionUID = -7468660058182930104L;
	private TwinColSelect<UserDto> twinColSelect;
	private UserDao userDao;

	@Autowired
	public SharedContactsComponent(UserDao userDao) {
		this.userDao = userDao;
		twinColSelect = new TwinColSelect<>();
		twinColSelect.setItemCaptionGenerator(new ItemCaptionGenerator<UserDto>() {

			private static final long serialVersionUID = 5296695761490804329L;

			@Override
			public String apply(UserDto user) {
				String fName = user.getfName();
				String lName = user.getlName();
				return fName + " " + lName;
			}
		});
		twinColSelect.setRows(6);
		twinColSelect.setLeftColumnCaption("My Contacts:");
		twinColSelect.setRightColumnCaption("Share to:");

		addComponent(twinColSelect);
	}

	/**
	 * 
	 * Gets users who have been selected to share a note.
	 */
	public Set<User> getSharedUsers() {
		Set<UserDto> sharedUsersDto = twinColSelect.getValue();
		Set<User> sharedUsers = new HashSet<>();
		for (UserDto userDto : sharedUsersDto) {
			User user = userDao.findById(userDto.getId());
			sharedUsers.add(user);
		}
		return sharedUsers;
	}

	/**
	 * 
	 * Sets the users sharing the note to the right side of the component.
	 * @param sharedContacts contacts users who share note.
	 */
	public void selectContacts(Set<User> sharedContacts) {
		Set<UserDto> usersDto = getUsersDto(sharedContacts);
		twinColSelect.setValue(usersDto);
	}

	/**
	 * 
	 * Sets users contacts to the left side of the component.
	 * @param contacts users contacts.
	 */
	public void setContacts(Set<User> contacts) {
		Set<UserDto> usersDto = getUsersDto(contacts);
		twinColSelect.setItems(usersDto);
	}

	private Set<UserDto> getUsersDto(Set<User> sharedContacts) {
		Set<UserDto> usersDto = new HashSet<>();
		for (User user : sharedContacts) {
			UserDto userDto = new UserDto(user.getfName(), user.getlName(), user.getId());
			usersDto.add(userDto);
		}
		return usersDto;
	}

}
