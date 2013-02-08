package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.basket.BasketManager;
import com.acme.fitness.webmvc.user.UserManager;

@Controller
@RequestMapping("/edzesek")
public class TrainingController {
	private static Logger logger = LoggerFactory.getLogger(TrainingController.class);

	private GeneralUsersService generalUsersService;
	private GeneralProductsService generalProductsService;
	private BasketManager basketManager;

	@Autowired
	public TrainingController(GeneralUsersService generalUsersService, GeneralProductsService generalProductsService, BasketManager basketManager) {
		this.generalUsersService = generalUsersService;
		this.generalProductsService = generalProductsService;
		this.basketManager = basketManager;
	}

	@RequestMapping(value = "")
	public String training(HttpServletRequest request) {
		request.setAttribute("trainers", generalUsersService.getAllTrainers());
		return "edzesek";
	}

	@ResponseBody
	@RequestMapping(value = "/edzo/naptar", method = RequestMethod.POST)
	public List<Training> getTrainerTrainings(@RequestParam("username") String username, @RequestParam("actualPageMonday") Date weeksMonday) {
		List<Training> trainings = new ArrayList<Training>();

		if (username != null && !username.equals("") && weeksMonday != null) {
			try {
				User trainer = generalUsersService.getUserByUsername(username);
				trainings = generalProductsService.getTrainingsOnWeekByTrainer(trainer, weeksMonday);
				logger.info("Ajax request to get trainer's trainings with Username: " + username + " monday: " + weeksMonday);
			} catch (FitnessDaoException e) {
				e.printStackTrace();
			}
		}
		return trainings;
	}

	@RequestMapping(value = "/ujedzes", method = RequestMethod.POST)
	public String newTraining(@RequestParam("trainer-username") String username, @RequestParam("training-date") Date trainingDate, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			User trainer = generalUsersService.getUserByUsername(username);
			basketManager.addNewTraining(username, trainingDate, response, request, new ObjectMapper());
			logger.info("Training add to basket with trainer:" + username + " , date: " + trainingDate);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return "redirect:/edzesek";
	}

}
