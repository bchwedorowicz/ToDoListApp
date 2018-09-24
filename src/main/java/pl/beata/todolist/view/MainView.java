package pl.beata.todolist.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.vaadin.spring.events.EventBus.UIEventBus;

import com.github.appreciated.app.layout.AppLayout;
import com.github.appreciated.app.layout.behaviour.AppLayoutComponent;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.Section;
import com.github.appreciated.app.layout.builder.design.AppLayoutDesign;
import com.github.appreciated.app.layout.builder.elements.builders.SubmenuBuilder;
import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.github.appreciated.app.layout.builder.factories.DefaultSpringNavigationElementInfoProducer;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.github.appreciated.app.layout.component.button.AppBarNotificationButton;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import pl.beata.todolist.MyUI;
import pl.beata.todolist.event.LogOutEvent;
import pl.beata.todolist.service.UserService;

@SpringComponent
@UIScope
@Lazy
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MainView {

	private	SpringViewProvider viewProvider;
	private UserService userSerivce;
	private MenuHeader menuHeader;
	private AppLayoutComponent appLayoutComponent;
	private Button logOutBtn = new Button("Log out");
	private UIEventBus eventBus;
	
	@Autowired
	public MainView(SpringViewProvider viewProvider, UserService userSerivce, UIEventBus eventBus) {
		this.viewProvider = viewProvider;
		this.userSerivce = userSerivce;
		this.eventBus = eventBus;
		appLayoutComponent = createAppView();
		addLogOutBtnListener();
	}
	
	public AppLayoutComponent getAppLayoutComponent() {
		return appLayoutComponent;
	}

	private AppLayoutComponent createAppView() {
	        
		DefaultNotificationHolder notifications = new DefaultNotificationHolder();
		
		menuHeader = new MenuHeader("Hello " + userSerivce.getCurrentUser().getfName() + "!", new ThemeResource("logo.png"));
		AppLayoutComponent layout = AppLayout.getCDIBuilder(Behaviour.LEFT_RESPONSIVE_HYBRID)
				.withViewProvider(() -> viewProvider)
				.withNavigationElementInfoProducer(new DefaultSpringNavigationElementInfoProducer())
				.withTitle("TO DO LIST APP").addToAppBar(logOutBtn).addToAppBar(new AppBarNotificationButton(notifications, true))
				.withDesign(AppLayoutDesign.MATERIAL).withNavigatorConsumer(navigator -> {
					/* Do something with it */})
				.withNavigatorConsumer(navigator -> {/* Do something with it */})
				.add(menuHeader, Section.HEADER)
				.add("Add New Note", VaadinIcons.PLUS_CIRCLE_O, EditNoteView.class)
				.add("My Profile", VaadinIcons.USER, MyProfileView.class)
				.add(SubmenuBuilder.get("Notes", VaadinIcons.NOTEBOOK)
						.add("All Notes", VaadinIcons.NOTEBOOK, AllNotesView.class)
						.add("My Notes", VaadinIcons.CLIPBOARD_TEXT, MyNotesView.class)
						.add("Others Notes", VaadinIcons.CLIPBOARD_USER, OthersNotesView.class).build())
				.add("Contacts", VaadinIcons.USERS, ContactsView.class)
				.build();
		return layout;
	}
	
	private void addLogOutBtnListener() {
		logOutBtn.addClickListener(event -> {
			userSerivce.logout();
			LogOutEvent logOutEvent = new LogOutEvent();
			eventBus.publish(this, logOutEvent);
		});
	}
}
