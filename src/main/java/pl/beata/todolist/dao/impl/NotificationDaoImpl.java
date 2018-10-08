package pl.beata.todolist.dao.impl;

import org.springframework.stereotype.Repository;

import pl.beata.todolist.dao.AbstractDao;
import pl.beata.todolist.dao.NotificationDao;
import pl.beata.todolist.model.BellNotification;

@Repository
public class NotificationDaoImpl extends AbstractDao<BellNotification> implements NotificationDao {

	public NotificationDaoImpl() {
		super(BellNotification.class);
	}

}
