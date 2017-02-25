package net.bqc.uetchat.is;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.bqc.uetchat.utils.FbUser;

public class DAOMySQLImpl implements DAOInterface {

	@Override
	public boolean addUser(Connection con, String userId, FbUser fbUser) {
		if (con == null || userId == null) return false;
		PreparedStatement statement; 
		if (fbUser == null) fbUser = new FbUser();
		try {
			statement = con.prepareStatement(ADD_USER);
			statement.setString(1, userId);
			statement.setString(2, fbUser.getGender());
			statement.setString(3, fbUser.getFirstName());
			statement.setString(4, fbUser.getLastName());
			statement.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	@Override
	public boolean addUserInChat(Connection con, String userId) {
		if (con == null || userId == null) return false;
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
		if (con == null || userId == null) return false;
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
		if (con == null || lhs == null || rhs == null) return false;
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
		if (con == null || lhs == null || rhs == null) return false;
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
	public String getRandomUserNotInChat(Connection con, String lhs, String gender) {
		if (con == null || lhs == null || gender == null) return null;
		PreparedStatement statement; 
		
		try {
			statement = con.prepareStatement(GET_RANDOM_USER_NOT_IN_CHAT);
			statement.setString(1, lhs);
			statement.setString(2, gender);
			statement.setString(3, gender);
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
		if (con == null || lhs == null) return null;
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

	@Override
	public String getUserStatusById(Connection con, String userId) {
		if (con == null || userId == null) return null;
		PreparedStatement statement; 
		
		try {
			statement = con.prepareStatement(GET_USER_STATUS_BY_ID);
			statement.setString(1, userId);
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
	public List<User> getAllUsers(Connection con) {
		if (con == null) return null;
		PreparedStatement statement; 
		List<User> list = null;
				
		try {
			statement = con.prepareStatement(GET_ALL_USER);
			ResultSet rs = statement.executeQuery();
			
			list = new ArrayList<User>();
			while (rs.next()) {
				list.add(new User(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			return list;
		}
	}
	
	@Override
	public List<User> getUsersNotInChat(Connection con) {
		if (con == null) return null;
		PreparedStatement statement; 
		List<User> list = null;
		
		try {
			statement = con.prepareStatement(GET_USERS_NOT_IN_CHAT);
			ResultSet rs = statement.executeQuery();
			
			list = new ArrayList<User>();
			while (rs.next()) {
				list.add(new User(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			return list;
		}
	}

}
