package net.bqc.uetchat.is;

import java.sql.Connection;

public class TestDAOInterface {
	public static void main(String[] args) {
		Connection con = DBConnector.getInstance().createConnection();
		DAOInterface dao = new DAOMySQLImpl();
		
//		dao.addUser(con, "2345678");
		
		String lhs = "1234569";
		String rhs = "64564574573";
//		dao.addUser(con, lhs, null);
		
//		System.out.println(dao.getRandomUserNotInChat(con, "1234569", "M"));
//		System.out.println(dao.getRandomUserNotInChat(con, lhs, "M"));
//		dao.addUserInChat(con, lhs);
//		System.out.println(dao.getUserStatusById(con, lhs));
//		String rhs = dao.getRandomUserNotInChat(con, "1234567");
		
//		dao.addUserInChat(con, lhs);
//		dao.addUserInChat(con, rhs);
		
//		dao.removeUserById(con, lhs);
//		dao.removeUserById(con, "2345678");
//		dao.removeUserById(con, "8910234");
		
//		boolean res = dao.addChat(con, lhs, rhs);
//		System.out.println(res);
		DBConnector.closeConnection(con);
	}
}
