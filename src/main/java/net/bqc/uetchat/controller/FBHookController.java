package net.bqc.uetchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Version;

import net.bqc.uetchat.utils.Configurer;

public class FBHookController {
	
	private JsonMapper jsonMapper = new DefaultJsonMapper();
	
	private FacebookClient pageClient = new DefaultFacebookClient(
			Configurer.PAGE_TOKEN, Version.VERSION_2_6);
	
	@RequestMapping(value="/fbhook", method={RequestMethod.GET})
	public String validate(Model model,
			@RequestParam(value="hub.challenge", required=false) String challenge,
			@RequestParam(value="hub.verify_token", required=false) String token) {
		
		// validate webhook
		if (token != null) {
			if (token.equalsIgnoreCase("cuong_is_the_boss")) {
				model.addAttribute("result", challenge);
				System.out.println("Validate webhook done!");
			} else {
				model.addAttribute("result", "Error, wrong validation token");
				System.out.println("Validate webhook failed!");
			}
			
			return "validate";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/fbhook",
			method={RequestMethod.POST},
			consumes="application/json",
			produces="application/json")
	@ResponseBody
	public ResponseEntity<String> receive(Model model,
			@RequestBody final String jsonAsString){
		
		
		
		return null;
	}
	
	private void joinChat() {
		
	}
	
	private void leaveChat() {
		
	}
	
	private void sendMessage() {
		
	}
}
