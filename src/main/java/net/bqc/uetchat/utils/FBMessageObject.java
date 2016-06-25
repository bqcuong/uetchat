package net.bqc.uetchat.utils;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.send.Bubble;
import com.restfb.types.send.GenericTemplatePayload;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.ImageAttachment;
import com.restfb.types.send.Message;
import com.restfb.types.send.PostbackButton;
import com.restfb.types.send.TemplateAttachment;

public class FBMessageObject {
	private static FacebookClient pageClient = new DefaultFacebookClient(
			Configurer.PAGE_TOKEN, Version.VERSION_2_6);
	
	public static void sendMessage(String recipient, Message message) {
		pageClient.publish("me/messages",
				FacebookType.class,
				Parameter.with("recipient", new IdMessageRecipient(recipient)),
				Parameter.with("message", message));
	}
	
	public static Message buildGenericMessage(
			String title, String subtitle, String postbackTitle, String postbackValue) {

		Bubble bubble = new Bubble(title);
		bubble.setSubtitle(subtitle);
		if (postbackTitle != null && postbackValue != null) {
			PostbackButton postbackBtn = new PostbackButton(postbackTitle, postbackValue); 
			bubble.addButton(postbackBtn);
		}
		GenericTemplatePayload payload = new GenericTemplatePayload();
		payload.addBubble(bubble);
		TemplateAttachment attachment = new TemplateAttachment(payload);
		
		return new Message(attachment);
	}
	
	public static Message buildImageMessage(String imageUrl) {
		ImageAttachment image = new ImageAttachment(imageUrl);
		return new Message(image);
	}
	
	public static Message buildTextMessage(String text) {
		return new Message(text);
	}
}
