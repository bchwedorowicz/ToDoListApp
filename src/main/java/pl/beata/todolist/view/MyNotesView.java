package pl.beata.todolist.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;

import pl.beata.todolist.model.Note;
import pl.beata.todolist.service.UserService;

/**
 * 
 * Represents view of private notes and notes shared to others, where current user is owner.
 *
 */
@SpringView(name = "myNotes")
@MenuCaption("My Notes")
@MenuIcon(VaadinIcons.CLIPBOARD_TEXT)
public class MyNotesView extends AbstractNotesView {

	private static final long serialVersionUID = -1600428294514482285L;
	private UserService userService;

	@Autowired
	public MyNotesView(UserService userService) {
		super(userService);
		this.userService = userService;
	}

	@Override
	protected List<Note> getNotes() {
		return userService.getCurrentUser().getNotes();
	}


}
