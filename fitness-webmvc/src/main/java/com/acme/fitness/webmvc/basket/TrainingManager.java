package com.acme.fitness.webmvc.basket;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.cookie.CookieManager;
import com.acme.fitness.webmvc.user.UserManager;

@Service
public class TrainingManager extends ItemManager {

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralUsersService gus;

	@Autowired
	private UserManager userManager;

	@Override
	public void loadBasketWithItem(Map<String, String> item, Basket basket) {
		for (String s : item.keySet()) {
			try {
				addTrainingToBasket(basket, s, item.get(s));
			} catch (FitnessDaoException e) {
				item.remove(s);
			}
		}
	}

	public void addNewTraining(String trainerName, String date, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		String clientName = userManager.getLoggedInUserName();
		if (clientName.equals("anonymousUser")) {
			addTrainingToAnonymousCookie(trainerName, date, request, response, mapper);
		} else {
			addTrainingToUserCookie(trainerName, date, request, response, mapper, clientName);
		}
	}

	public void addNewTrainingFromAnonymousBasket(HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		Map<String, String> memberships = readFromCookies(request, mapper, "trainingsInBasket");
		addMapToCookie(response, request, mapper, memberships);
	}

	public void removeTraining(HttpServletResponse response, HttpServletRequest request, String trainerName) {
		String userName = userManager.getLoggedInUserName();
		if (userName.equals("anonymousUser")) {
			removeAnonymousTrainingFromCookiesByTrainerName(response, request, trainerName);
		} else {
			Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, new ObjectMapper());
			Map<String, Map<String, String>> basket = loadBasketByUserName(users, userName);
			Map<String, String> trainings = loadProductsByProductType(basket, "trainingsInBasket");
			
			trainings.remove(trainerName);
			if(trainings.size() == 0) {
				basket.remove("trainingsInBasket");
			}
			if (basket.size() == 0) {
				users.remove(userName);
			}
			writeMapToCookie(response, new ObjectMapper(), "userNames", users);
		}
	}
	
	public void removeAnonymousTrainingFromCookiesByTrainerName(HttpServletResponse response, HttpServletRequest request, String trainerName) {
		Map<String, String> trainings = readFromCookies(request, new ObjectMapper(), "trainingsInBasket");
		trainings.remove(trainerName);
		writeMapToCookie(response, new ObjectMapper(), "trainingsInBasket", trainings);
	}

	private void addMapToCookie(HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper, Map<String, String> trainings) {
		String userName = userManager.getLoggedInUserName();
		if (userName.equals("anonymousUser")) {
			writeMapToCookie(response, mapper, "trainingsInBasket", trainings);
		} else {
			Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, mapper);
			Map<String, Map<String, String>> basket = new HashMap<String, Map<String, String>>();
			if (users.containsKey(userName)) {
				basket = users.get(userName);
			}
			basket.put("trainingsInBasket", trainings);
			users.put(userName, basket);
			writeMapToCookie(response, mapper, "userNames", users);
		}
	}

	private void addTrainingToUserCookie(String trainerName, String date, HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, String clientName) {
		Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, mapper);
		Map<String, Map<String, String>> basket = loadBasketByUserName(users, clientName);
		Map<String, String> products = loadProductsByProductType(basket, "trainingsInBasket");

		addTrainingToMap(trainerName, date, products);
		basket.put("trainingsInBasket", products);
		users.put(clientName, basket);
		writeMapToCookie(response, mapper, "userNames", users);
	}

	private void addTrainingToAnonymousCookie(String trainerName, String date, HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper) {
		Map<String, String> map = readFromCookies(request, mapper, "trainingsInBasket");
		addTrainingToMap(trainerName, date, map);
		writeMapToCookie(response, mapper, "trainingsInBasket", map);
	}

	private void addTrainingToMap(String trainerName, String date, Map<String, String> map) {
		map.put(trainerName, date);
	}

	private void addTrainingToBasket(Basket basket, String trainerName, String date) throws FitnessDaoException {
		User trainer = null;
		User client = null;
		String userName = userManager.getLoggedInUserName();
		if (!userName.equals("anonymousUser")) {
			client = gus.getUserByUsername(userName);
		}
		trainer = gus.getUserByUsername(trainerName);
		Training training = gps.newTraining(trainer, client, new Date(Long.parseLong(date)));
		basket.addTraining(training);
		training.setBasket(basket);
	}

	public void addTrainingToList(Map<String, Map<String, String>> basket, Map<String, String> anonymousTrainings) {
		basket.put("trainingsInBasket", anonymousTrainings);
	}

	public void removeTrainingsFromBasket(String userName, List<Training> reservedTrainings, HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {
		Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, new ObjectMapper());
		Map<String, Map<String, String>> basket = loadBasketByUserName(users, userName);
		Map<String, String> trainings = loadProductsByProductType(basket, "trainingsInBasket");
		
		for(String s : trainings.keySet()) {
			for(Training t : reservedTrainings) {
				if(s.equals(t.getTrainer().getUsername())) {
					trainings.remove(s);
				}
			}
		}
		
		if(trainings.size() == 0) {
			basket.remove("trainingsInBasket");
		}
		if (basket.size() == 0) {
			users.remove(userName);
		}
		writeMapToCookie(response, new ObjectMapper(), "userNames", users);
	}
}
