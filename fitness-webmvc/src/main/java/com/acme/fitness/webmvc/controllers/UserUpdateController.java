package com.acme.fitness.webmvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;

@Controller
@RequestMapping("/modositas")
public class UserUpdateController {
	private static final Logger logger = LoggerFactory.getLogger(UserUpdateController.class);

	private GeneralUsersService generalUsersService;
	private StandardPasswordEncoder standardPasswordEncoder;
	private User user;

	@Autowired
	public UserUpdateController(GeneralUsersService generalUsersService, StandardPasswordEncoder standardPasswordEncoder) {
		this.generalUsersService = generalUsersService;
		this.standardPasswordEncoder=standardPasswordEncoder;
	}

	@RequestMapping("/{username}")
	public String loadUserDetails(HttpServletRequest request, @PathVariable String username) {
		try {
			User user = generalUsersService.getUserByUsername(username);
			request.setAttribute("user", user);
			this.user=user;
			logger.info("load user update page with username: " + username);
		} catch (FitnessDaoException e) {
			logger.info("User does not exist with username: " + username);
		}
		
		return "usermodosit";
	}
	
	@RequestMapping(value="/validatePass", method=RequestMethod.POST)
	@ResponseBody
	public boolean validateUserPassword(@RequestParam("username") String username, @RequestParam("password") String password){
		return standardPasswordEncoder.matches(password, user.getPassword());
	}
	
	@RequestMapping(value="/adatok", method=RequestMethod.POST)
	@ResponseBody
	public boolean modifyUserData(@RequestParam("fullName") String fullName, @RequestParam("email") String email, @RequestParam("mobile") String mobile){
		boolean result=true;
		try {
			user.setFullName(fullName);
			user.setEmail(email);
			user.setMobile(mobile);
			generalUsersService.updateUser(user);
			logger.info("User's details updated. Username: "+user.getUsername());
		} catch (Exception e) {
			result=false;
		}
		return result;
	}
	
	@RequestMapping(value="/jelszo", method=RequestMethod.POST)
	@ResponseBody
	public boolean modifyUserPassword(@RequestParam("newPassword") String newPassword){
		boolean result=true;
		try {
			user.setPassword(standardPasswordEncoder.encode(newPassword));
			generalUsersService.updateUser(user);
			logger.info("User's password updated. Username: "+user.getUsername());
		} catch (Exception e) {
			result=false;
		}
		return result;
	}
}
