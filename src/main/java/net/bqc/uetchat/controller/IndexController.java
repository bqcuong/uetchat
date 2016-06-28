package net.bqc.uetchat.controller;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.bqc.uetchat.is.DAOInterface;
import net.bqc.uetchat.is.DAOMySQLImpl;
import net.bqc.uetchat.is.DBConnector;
import net.bqc.uetchat.is.User;

@Controller
public class IndexController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String hello(Model model,
			@RequestParam(value="action", required=false) String action) {
		
		if (action != null && action.equals("testdb")) {
//			System.out.println("____in Test db___");
//			System.out.println("____HOST____" + System.getenv("OPENSHIFT_MYSQL_DB_HOST"));
//			System.out.println("____PORT____" + System.getenv("OPENSHIFT_MYSQL_DB_PORT"));
//			System.out.println("____USERNAME____" + System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"));
//			System.out.println("____PASSWORD____" + System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"));
//			System.out.println("____URL____" + System.getenv("OPENSHIFT_MYSQL_DB_URL"));
			
			//return "db";
			Connection con = DBConnector.getInstance().createConnection();
			DAOInterface dao = new DAOMySQLImpl();
			List<User> list = dao.getAllUsers(con);
			
			if (list == null) {
				model.addAttribute("message", "Connection to db failed");
			} else if (list.isEmpty()) {
				model.addAttribute("message", "No user online");
			} else {
				model.addAttribute("users", list);
			}
			
			DBConnector.closeConnection(con);
			
		}
		
		return "index";
	}
}
