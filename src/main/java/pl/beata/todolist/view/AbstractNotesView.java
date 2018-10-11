package pl.beata.todolist.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.teemusa.flexlayout.AlignContent;
import org.vaadin.teemusa.flexlayout.FlexDirection;
import org.vaadin.teemusa.flexlayout.FlexLayout;
import org.vaadin.teemusa.flexlayout.FlexWrap;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.MyUI;
import pl.beata.todolist.components.NoteComponent;
import pl.beata.todolist.model.Note;
import pl.beata.todolist.service.UserService;

public abstract class AbstractNotesView extends VerticalLayout implements View {

	private static final long serialVersionUID = -3554318271409319860L;
	private FlexLayout tagLayout = new FlexLayout();
	private UserService userService;

	@Autowired
	public AbstractNotesView(UserService userService) {
		this.userService = userService;
		addComponent(tagLayout);

		tagLayout.setFlexDirection(FlexDirection.Row);
		tagLayout.setFlexWrap(FlexWrap.Wrap);
		tagLayout.setAlignContent(AlignContent.FlexStart);

		tagLayout.setWidth("100%");
		tagLayout.setHeight("100%");
	}

	@Override
	@Transactional
	public void enter(ViewChangeEvent event) {
		List<Note> notesList = getNotes();
		for (Note note : notesList) {
			VaadinIcons owner;
			if (userService.getCurrentUser().equals(note.getOwner())) {
				owner = VaadinIcons.PIN_POST;
			} else {
				owner = VaadinIcons.USER_CLOCK;
			}
			NoteComponent noteComponent = new NoteComponent(note, owner);
			noteComponent.setDescription("Owner: " + note.getOwner().getfName() + " " + note.getOwner().getlName());
			tagLayout.addComponent(noteComponent);
			noteComponent.addClickListener(e -> {
				String navigationState = "note/" + note.getId();
				MyUI.getCurrent().getNavigator().navigateTo(navigationState);
			});
		}
	}

	protected abstract List<Note> getNotes();
}
