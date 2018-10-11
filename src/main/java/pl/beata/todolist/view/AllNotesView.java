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
 * Represents all notes view: 
 * notes from current user shared to other contacts, 
 * notes shared from other contacts to current user
 * and user's private notes.
 *
 */
@SpringView(name = "allNotes")
@MenuCaption("All Notes")
@MenuIcon(VaadinIcons.NOTEBOOK)
public class AllNotesView extends AbstractNotesView {

	private static final long serialVersionUID = 3800298948637628510L;
	private UserService userService;

	@Autowired
	public AllNotesView(UserService userService) {
		super(userService);
		this.userService = userService;

	}

	@Override
	protected List<Note> getNotes() {
		List<Note> allNotes = userService.getCurrentUser().getNotes();
		allNotes.addAll(userService.getCurrentUser().getSharedNotes());
		return allNotes;
	}

}
