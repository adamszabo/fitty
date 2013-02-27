package com.acme.fitness.webmvc.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.basket.BasketManager;
import com.acme.fitness.webmvc.user.UserManager;

@Controller
@RequestMapping(value="/rendelesek")
public class OrdersController {
	
	private UserManager userManager;
	private GeneralUsersService generalUsersService;
	private GeneralOrdersService generalOrdersService;
	private BasketManager basketManager;
	
	@Autowired
	public OrdersController(UserManager userManager, GeneralUsersService generalUsersService, GeneralOrdersService generalOrdersService, BasketManager basketManager){
		this.userManager=userManager;
		this.generalUsersService=generalUsersService;
		this.generalOrdersService=generalOrdersService;
		this.basketManager=basketManager;
	}
	
	@RequestMapping(value="/atveheto")
	public String addNotDeliveredBasketsToRequest(HttpServletRequest request, HttpServletResponse response){
		addBasketToRequest(request, response);
		addBasketsToRequestWithDeliveredStatus(request, false, "baskets");
		
		return "rendelesek";
	}
	
	@RequestMapping(value="/teljesitett")
	public String addDeliveredBasketsToRequest(HttpServletRequest request, HttpServletResponse response){
		addBasketToRequest(request, response);
		addBasketsToRequestWithDeliveredStatus(request, true, "deliveredBaskets");
		
		return "rendelesek";
	}
	
	private void addBasketToRequest(HttpServletRequest request, HttpServletResponse response){
		basketManager.addBasketToSessionIfExists(request, response, new ObjectMapper());
		basketManager.isAnonymousBasketIfUserLoggedIn(request, response, new ObjectMapper());
	}
	
	private void addBasketsToRequestWithDeliveredStatus(HttpServletRequest request, boolean isDelivered, String requestAttributeName){
		String username=userManager.getLoggedInUserName();
		try {
			User user=generalUsersService.getUserByUsername(username);
			List<Basket> baskets=generalOrdersService.getBasketsByUserAndDeliveredStatus(user, isDelivered);
			request.setAttribute(requestAttributeName, baskets);
		} catch (FitnessDaoException e) {
			e.fillInStackTrace().printStackTrace();
		}
	}
}
