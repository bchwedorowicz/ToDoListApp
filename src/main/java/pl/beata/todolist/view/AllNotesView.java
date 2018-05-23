package pl.beata.todolist.view;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "allNotes")
@MenuCaption("All Notes")
@MenuIcon(VaadinIcons.NOTEBOOK)
public class AllNotesView extends VerticalLayout implements View{

}