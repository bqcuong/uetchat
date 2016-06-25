package net.bqc.uetchat.is;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.bqc.uetchat.utils.Configurer;

public class DBConnector {
	
	private static DBConnector instance = new DBConnector();
	private DBConnector() {}
	public static DBConnector getInstance() { return instance; }
	
	public Connection createConnection() {
		Connection con = null;		
		try {
			Class.forName(Configurer.JDBC_DRIVER);
			con = DriverManager.getConnection(
					Configurer.DB_URL,
					Configurer.DB_USERNAME, 
					Configurer.DB_PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return con;
		}
	}
	
	public static boolean closeConnection(Connection con) {
		if (con == null) return false;
		try {
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
