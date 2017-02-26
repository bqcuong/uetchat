package net.bqc.uetchat.controller;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
import net.bqc.uetchat.is.User;
import net.bqc.uetchat.utils.FBMessageObject;
import net.bqc.uetchat.utils.FbUser;
import net.bqc.uetchat.utils.Helper;

@Controller
public class WebHookController {

	private JsonMapper jsonMapper = new DefaultJsonMapper();
	
	private static final Logger logger = Logger.getLogger(WebHookController.class);

	private static final String TEXT_LEAVE_CHAT_REGEX = "^[Pp][Pp].*";

	private DAOInterface dao = new DAOMySQLImpl();
	private static final Connection con = DBConnector.getInstance().createConnection();

	
	@RequestMapping(value="/random", method=RequestMethod.POST)
	public String random(Model model) {
		try {
			List<User> users = dao.getUsersNotInChat(con);
			logger.info("Run manually random....");
			for (User user : users) {
				if (!user.getInChat().equals("Y")) {
					String partner = dao.getRandomUserNotInChat(con, user.getUserId());
					if (partner != null) {
						startChat(user.getUserId(), partner);
					}
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return "redirect:/random";
	}
	
	@RequestMapping(value="/webhook", method={RequestMethod.GET})
	public String validate(Model model,
			@RequestParam(value="hub.challenge", required=false) String challenge,
			@RequestParam(value="hub.verify_token", required=false) String token) {

		// validate webhook
		if (token != null) {
			if (token.equalsIgnoreCase("cuong_is_the_boss")) {
				model.addAttribute("result", challenge);
				logger.info("Webhook validate done");
			} else {
				model.addAttribute("result", "Error, wrong validation token");
				logger.info("Webhook validate failed");
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

		try {

			logger.info("[Message] " + jsonAsString);
			
			WebhookObject whObject = jsonMapper.toJavaObject(
					jsonAsString, WebhookObject.class);

			List<MessagingItem> messagingItems = whObject.getEntryList().get(0)
					.getMessaging();

			for (MessagingItem messagingItem : messagingItems) {
				String userId = messagingItem.getSender().getId();
				try {
					// add user to db when he sends anything
					String userStatus = dao.getUserStatusById(con, userId);
					if (userStatus == null) {
						joinChat(userId);

					} else {
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
					logger.error("[Exception] " + e.getMessage());
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}


		} catch (Exception e) {
			logger.error("[Exception] " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	private void processTextMessage(String userId, String text) {
		if (text.matches(TEXT_LEAVE_CHAT_REGEX)) {
			leaveChat(userId);
		} else {
			String partner = dao.getPartnerInChat(con, userId);
			if (partner == null) return;

			FBMessageObject.sendMessage(partner, text);
		}
	}

	private void processImageMessage(String userId, String imageUrl) {
		Message imageMessage = FBMessageObject.buildImageMessage(imageUrl);
		sendMessage(userId, imageMessage);
	}

	@Async
	private void joinChat(String userId) {
		boolean isInChat = dao.isInChat(con, userId); 
		
		if (isInChat) {
			dao.removeChatByUserId(con, userId);
		}
		
		FbUser fbUser = Helper.getFbUser(userId);
		boolean success = dao.addUser(con, userId, fbUser);
		logger.info(fbUser);
		if (success == false) {
			return;
		}
		
		FBMessageObject.sendMessage(
				userId,
				FBMessageObject.buildGenericMessage(
						"Th\u1EA3 c\u00E2u...",
						"\u0110ang t\u00ECm c\u00E1 cho b\u1EA1n th\u1EA3 th\u00EDnh...",
						null, null));

		String partner = dao.getRandomUserNotInChat(con, userId);
		if (partner != null) {
			startChat(userId, partner);
		}
	}

	private void startChat(String lhs, String rhs) {
		dao.addUserInChat(con, lhs);
		dao.addUserInChat(con, rhs);
		dao.addChat(con, lhs, rhs);
		
		logger.info("[Mapping] {" + lhs + ", " + rhs + ")");

		FBMessageObject.sendMessage(
				lhs,
				FBMessageObject.buildGenericMessage(
						"Xong!",
						"C\u00E1 \u0111\u00E3 c\u1EAFn c\u00E2u, h\u00E3y gi\u1EADt c\u1EA7n \u0111i n\u00E0o =)) G\u00F5 pp \u0111\u1EC3 k\u1EBFt th\u00FAc.",
						null, null));
		FBMessageObject.sendMessage(
				rhs,
				FBMessageObject.buildGenericMessage(
						"Xong!",
						"C\u00E1 \u0111\u00E3 c\u1EAFn c\u00E2u, h\u00E3y gi\u1EADt c\u1EA7n \u0111i n\u00E0o =)) G\u00F5 pp \u0111\u1EC3 k\u1EBFt th\u00FAc.",
						null, null));
	}

	@Async
	private void leaveChat(String userId) {

		dao.removeUserById(con, userId);
		logger.info("[Destroy] " + userId + " From Users");

		FBMessageObject.sendMessage(
				userId,
				FBMessageObject.buildGenericMessage(
						"B\u1EA1n \u0111\u00E3 ng\u01B0ng th\u1EA3 th\u00EDnh!",
						"G\u00F5 k\u00ED t\u1EF1 b\u1EA5t k\u00EC \u0111\u1EC3 b\u1EAFt \u0111\u1EA7u th\u1EA3 th\u00EDnh ^^",
						null, null));
		
		String partner = dao.getPartnerInChat(con, userId);
		if (partner == null) return;

		dao.removeChatByUserId(con, userId);
		logger.debug("[Destroy] {" + userId + ", " + partner + "}");
		
		dao.removeUserById(con, partner);
		logger.info("[Destroy] " + partner + " From Users");

		FBMessageObject.sendMessage(
				partner,
				FBMessageObject.buildGenericMessage(
						"\u0110\u1ED1i ph\u01B0\u01A1ng \u0111\u00E3 ng\u01B0ng th\u1EA3 th\u00EDnh!",
						"G\u00F5 k\u00ED t\u1EF1 b\u1EA5t k\u00EC \u0111\u1EC3 b\u1EAFt \u0111\u1EA7u th\u1EA3 th\u00EDnh ^^",
						null, null));
	}

	private void sendMessage(String userId, Message message) {
		String partner = dao.getPartnerInChat(con, userId);
		try {
			FBMessageObject.sendMessage(partner, message);
		} catch (Exception e) {
			logger.info(e.getMessage());
//			FBMessageObject.sendErrorMessage(userId);
		}
	}
}
