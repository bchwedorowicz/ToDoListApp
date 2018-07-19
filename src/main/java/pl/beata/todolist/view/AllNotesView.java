package pl.beata.todolist.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemusa.flexlayout.AlignContent;
import org.vaadin.teemusa.flexlayout.FlexDirection;
import org.vaadin.teemusa.flexlayout.FlexLayout;
import org.vaadin.teemusa.flexlayout.FlexWrap;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;

import pl.beata.todolist.MyUI;
import pl.beata.todolist.components.NoteComponent;
import pl.beata.todolist.model.Note;
import pl.beata.todolist.model.Task;
import pl.beata.todolist.service.UserService;

@SpringView(name = "allNotes")
@MenuCaption("All Notes")
@MenuIcon(VaadinIcons.NOTEBOOK)
public class AllNotesView extends HorizontalLayout implements View {
	
	private List<Note> notesList = new ArrayList<>();
	private FlexLayout tagLayout = new FlexLayout();
	
	@Autowired
	public AllNotesView(UserService userService) {		
		addComponent(tagLayout);
		
		tagLayout.setFlexDirection(FlexDirection.Row);
		tagLayout.setFlexWrap(FlexWrap.Wrap);
		tagLayout.setAlignContent(AlignContent.FlexStart);
		
		tagLayout.setWidth("100%");
		tagLayout.setHeight("100%");
		
		notesList = userService.getCurrentUser().getNotes();
		for (Note note : notesList) {
			NoteComponent noteComponent = new NoteComponent(note);
			tagLayout.addComponent(noteComponent);
			noteComponent.addClickListener(event -> {
				String navigationState = "note/" + note.getId();
				MyUI.getCurrent().getNavigator().navigateTo(navigationState);
				List<Task> tasks = note.getTasks();
			});
		}
	}
	
	

}
