package net.bqc.uetchat.is;

public class User {
	
	private String userId;
	private String inChat;
	
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", inChat=" + inChat + "]";
	}
	
}
