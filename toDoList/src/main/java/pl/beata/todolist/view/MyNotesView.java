package pl.beata.todolist.view;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "myNotes")
@MenuCaption("My Notes")
@MenuIcon(VaadinIcons.CLIPBOARD_TEXT)
public class MyNotesView extends VerticalLayout implements View {

}
