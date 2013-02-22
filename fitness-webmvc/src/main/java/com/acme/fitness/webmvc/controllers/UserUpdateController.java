package com.acme.fitness.webmvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;

@Controller
@RequestMapping("/modosit")
public class UserUpdateController {
	private static final Logger logger = LoggerFactory.getLogger(UserUpdateController.class);

	private GeneralUsersService generalUsersService;

	@Autowired
	public UserUpdateController(GeneralUsersService generalUsersService) {
		this.generalUsersService = generalUsersService;
	}

	@RequestMapping("/{username}")
	public String loadUserDetails(HttpServletRequest request, @PathVariable String username) {
		try {
			User user = generalUsersService.getUserByUsername(username);
			request.setAttribute("user", user);
			logger.info("load user update page with username: " + username);
		} catch (FitnessDaoException e) {
			logger.info("User does not exist with username: " + username);
		}
		
		return "usermodosit";
	}
}
