package net.bqc.uetchat.controller;

import java.sql.Connection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bqc.uetchat.entity.SendToAllMessage;
import net.bqc.uetchat.is.DAOInterface;
import net.bqc.uetchat.is.DAOMySQLImpl;
import net.bqc.uetchat.is.DBConnector;
import net.bqc.uetchat.is.User;
import net.bqc.uetchat.utils.FBMessageObject;

@Controller
public class IndexController {
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	
	@RequestMapping(value="/random", method=RequestMethod.GET)
	public String hello(Model model) {

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
					
		return "random";
	}
	
	@RequestMapping(
			value="/sendall", method = RequestMethod.POST,
			consumes = "application/json")
	@ResponseBody
	public ResponseEntity<String> sendtoAll(
			@RequestParam(name="token") String token,
			@RequestBody String jsonAsString) {
		
		if (token == null || !token.equals("uetchatbotvnu1234")) {
			return new ResponseEntity<String> ("wrong token", HttpStatus.OK);
		}
		
		try {
			SendToAllMessage message = jsonMapper.readValue(
					jsonAsString, SendToAllMessage.class);
			Connection con = DBConnector.getInstance().createConnection();
			DAOInterface dao = new DAOMySQLImpl();
			List<User> users = dao.getAllUsers(con);
			
			System.out.println("__[Send All]__start sending [" + message.getText() + "] to all users");
			for (User u : users) {
				try {
					FBMessageObject.sendMessage(u.getUserId(), message.getText());
					System.out.println("__[Send All]__sent to " + u.getUserId());
				} catch (Exception e) {
					System.out.println("__[Send All]__cannot send to " + u.getUserId());
				}
			}
			return new ResponseEntity<String>("success", HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<String> (e.getMessage(), HttpStatus.OK);
		}
		
	}
}
