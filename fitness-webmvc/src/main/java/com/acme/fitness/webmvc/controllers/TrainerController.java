package com.acme.fitness.webmvc.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.user.UserManager;

@Controller
@RequestMapping("/edzo")
public class TrainerController {
	private static Logger logger = LoggerFactory.getLogger(TrainingController.class);

	private GeneralProductsService generalProductsService;
	private GeneralUsersService generalUsersService;
	private UserManager userManager;

	@Autowired
	public TrainerController(GeneralProductsService generalProductsService, UserManager userManager, GeneralUsersService generalUsersService) {
		this.generalProductsService = generalProductsService;
		this.userManager = userManager;
		this.generalUsersService = generalUsersService;
	}

	@ResponseBody
	@RequestMapping(value = "/vakacio", method = RequestMethod.POST)
	public boolean addVacation(HttpServletRequest request, @RequestParam("trainingDate") long dateInMS) {
		boolean result=false;
		Date trainingDate = new Date(dateInMS);
		String actualUserName = userManager.getLoggedInUserName();

		try {
			User user = generalUsersService.getUserByUsername(actualUserName);
			generalProductsService.goOnHoliday(user, trainingDate);
			logger.info("Vacation for: " + actualUserName + " with date: " + trainingDate);
			result=true;
		} catch (FitnessDaoException e) {
			e.printStackTrace();
			result=false;
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/vakacioNap", method = RequestMethod.POST)
	public boolean getTrainerAllTrainingsOnDay(HttpServletRequest request, @RequestParam("trainingDate") long dateInMS) {
		boolean result=false;
		Date trainingDate = new Date(dateInMS);
		String actualUserName = userManager.getLoggedInUserName();

		try {
			User user = generalUsersService.getUserByUsername(actualUserName);
			 generalProductsService.goOnHolidayToAllDay(user, trainingDate);
			logger.info("All day vacation for: " + actualUserName + " with date: " + trainingDate);
			result=true;
		} catch (FitnessDaoException e) {
			e.printStackTrace();
			result=false;
		}

		return result;
	}
}
