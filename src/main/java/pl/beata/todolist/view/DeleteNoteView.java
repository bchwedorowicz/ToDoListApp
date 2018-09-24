package pl.beata.todolist.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import pl.beata.todolist.MyUI;
import pl.beata.todolist.dao.NoteDao;
import pl.beata.todolist.event.CloseDeleteWindowEvent;
import pl.beata.todolist.event.DeleteNoteEvent;
import pl.beata.todolist.model.Note;

@SpringComponent
@UIScope
public class DeleteNoteView extends Window {

	private Label question = new Label("Are you sure you want to delete this note?");
	private Button yesBtn = new Button("Yes");
	private Button noBtn = new Button("No");
	private UIEventBus eventBus;
	private NoteDao noteDao;

	@Autowired
	public DeleteNoteView(UIEventBus eventBus, NoteDao notetDao) {
		this.eventBus = eventBus;
		this.noteDao = notetDao;
		HorizontalLayout hLayout = new HorizontalLayout(yesBtn, noBtn);
		VerticalLayout vLayout = new VerticalLayout(question, hLayout);
		vLayout.setComponentAlignment(question, Alignment.MIDDLE_CENTER);
		vLayout.setComponentAlignment(hLayout, Alignment.MIDDLE_CENTER);
		setContent(vLayout);
		subscribeDeleteNoteEvent();
		subscribeCloseDeleteWindowEvent();
	}

	public Button getYesBtn() {
		return yesBtn;
	}

	public Button getNoBtn() {
		return noBtn;
	}

	private void subscribeDeleteNoteEvent() {
		eventBus.subscribe(new EventBusListener<DeleteNoteEvent>() {

			@Override
			public void onEvent(org.vaadin.spring.events.Event<DeleteNoteEvent> event) {
				Note deletedNote = event.getPayload().getDeletedNote();
				noteDao.delete(deletedNote);
				Notification.show("Note deleted");
				MyUI.getCurrent().removeWindow(DeleteNoteView.this);
				String navigationState = "allNotes";
				MyUI.getCurrent().getNavigator().navigateTo(navigationState);
			}
		});
	}

	private void subscribeCloseDeleteWindowEvent() {
		eventBus.subscribe(new EventBusListener<CloseDeleteWindowEvent>() {

			@Override
			public void onEvent(org.vaadin.spring.events.Event<CloseDeleteWindowEvent> event) {
				MyUI.getCurrent().removeWindow(DeleteNoteView.this);
			}
		});
	}

}
