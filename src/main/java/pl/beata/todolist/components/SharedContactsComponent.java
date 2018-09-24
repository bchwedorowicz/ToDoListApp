package pl.beata.todolist.components;

import java.util.Set;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import pl.beata.todolist.model.User;

@SpringComponent
@UIScope
public class SharedContactsComponent extends VerticalLayout {
	
	private TwinColSelect<User> sample;
	
	public SharedContactsComponent() {
		sample = new TwinColSelect<>();
		sample.setItemCaptionGenerator(new ItemCaptionGenerator<User>() {
			
			@Override
			public String apply(User item) {
				String fName = item.getfName();
				String lName = item.getlName();
				return fName + " " + lName;
			}
		});
		sample.setRows(6);
		sample.setLeftColumnCaption("My Contacts:");
		sample.setRightColumnCaption("Share to:");
		
		addComponent(sample);
	}
	
	public Set<User> getSharedUsers(){
		return sample.getValue();
	}
	
	public void selectContacts(Set<User> sharedContacts) {
			sample.setValue(sharedContacts);	
	}
	public void setSharedContacts(Set<User> sharedContacts) {
		sample.setItems(sharedContacts);
	}
	
}
