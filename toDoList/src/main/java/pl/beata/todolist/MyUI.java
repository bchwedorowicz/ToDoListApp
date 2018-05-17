package pl.beata.todolist;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import pl.beata.todolist.form.SignInForm;
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
	private SignInForm signInForm;
	@Autowired
	private MainView mainView;

	public void init(VaadinRequest request) {

		if (userService.isUserLoggedIn()) {
			setContent(mainView.createAppView());
		} else {
			setContent(signInForm);
		}
	}

}