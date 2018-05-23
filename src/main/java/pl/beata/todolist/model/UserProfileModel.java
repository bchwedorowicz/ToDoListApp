package pl.beata.todolist.model;

public class UserProfileModel {
	
	private String fName;
	private String lName;
	private String email;
	private String password;
	
	public UserProfileModel(String fName, String lName, String email, String password) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
	}

}
