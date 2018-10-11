package pl.beata.todolist.dto;

public class UserDto {
	
	private String fName;
	private String lName;
	private int id;
	
	public UserDto(String fName, String lName, int id) {
		this.fName = fName;
		this.lName = lName;
		this.id = id;
	}
	
	public String getfName() {
		return fName;
	}
	
	public String getlName() {
		return lName;
	}
	
	public int getId() {
		return id;
	}
	
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public void setlName(String lName) {
		this.lName = lName;
	}
	
	@Override
	public String toString() {
		return fName + " " + lName;
	}
	
	@Override
	public int hashCode() {
		return fName.hashCode() + lName.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserDto)) {
			return false;
		}
		UserDto userDto = (UserDto) obj;
		if (!this.fName.equals(userDto.fName) || !this.lName.equals(userDto.lName) || this.id != userDto.id) {
			return false;
		}
		return true;
	}

}
