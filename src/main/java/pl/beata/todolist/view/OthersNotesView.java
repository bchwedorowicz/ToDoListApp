package pl.beata.todolist.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;

import pl.beata.todolist.model.Note;
import pl.beata.todolist.service.UserService;

@SpringView(name = "othersNotes")
@MenuCaption("Others Notes")
@MenuIcon(VaadinIcons.CLIPBOARD_USER)
public class OthersNotesView extends AbstractNotesView {
	
	private UserService userService;

	@Autowired
	public OthersNotesView(UserService userService) {
		super(userService);
		this.userService = userService;
	}

	@Override
	protected List<Note> getNotes() {
		return userService.getCurrentUser().getSharedNotes();
	}
	
	

}
