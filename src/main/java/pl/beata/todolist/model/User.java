package pl.beata.todolist.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import pl.beata.todolist.uploader.PhotoUploader;

@Entity
public class User {

	@Lob
	private byte[] photo;
	private String fName;
	private String lName;
	private String email;
	private String password;
	@Id
	@GeneratedValue
	private int id;

	@ManyToMany
	@JoinTable(name = "USER_CONTACTS", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "CONTACT_ID", referencedColumnName = "ID"))
	private Set<User> contacts;

	@OneToMany(mappedBy = "owner")
	private List<Note> notes;
	
	@ManyToMany(mappedBy = "coOwners")
	private List<Note> sharedNotes;

	public Set<User> getContacts() {
		return contacts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public void setContacts(Set<User> contacts) {
		this.contacts = contacts;
	}
	
	public Note getNote(String noteName) {
		Note currentNote = null;
		for (Note note : notes) {
			if (note.getTitle().equals(noteName)) {
				currentNote = note;
			}
		}
		return currentNote;
	}
	
	public List<Note> getSharedNotes() {
		return sharedNotes;
	}
	
	
}
