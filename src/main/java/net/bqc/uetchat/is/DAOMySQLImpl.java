package net.bqc.uetchat.is;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOMySQLImpl implements DAOInterface {

	@Override
	public boolean addUser(Connection con, String userId) {
		PreparedStatement statement; 
				
		try {
			statement = con.prepareStatement(ADD_USER);
			statement.setString(1, userId);
			statement.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			return false;
		}	
	}
	
	@Override
	public boolean addUserInChat(Connection con, String userId) {
		PreparedStatement statement; 
		
		try {
			statement = con.prepareStatement(ADD_USER_IN_CHAT);
			statement.setString(1, userId);
			statement.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeUserById(Connection con, String userId) {
		PreparedStatement statement; 
		
		try {
			statement = con.prepareStatement(REMOVE_USER_BY_ID);
			statement.setString(1, userId);
			statement.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean addChat(Connection con, String lhs, String rhs) {
		PreparedStatement statement; 
		PreparedStatement statement2; 
		
		try {
			statement = con.prepareStatement(ADD_CHAT);
			statement.setString(1, lhs);
			statement.setString(2, rhs);
			
			statement2 = con.prepareStatement(ADD_CHAT);
			statement2.setString(1, rhs);
			statement2.setString(2, lhs);
			
			statement.executeUpdate();
			statement2.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeChatByUserId(Connection con, String lhs, String rhs) {
		PreparedStatement statement; 
		PreparedStatement statement2; 
		
		try {
			statement = con.prepareStatement(REMOVE_CHAT_BY_USER_ID);
			statement.setString(1, lhs);
			
			statement2 = con.prepareStatement(REMOVE_CHAT_BY_USER_ID);
			statement2.setString(1, rhs);
			
			statement.executeUpdate();
			statement2.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public String getRandomUserNotInChat(Connection con, String lhs) {
		PreparedStatement statement; 
		
		try {
			statement = con.prepareStatement(GET_RANDOM_USER_NOT_IN_CHAT);
			statement.setString(1, lhs);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getPartnerInChat(Connection con, String lhs) {
		PreparedStatement statement; 
		
		try {
			statement = con.prepareStatement(GET_PARTNER_IN_CHAT);
			statement.setString(1, lhs);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
