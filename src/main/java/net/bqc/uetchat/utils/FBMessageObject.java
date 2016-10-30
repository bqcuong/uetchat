package net.bqc.uetchat.utils;

import org.springframework.web.client.RestTemplate;

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
	
	private static RestTemplate rstTempl = new RestTemplate();
	private static final String API = "https://graph.facebook.com/v2.6/me/messages?access_token=" + Configurer.PAGE_TOKEN;
	
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
	
	public static void sendMessage(String rcp, String text) {
		CustomRecipient recipient = new CustomRecipient(rcp);
		CustomMessage message = new CustomMessage(text);
		CustomTextMessage textMessage = new CustomTextMessage(recipient, message);
		
		rstTempl.postForEntity(API, textMessage, String.class);
	}
}
