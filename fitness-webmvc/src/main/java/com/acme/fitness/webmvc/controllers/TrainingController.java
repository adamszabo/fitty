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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.basket.BasketManager;
import com.acme.fitness.webmvc.dto.AllStatedTrainings;

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
	public String training(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("trainingTypes", generalProductsService.getAllTrainingTypes());
		basketManager.addBasketToSessionIfExists(request, response, new ObjectMapper());
		basketManager.isAnonymousBasketIfUserLoggedIn(request, response, new ObjectMapper());
		return "edzesek";
	}

	@ResponseBody
	@RequestMapping(value = "/edzo/naptar", method = RequestMethod.POST)
	public AllStatedTrainings getTrainerTrainings(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("actualPageMonday") Date weeksMonday) {
		AllStatedTrainings trainings = new AllStatedTrainings();

		if (username != null && !username.equals("") && weeksMonday != null) {
			try {
				User trainer = generalUsersService.getUserByUsername(username);
				trainings.setOrderedTrainings(generalProductsService.getTrainingsOnWeekByTrainer(trainer, weeksMonday));
				trainings.setTrainingsInBasket(getTrainingsFromBasket(request));
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
		basketManager.addNewTraining(username, String.valueOf(trainingDate.getTime()), response, request, new ObjectMapper());
		logger.info("Training add to basket with trainer:" + username + " , date: " + trainingDate);
		return "redirect:" + redirectedFrom(request);
	}
	
	@RequestMapping("/torles/{trainerName}")
	public String deleteTraining(@PathVariable String trainerName, HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeTrainingFromBasket(request, response, trainerName);
		return "redirect:" + redirectedFrom(request);
	}

	@RequestMapping(value = "/torles/anonymous/{username}")
	public String removeAnonymousTraining(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeAnonymousTraining(request, response, username);
		return "redirect:" + redirectedFrom(request);
	}
	
	@RequestMapping(value = "/torles/foglalt")
	public String removeReservedTrainings(HttpServletRequest request, HttpServletResponse response) {
		basketManager.removeReservedTrainings(request, response, new ObjectMapper());
		return "redirect:" + redirectedFrom(request);
	}
	
	private String redirectedFrom(HttpServletRequest request) {
		String split[] = request.getHeader("referer").split(request.getContextPath());
		return split[1];
	}
	
	private List<Training> getTrainingsFromBasket(HttpServletRequest request) {
		List<Training> result=new ArrayList<Training>();
		Basket basket=(Basket)request.getSession().getAttribute("basket");
		
		if(basket!=null){
			result=basket.getTrainings();
		}
		
		return result;
	}

}
