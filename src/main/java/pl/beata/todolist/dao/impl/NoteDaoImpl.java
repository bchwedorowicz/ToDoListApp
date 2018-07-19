package pl.beata.todolist.dao.impl;

import org.springframework.stereotype.Repository;

import pl.beata.todolist.dao.AbstractDao;
import pl.beata.todolist.dao.NoteDao;
import pl.beata.todolist.model.Note;

@Repository
public class NoteDaoImpl extends AbstractDao<Note> implements NoteDao {

	public NoteDaoImpl() {
		super(Note.class);
	}

}
