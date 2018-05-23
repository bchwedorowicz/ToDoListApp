package pl.beata.todolist.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

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

@SpringComponent
@UIScope
public class MainView extends AbstractView {

	@Autowired
	SpringViewProvider viewProvider;

	public AppLayoutComponent createAppView() {
		DefaultNotificationHolder notifications = new DefaultNotificationHolder();
		DefaultBadgeHolder badge = new DefaultBadgeHolder();

		AppLayoutComponent layout = AppLayout.getCDIBuilder(Behaviour.LEFT_RESPONSIVE_HYBRID)
				.withViewProvider(() -> viewProvider)
				.withNavigationElementInfoProducer(new DefaultSpringNavigationElementInfoProducer())
				.withTitle("App Layout Basic Example").addToAppBar(new AppBarNotificationButton(notifications, true))
				.withDesign(AppLayoutDesign.MATERIAL).withNavigatorConsumer(navigator -> {
					/* Do something with it */})
				.add(new MenuHeader("Version 0.9.21", new ThemeResource("logo.png")), Section.HEADER)
				.add("My Profile", VaadinIcons.USER, badge, MyProfileView.class)
				.add(SubmenuBuilder.get("Notes", VaadinIcons.NOTEBOOK)
						.add("All Notes", VaadinIcons.NOTEBOOK, AllNotesView.class)
						.add("My Notes", VaadinIcons.CLIPBOARD_TEXT, MyNotesView.class)
						.add("Other Notes", VaadinIcons.CLIPBOARD_USER, OtherNotesView.class).build())
				.add("Contacts", VaadinIcons.USERS, ContactsView.class).build();
		return layout;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> arg0, HttpServletRequest arg1, HttpServletResponse arg2)
			throws Exception {

	}
}
