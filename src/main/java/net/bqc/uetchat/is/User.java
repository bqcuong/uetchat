package net.bqc.uetchat.is;

public class User {
	
	private String userId;
	private char inChat;
	
	public User(String userId, char inChat) {
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

	public char getInChat() {
		return inChat;
	}

	public void setInChat(char inChat) {
		this.inChat = inChat;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", inChat=" + inChat + "]";
	}
}