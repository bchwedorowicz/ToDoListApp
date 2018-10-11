package pl.beata.todolist.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventBusListener;

import com.github.appreciated.app.layout.AppLayout;
import com.github.appreciated.app.layout.behaviour.AppLayoutComponent;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.Section;
import com.github.appreciated.app.layout.builder.design.AppLayoutDesign;
import com.github.appreciated.app.layout.builder.elements.builders.SubmenuBuilder;
import com.github.appreciated.app.layout.builder.entities.DefaultNotification;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.github.appreciated.app.layout.builder.entities.NotificationHolder;
import com.github.appreciated.app.layout.builder.entities.NotificationHolder.NotificationListener;
import com.github.appreciated.app.layout.builder.factories.DefaultSpringNavigationElementInfoProducer;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.github.appreciated.app.layout.component.button.AppBarNotificationButton;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.dao.NotificationDao;
import pl.beata.todolist.event.LogInEvent;
import pl.beata.todolist.event.LogOutEvent;
import pl.beata.todolist.model.BellNotification;
import pl.beata.todolist.notification.BellDefaultNotification;
import pl.beata.todolist.service.UserService;

/**
 * 
 * Represents main view, with menu and appbar.
 *
 */
@SpringComponent
@UIScope
@Lazy
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MainView {

	private SpringViewProvider viewProvider;
	private UserService userSerivce;
	private VerticalLayout menuHeaderLayout;
	private AppLayoutComponent appLayoutComponent;
	private Button logOutBtn = new Button("Log out");
	private UIEventBus eventBus;
	private NotificationDao notificationDao;
	private DefaultNotificationHolder notifications;

	@Autowired
	public MainView(SpringViewProvider viewProvider, UserService userSerivce, UIEventBus eventBus,
			NotificationDao notificationDao) {
		this.viewProvider = viewProvider;
		this.userSerivce = userSerivce;
		this.eventBus = eventBus;
		this.notificationDao = notificationDao;
		appLayoutComponent = createAppView();
		addLogOutBtnListener();
		subscribeUserLoginEvent();
		fillCurrentUserData();
	}

	public AppLayoutComponent getAppLayoutComponent() {
		return appLayoutComponent;
	}

	private AppLayoutComponent createAppView() {

		notifications = new DefaultNotificationHolder();

		notifications.addStatusListener(new NotificationListener() {

			@Override
			public void onUnreadCountChange(@SuppressWarnings("rawtypes") NotificationHolder holder) {
			}

			@Override
			public void onNotificationChanges(@SuppressWarnings("rawtypes") NotificationHolder newStatus) {
				List<DefaultNotification> allNotifications = notifications.getNotifications();
				for (DefaultNotification defaultNotification : allNotifications) {
					if (!defaultNotification.isUnnread()) {
						BellDefaultNotification bellDefaultNotification = (BellDefaultNotification) defaultNotification;
						BellNotification bellNotification = notificationDao.findById(bellDefaultNotification.getId());
						notificationDao.delete(bellNotification);
					}
				}

			}
		});

		AppBarNotificationButton bell = new AppBarNotificationButton(notifications, true);

		menuHeaderLayout = new VerticalLayout();
		
		AppLayoutComponent layout = AppLayout.getCDIBuilder(Behaviour.LEFT_RESPONSIVE_HYBRID)
				.withViewProvider(() -> viewProvider)
				.withNavigationElementInfoProducer(new DefaultSpringNavigationElementInfoProducer())
				.withTitle("TO DO LIST APP").addToAppBar(logOutBtn).addToAppBar(bell)
				.withDesign(AppLayoutDesign.MATERIAL).withNavigatorConsumer(navigator -> {
					/* Do something with it */})
				.withNavigatorConsumer(navigator -> {
					/* Do something with it */})
				.add(menuHeaderLayout, Section.HEADER).add("Add New Note", VaadinIcons.PLUS_CIRCLE_O, EditNoteView.class)
				.add("My Profile", VaadinIcons.USER, MyProfileView.class)
				.add(SubmenuBuilder.get("Notes", VaadinIcons.NOTEBOOK)
						.add("All Notes", VaadinIcons.NOTEBOOK, AllNotesView.class)
						.add("My Notes", VaadinIcons.CLIPBOARD_TEXT, MyNotesView.class)
						.add("Others Notes", VaadinIcons.CLIPBOARD_USER, OthersNotesView.class).build())
				.add("Contacts", VaadinIcons.USERS, ContactsView.class).build();
		return layout;
	}

	private void addLogOutBtnListener() {
		logOutBtn.addClickListener(event -> {
			userSerivce.logout();
			LogOutEvent logOutEvent = new LogOutEvent();
			eventBus.publish(this, logOutEvent);
		});
	}
	
	private void subscribeUserLoginEvent() {
		eventBus.subscribe(new EventBusListener<LogInEvent>() {

			private static final long serialVersionUID = -8759443031294768607L;

			@Override
			public void onEvent(Event<LogInEvent> event) {
				fillCurrentUserData();	
			}
		});
	}
	
	private void fillCurrentUserData() {
		MenuHeader menuHeader = new MenuHeader("Hello " + userSerivce.getCurrentUser().getfName() + "!",
				new ThemeResource("logo.png"));
		menuHeaderLayout.removeAllComponents();
		menuHeaderLayout.addComponent(menuHeader);
		
		notifications.clearNotifications();
		
		for (BellNotification notification : userSerivce.getCurrentUser().getNotifications()) {
			String title = notification.getTitle();
			String description = notification.getDescription();
			int id = notification.getId();

			BellDefaultNotification defaultNotification = new BellDefaultNotification(title, description, id);

			notifications.addNotification(defaultNotification);
		}
	}
}
