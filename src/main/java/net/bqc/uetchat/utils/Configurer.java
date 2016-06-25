package net.bqc.uetchat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurer {

	public static String PAGE_TOKEN;
	public static String JDBC_DRIVER;
	public static String DB_URL;
	public static String DB_USERNAME; 
	public static String DB_PASSWORD;
	public static String API_KEY;
	public static String APP_URL;
	
	static {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream is = classLoader.getResourceAsStream("config.properties");
			Properties p = new Properties();
			p.load(is);
			PAGE_TOKEN = p.getProperty("page_token");
			
			API_KEY = p.getProperty("api_key");
			
			JDBC_DRIVER = p.getProperty("db_driver");
			DB_URL = p.getProperty("db_url");
			DB_USERNAME = p.getProperty("db_username");
			DB_PASSWORD = p.getProperty("db_password");
			
			APP_URL = p.getProperty("app_url");
			
			is.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
