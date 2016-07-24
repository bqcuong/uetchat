package net.bqc.uetchat.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.bqc.uetchat.entity.SendToAllMessage;

public class TestJsonMapper {

	public static void main(String[] args) {
		String jsonAsString = "{\"text\":\"[BOT] Xin th\u00F4ng b\u00E1o, h\u1EC7 th\u1ED1ng s\u1EBD ng\u1EEBng ho\u1EA1t \u0111\u1ED9ng v\u00E0o 00h ng\u00E0y 28/08/2016. C\u1EA3m \u01A1n m\u1ECDi ng\u01B0\u1EDDi \u0111\u00E3 \u1EE7ng h\u1ED9 UET Chatbot trong th\u1EDDi gian qua :D\"}";
		ObjectMapper jsonMapper = new ObjectMapper();
		
		try {
			SendToAllMessage message = jsonMapper.readValue(
					jsonAsString, SendToAllMessage.class);
			
			System.out.println(message.getText());
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
