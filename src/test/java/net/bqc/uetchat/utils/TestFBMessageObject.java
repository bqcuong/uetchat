//package net.bqc.uetchat.utils;
//
//import com.restfb.DefaultFacebookClient;
//import com.restfb.FacebookClient;
//import com.restfb.Parameter;
//import com.restfb.Version;
//import com.restfb.types.FacebookType;
//import com.restfb.types.send.IdMessageRecipient;
//import com.restfb.types.send.Message;
//
//public class TestFBMessageObject {
//	
//	public static void main(String[] args) {
//		FacebookClient pageClient = new DefaultFacebookClient(
//				Configurer.PAGE_TOKEN, Version.VERSION_2_6);
//		IdMessageRecipient idRecipient = new IdMessageRecipient("1103710079675517");
//		
//		Message genericMessage = FBMessageObject
//				.buildGenericMessage(
//						"Chat v\u1EDBi UETer",
//						"OK, b\u1EA1n h\u00E3y b\u1EA5m n\u00FAt d\u01B0\u1EDBi \u0111\u1EC3 b\u1EAFt \u0111\u1EA7u \u0111i th\u1EA3 th\u00EDnh n\u00E0o ^^!",
//						"B\u1EAFt \u0111\u1EA7u chat",
//						"START_CHAT");
//		Message genericMessage2 = FBMessageObject
//				.buildGenericMessage(
//						"Chat v\u1EDBi UETer",
//						"OK, b\u1EA1n h\u00E3y b\u1EA5m n\u00FAt d\u01B0\u1EDBi \u0111\u1EC3 b\u1EAFt \u0111\u1EA7u \u0111i th\u1EA3 th\u00EDnh n\u00E0o ^^!",
//						null,
//						null);
//		Message imageMessage = FBMessageObject
//				.buildImageMessage("https://scontent.xx.fbcdn.net/t39.1997-6/p100x100/11057086_789355287820052_1582909769_n.png?_nc_ad=z-m");
//		
//		Message textMessage = new Message("Chat v\u1EDBi UETer");
//		
//		pageClient.publish("me/messages",
//				FacebookType.class,
//				Parameter.with("recipient", idRecipient),
//				Parameter.with("message", textMessage));
//	}
//
//}
