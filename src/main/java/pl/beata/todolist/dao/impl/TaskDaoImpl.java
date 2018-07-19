package pl.beata.todolist.dao.impl;

import org.springframework.stereotype.Repository;

import pl.beata.todolist.dao.AbstractDao;
import pl.beata.todolist.dao.TaskDao;
import pl.beata.todolist.model.Task;

@Repository
public class TaskDaoImpl extends AbstractDao<Task> implements TaskDao {

	public TaskDaoImpl() {
		super(Task.class);
	}

}
