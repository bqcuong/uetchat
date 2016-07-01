package net.bqc.uetchat.controller;

import java.sql.Connection;
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

import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.types.send.Message;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.messaging.MessageItem;
import com.restfb.types.webhook.messaging.MessagingAttachment;
import com.restfb.types.webhook.messaging.MessagingItem;

import net.bqc.uetchat.is.DAOInterface;
import net.bqc.uetchat.is.DAOMySQLImpl;
import net.bqc.uetchat.is.DBConnector;
import net.bqc.uetchat.utils.FBMessageObject;
import net.bqc.uetchat.utils.Helper;

@Controller
public class WebHookController {
	
	private JsonMapper jsonMapper = new DefaultJsonMapper();
	
//	private static final String POSTBACK_START_CHAT = "START_CHAT";
//	private static final String POSTBACK_LEAVE_CHAT = "LEAVE_CHAT";
//	private static final String TEXT_START_CHAT_REGEX = "^/[Ii][Nn]";
	private static final String TEXT_LEAVE_CHAT_REGEX = "^[Pp][Pp].*";
	
	private DAOInterface dao = new DAOMySQLImpl();
	private Connection con;
	
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
		
		System.out.println("__WEBHOOK_INFO__USER_SEND__[" + jsonAsString + "]");
		
		try {
			con = DBConnector.getInstance().createConnection();
			
			WebhookObject whObject = jsonMapper.toJavaObject(
					jsonAsString, WebhookObject.class);
			
			MessagingItem messagingItem = whObject.getEntryList().get(0)
					.getMessaging().get(0);
			String userId = messagingItem.getSender().getId();
			// PostbackItem postBackItem = messagingItem.getPostback();
			
			// add user to db when he sends anything
			String userStatus = dao.getUserStatusById(con, userId);
			if (userStatus == null) {
				joinChat(userId);
				
			}
			// else if (postBackItem != null) {
			// 	String postbackValue = postBackItem.getPayload();
			// 	processPostbackMessage(userId, postbackValue);
			// } 
			else {
				MessageItem messageItem = messagingItem.getMessage();
				List<MessagingAttachment> attachments = messageItem.getAttachments(); 
				String text = messageItem.getText();
				
				if (!attachments.isEmpty() && text == null) {
					String imageUrl = attachments.get(0).getPayload().getUrl();
					processImageMessage(userId, imageUrl);
					
				} else {
					processTextMessage(userId, text);
				}
			}
			
		} catch (Exception e) {
			System.out.println("__WEBHOOK_ERROR_JSON__[" + jsonAsString + "]");
			return new ResponseEntity<String>("can not get json", HttpStatus.OK);
				
		} finally {
			DBConnector.closeConnection(con);
		}
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	private void processTextMessage(String userId, String text) {
		System.out.println("__WEBHOOK_TEXT_MESSAGE__[" + userId + "][" + text + "]");
		
		if (text.matches(TEXT_LEAVE_CHAT_REGEX)) {
			leaveChat(userId);
		} else {
			Message textMessage = FBMessageObject.buildTextMessage(text);
			sendMessage(userId, textMessage);
		}
	}
	
	private void processImageMessage(String userId, String imageUrl) {
		System.out.println("__WEBHOOK_IMAGE_MESSAGE__[" + userId + "][" + imageUrl + "]");
		
		Message imageMessage = FBMessageObject.buildImageMessage(imageUrl);
		sendMessage(userId, imageMessage);
	}
	
	// private void processPostbackMessage(String userId, String postbackValue) {
	// 	System.out.println("__WEBHOOK_POSTBACK_MESSAGE__[" + userId + "][" + postbackValue + "]");
		
	// 	if (postbackValue.equals(POSTBACK_LEAVE_CHAT)) {
	// 		leaveChat(userId);
	// 	}
	// }
	
	private void joinChat(String userId) {
		String gender = Helper.getUserGender(userId);
		dao.addUser(con, userId, gender);
		FBMessageObject.sendMessage(
				userId,
				FBMessageObject.buildGenericMessage(
						"Searching...",
						"\u0110ang t\u00ECm c\u00E1 cho b\u1EA1n th\u1EA3 th\u00EDnh...",
						null, null));
		
		String partner = dao.getRandomUserNotInChat(con, userId, gender);
		if (partner != null) {
			startChat(userId, partner);
		}
	}
	
	private void startChat(String lhs, String rhs) {
		String lhsStatus = dao.getUserStatusById(con, lhs);
		String rhsStatus = dao.getUserStatusById(con, rhs);
		
		if (lhsStatus.equals("Y") || rhsStatus.equals("Y")) {
			return;
		}
		
		dao.addUserInChat(con, lhs);
		dao.addUserInChat(con, rhs);
		dao.addChat(con, lhs, rhs);
		FBMessageObject.sendMessage(
				lhs,
				FBMessageObject.buildTextMessage(
						"[BOT]Done! C\u00E1 \u0111\u00E3 c\u1EAFn c\u00E2u, h\u00E3y gi\u1EADt c\u1EA7n \u0111i n\u00E0o =))"));
		FBMessageObject.sendMessage(
				rhs,
				FBMessageObject.buildTextMessage(
						"[BOT]Done! C\u00E1 \u0111\u00E3 c\u1EAFn c\u00E2u, h\u00E3y gi\u1EADt c\u1EA7n \u0111i n\u00E0o =))"));
	}
	
	private void leaveChat(String userId) {
		FBMessageObject.sendMessage(
				userId,
				FBMessageObject.buildTextMessage("[BOT]B\u1EA1n \u0111\u00E3 ng\u01B0ng th\u1EA3 th\u00EDnh!"));
		FBMessageObject.sendMessage(
				userId,
				FBMessageObject.buildGenericMessage(
						"Finished...",
						"G\u00F5 k\u00ED t\u1EF1 b\u1EA5t k\u00EC \u0111\u1EC3 b\u1EAFt \u0111\u1EA7u th\u1EA3 th\u00EDnh ^^ G\u00F5 pp \u0111\u1EC3 k\u1EBFt th\u00FAc",
						null, null));
		
		dao.removeUserById(con, userId);
		
		String partner = dao.getPartnerInChat(con, userId);
		if (partner == null) return;
		
		dao.removeChatByUserId(con, userId, partner);
		dao.removeUserById(con, partner);
		
		FBMessageObject.sendMessage(
				partner,
				FBMessageObject.buildTextMessage("[BOT]\u0110\u1ED1i ph\u01B0\u01A1ng \u0111\u00E3 ng\u01B0ng th\u1EA3 th\u00EDnh!"));
		
		FBMessageObject.sendMessage(
				partner,
				FBMessageObject.buildGenericMessage(
						"Finished...",
						"G\u00F5 k\u00ED t\u1EF1 b\u1EA5t k\u00EC \u0111\u1EC3 b\u1EAFt \u0111\u1EA7u th\u1EA3 th\u00EDnh ^^ G\u00F5 pp \u0111\u1EC3 k\u1EBFt th\u00FAc",
						null, null));
	}
	
	private void sendMessage(String userId, Message message) {
		String partner = dao.getPartnerInChat(con, userId);
		if (partner == null) return;
		
		FBMessageObject.sendMessage(partner, message);
	}
}