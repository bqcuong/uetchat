package net.bqc.uetchat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Helper {
	
	private static final String GET_USER_GENDER_API =
			"https://graph.facebook.com/v2.6/%s?fields=gender&access_token=" + Configurer.PAGE_TOKEN;
	
	public static String getUserGender(String userId) {
		String raw = sendGet(String.format(GET_USER_GENDER_API, userId));
		if (raw == null) {
			return null;
		} else if (raw.contains("\"male\"")) {
			return "M";
		} else if (raw.contains("\"female\"")) {
			return "F";
		} else return null;
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
			System.err.println("__HELPER__Error when getting gender of user");
			return null;
		}

	}
}
