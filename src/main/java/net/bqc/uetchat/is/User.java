package net.bqc.uetchat.is;

public class User {
	
	private String userId;
	private String inChat;
	private String firstName;
	private String lastName; 
	private String gender = "M";
	
	public User(String userId, String inChat, String gender) {
		super();
		this.userId = userId;
		this.inChat = inChat;
		this.gender = gender;
	}
	
	public User(String userId, String inChat) {
		super();
		this.userId = userId;
		this.inChat = inChat;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInChat() {
		return inChat;
	}

	public void setInChat(String inChat) {
		this.inChat = inChat;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", inChat=" + inChat + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + "]";
	}

}
