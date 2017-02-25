package net.bqc.uetchat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class Helper {
	
	private static final Logger logger = Logger.getLogger(Helper.class);
	
	private static final String FB_USER_API =
			"https://graph.facebook.com/v2.8/%s?fields=gender,first_name,last_name&access_token=" + Configurer.PAGE_TOKEN;
	
	private static RestTemplate rstTempl = new RestTemplate();
	
	public static void main(String[] args) {
//		FBMessageObject.sendErrorMessage("1103710079675517"); // male
//		logger.info(getFbUser("1110618665643355")); // female
	}
	
	public static FbUser getFbUser(String userId) {
		try {
			ResponseEntity<FbUser> fbUserRsp = rstTempl.getForEntity(String.format(FB_USER_API, userId), FbUser.class);
			FbUser fbUser = fbUserRsp.getBody();
			if (fbUser.getGender() == null || fbUser.getGender().equals("male")) {
				fbUser.setGender("M");
			} else {
				fbUser.setGender("F");
			}
			return fbUser;
		} catch (RestClientException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return new FbUser();
		}
		
	}

	public static String sendGet(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", "Mozila/5.0");

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
			
		} catch (IOException e) {
			logger.info("Error when send get to facebook api");
			logger.info(e.getMessage());
			return null;
		}

	}
}
