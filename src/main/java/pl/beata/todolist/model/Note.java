package pl.beata.todolist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Note {

	private String title;
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	private User owner;
	
	@ManyToMany
	private Set<User> coOwners;

	@OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void addTask(Task task) {
		task.setNote(this);
		tasks.add(task);
	}
	
	public Set<User> getCoOwners() {
		return coOwners;
	}
	
	public void setCoOwners(Set<User> coOwners) {
		this.coOwners = coOwners;
	}
	
	public void setCoOwner(User coOwner) {
		coOwners.add(coOwner);
	}

}
