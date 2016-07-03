package net.bqc.uetchat.utils;

public class CustomTextMessage {
	private CustomRecipient recipient;
	private CustomMessage message;
	
	public CustomTextMessage(CustomRecipient recipient, CustomMessage message) {
		super();
		this.recipient = recipient;
		this.message = message;
	}

	public CustomRecipient getRecipient() {
		return recipient;
	}

	public void setRecipient(CustomRecipient recipient) {
		this.recipient = recipient;
	}

	public CustomMessage getMessage() {
		return message;
	}

	public void setMessage(CustomMessage message) {
		this.message = message;
	}
	
}
