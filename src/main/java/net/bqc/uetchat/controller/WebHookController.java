package net.bqc.uetchat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Version;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.messaging.MessageItem;
import com.restfb.types.webhook.messaging.MessagingAttachment;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.restfb.types.webhook.messaging.PostbackItem;

import net.bqc.uetchat.utils.Configurer;

@Controller
public class WebHookController {
	
	private JsonMapper jsonMapper = new DefaultJsonMapper();
	
	private static final String POSTBACK_START_CHAT = "START_CHAT";
	private static final String POSTBACK_LEAVE_CHAT = "LEAVE_CHAT";
	private static final String TEXT_START_CHAT_REGEX = "^/[Ii][Nn]";
	private static final String TEXT_LEAVE_CHAT_REGEX = "^/[Oo][Uu][Tt]";
	
	
	private FacebookClient pageClient = new DefaultFacebookClient(
			Configurer.PAGE_TOKEN, Version.VERSION_2_6);
	
	@RequestMapping(value="/webhook", method={RequestMethod.GET})
	public String validate(Model model,
			@RequestParam(value="hub.challenge", required=false) String challenge,
			@RequestParam(value="hub.verify_token", required=false) String token) {
		
		// validate webhook
		if (token != null) {
			if (token.equalsIgnoreCase("cuong_is_the_boss")) {
				model.addAttribute("result", challenge);
				System.out.println("__WEBHOOK_INFO__USER_SEND__validate done");
			} else {
				model.addAttribute("result", "Error, wrong validation token");
				System.out.println("__WEBHOOK_INFO__USER_SEND__validate failed");
			}
			
			return "validate";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/webhook",
			method={RequestMethod.POST},
			consumes="application/json",
			produces="application/json")
	@ResponseBody
	public ResponseEntity<String> receive(Model model,
			@RequestBody final String jsonAsString){
		
		System.out.println("__WEBHOOK_INFO__USER_SEND__" + jsonAsString);
		
		try {
			WebhookObject whObject = jsonMapper.toJavaObject(
					jsonAsString, WebhookObject.class);
			
			MessagingItem messagingItem = whObject.getEntryList().get(0)
					.getMessaging().get(0);
			String userId = messagingItem.getSender().getId();
			PostbackItem postBackItem = messagingItem.getPostback();

			if (postBackItem != null) {
				String postbackValue = postBackItem.getPayload();
				processPostbackMessage(userId, postbackValue);
				
			} else {
				MessageItem messageItem = messagingItem.getMessage();
				List<MessagingAttachment> attachments = messageItem.getAttachments(); 
				
				if (!attachments.isEmpty()) {
					String imageUrl = attachments.get(0).getPayload().getUrl();
					processImageMessage(userId, imageUrl);
					
				} else {
					String text = messageItem.getText();
					processTextMessage(userId, text);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("can not get json", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	private void processTextMessage(String userId, String text) {
		System.out.println("__WEBHOOK_TEXT_MESSAGE__[" + userId + "][" + text + "]");
		
		if (text.matches(TEXT_START_CHAT_REGEX)) {
			startChat(userId);
		} else if (text.matches(TEXT_LEAVE_CHAT_REGEX)) {
			leaveChat(userId);
		} else {
			
		}
	}
	
	private void processImageMessage(String userId, String imageUrl) {
		System.out.println("__WEBHOOK_IMAGE_MESSAGE__[" + userId + "][" + imageUrl + "]");
	}
	
	private void processPostbackMessage(String userId, String postbackValue) {
		System.out.println("__WEBHOOK_POSTBACK_MESSAGE__[" + userId + "][" + postbackValue + "]");
		
		if (postbackValue.equals(POSTBACK_START_CHAT)) {
			startChat(userId);
		} else if (postbackValue.equals(POSTBACK_LEAVE_CHAT)) {
			leaveChat(userId);
		}
	}
	
	private void startChat(String userId) {
		
	}
	
	private void leaveChat(String userId) {
		
	}
	
	private void sendMessage(String userId) {
		
	}
}
