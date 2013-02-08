package com.acme.fitness.webmvc.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.user.UserManager;

@Controller
@RequestMapping("/edzesek")
public class TrainingController {

	private GeneralUsersService generalUsersService;

	@Autowired
	public TrainingController(GeneralUsersService generalUsersService) {
		this.generalUsersService = generalUsersService;
	}

	@RequestMapping(value = "")
	public String training(HttpServletRequest request) {
		request.setAttribute("trainers", generalUsersService.getAllTrainers());
		// request.setAttribute("trainers", new ArrayList<User>());
		return "edzesek";
	}

	@ResponseBody
	@RequestMapping(value = "/edzo/naptar", method = RequestMethod.POST)
	public String getTrainerTrainings(@RequestParam("username") String username, @RequestParam("actualPageMonday") Date weeksMonday) {
		System.out.println("Username: " + username + " monday: " + weeksMonday);
		return "edzesek";
	}

	@ResponseBody
	@RequestMapping(value = "/ujedzes", method = RequestMethod.POST)
	public String newTraining(@RequestParam("date") long dateInMs) {
		String userFullName = "";
		try {
			User client = generalUsersService.getUserByUsername(new UserManager().getLoggedInUserName());
			userFullName = client.getFullName();
		} catch (FitnessDaoException e) {
			userFullName = "Anonymus";
		}
		System.out.println(new Date(dateInMs));
		System.out.println(new UserManager().getLoggedInUserName());
		return userFullName;
	}

}
