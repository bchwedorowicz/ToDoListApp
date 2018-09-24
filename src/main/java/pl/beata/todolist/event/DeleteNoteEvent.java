package pl.beata.todolist.event;

import pl.beata.todolist.model.Note;

public class DeleteNoteEvent {

	private Note deletedNote;

	public DeleteNoteEvent(Note deletedNote) {
		this.deletedNote = deletedNote;
	}

	public Note getDeletedNote() {
		return deletedNote;
	}

}
