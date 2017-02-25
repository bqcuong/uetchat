package net.bqc.uetchat.is;

import java.sql.Connection;
import java.util.List;

import net.bqc.uetchat.utils.FbUser;

public interface DAOInterface {
	
	public static final String ADD_USER = 
			"INSERT INTO `user` (user_id, in_chat, gender, first_name, last_name) VALUES (?, 'N', ?, ?, ?)";
	public static final String ADD_USER_IN_CHAT =
			"UPDATE `user` SET in_chat = 'Y' WHERE user_id = ?";
	public static final String REMOVE_USER_BY_ID =
			"DELETE FROM `user` WHERE user_id = ?";
	public static final String ADD_CHAT =
			"INSERT INTO `chat` (lhs, rhs) VALUES (?, ?)";
	public static final String REMOVE_CHAT_BY_USER_ID =
			"DELETE FROM `chat` WHERE lhs = ?";
	public static final String GET_RANDOM_USER_NOT_IN_CHAT =
			"SELECT * FROM (SELECT * FROM `uetchat`.`user` "
			+ "WHERE NOT user_id = ? AND in_chat = 'N') `pretbl` "
			+ "WHERE `pretbl`.gender = IF (? = 'F', 'M', 'F') OR `pretbl`.gender = IF (? = 'M', 'M', 'M') "
			+ "ORDER BY RAND() LIMIT 1";
	public static final String GET_PARTNER_IN_CHAT =
			"SELECT rhs FROM `chat` WHERE lhs = ?";
	public static final String GET_USER_STATUS_BY_ID = 
			"SELECT in_chat FROM `user` WHERE user_id = ?";
	public static final String GET_ALL_USER = 
			"SELECT * FROM `user`";
	public static final String GET_USERS_NOT_IN_CHAT =
			"SELECT * FROM `user` WHERE `in_chat` = 'N'";
	
	public boolean addUser(Connection con, String userId, FbUser fbUser);
	public boolean addUserInChat(Connection con, String userId);
	public boolean removeUserById(Connection con, String userId);
	public boolean addChat(Connection con, String lhs, String rhs);
	public boolean removeChatByUserId(Connection con, String lhs, String rhs);
	public String getRandomUserNotInChat(Connection con, String lhs, String gender);
	public String getPartnerInChat(Connection con, String lhs);
	public String getUserStatusById(Connection con, String userId);
	public List<User> getAllUsers(Connection con);
	public List<User> getUsersNotInChat(Connection con);
}
