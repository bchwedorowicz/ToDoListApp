package pl.beata.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.events.EventBusListener;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import pl.beata.todolist.event.LogOutEvent;
import pl.beata.todolist.event.LogInEvent;
import pl.beata.todolist.form.LogInForm;
import pl.beata.todolist.service.UserService;
import pl.beata.todolist.view.MainView;

@Push
@PushStateNavigation
@SpringUI
@Viewport("width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no")
@Theme("demo")
@Title("App Layout Demo")
public class MyUI extends UI {

	@Autowired
	private UserService userService;
	@Autowired
	private LogInForm logInForm;
	@Autowired
	private MainView mainView;
	@Autowired
	private UIEventBus eventBus;

	@Override
	public void init(VaadinRequest request) {

		if (userService.isUserLoggedIn()) {
			setContent(mainView.getAppLayoutComponent());
		} else {
			setContent(logInForm);
		}		
		subscribeUserLoginEvent();
		subscribeLogOutBtnEvent();
	}
	
	private void subscribeUserLoginEvent() {
		EventBusListener<LogInEvent> listener = new EventBusListener<LogInEvent>() {

			@Override
			public void onEvent(org.vaadin.spring.events.Event<LogInEvent> event) {
				setContent(mainView.getAppLayoutComponent());
				MyUI.getCurrent().getNavigator().navigateTo("myNotes");
			}
			
		};
		eventBus.subscribe(listener);
	}
	
	private void subscribeLogOutBtnEvent() {
		eventBus.subscribe(new EventBusListener<LogOutEvent>() {

			@Override
			public void onEvent(org.vaadin.spring.events.Event<LogOutEvent> event) {
				setContent(logInForm);
			}
			
		});
	}

}