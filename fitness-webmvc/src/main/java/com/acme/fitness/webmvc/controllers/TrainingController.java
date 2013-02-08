package com.acme.fitness.webmvc.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.user.UserManager;

@Controller
@RequestMapping("/edzesek")
public class TrainingController {

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralUsersService gus;

	@Autowired
	private GeneralOrdersService gos;

	@RequestMapping(value = "")
	public String training(HttpServletRequest request) {
		request.setAttribute("trainers", gus.getAllTrainers());
		return "edzesek";
	}
	
	@RequestMapping(value = "/edzo/naptar")
	public String getTrainerTrainings(HttpServletRequest request) {
		request.setAttribute("trainers", gus.getAllTrainers());
		request.getSession().setAttribute("activeTrainer", request.getParameter("new-active-trainer-input"));
		return "edzesek";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ujedzes", method=RequestMethod.POST)
	public String newTraining(@RequestParam("date") long dateInMs, HttpServletRequest request) throws StoreQuantityException {
//		try {
//			
//			User client = gus.getUserByUsername(new UserManager().getLoggedInUserName());
//		} catch (FitnessDaoException e) {
//		}
		String trainerUserName = (String) request.getSession().getAttribute("activeTrainer");
		User trainer = null;
		User client = null;
		if(trainerUserName != null) {
			try {
				trainer = gus.getUserByUsername(trainerUserName);
				List<Training> trainings = gps.getTrainingsByTrainer(trainer);
				client = gus.getUserByUsername(new UserManager().getLoggedInUserName());
				boolean isTrainingOnDate = false;
				gps.isDateReserved(trainer, new Date(dateInMs));
				for(Training t : gps.getTrainingsOnWeekByTrainer(trainer, new Date(dateInMs))) {
					System.out.println(t.getTrainingStartDate());
				}
				
				for(Training t : trainings) {
					System.out.println(t);
					System.out.println(new Date(dateInMs));
					if(t.getTrainingStartDate().getTime() == new Date(dateInMs).getTime()) {
						System.out.println("There is training on the given date!!!");
						isTrainingOnDate = true;
					}
				}
				if(!isTrainingOnDate) {
					Training training = gps.newTraining(trainer, client, new Date(dateInMs));
					Basket basket =  gos.newBasket(client);
					gos.addTrainingToBasket(basket, training);
					gos.checkOutBasket(basket);
					System.out.println("New training has made!!!!!!");
				}
			} catch (FitnessDaoException e) {
				e.printStackTrace();
			}
		}
		return client.getFullName() == null ? "" : client.getFullName();
	}
	
}
