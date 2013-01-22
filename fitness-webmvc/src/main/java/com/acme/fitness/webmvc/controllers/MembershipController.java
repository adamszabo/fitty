package com.acme.fitness.webmvc.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.web.CookieManager;
import com.acme.fitness.webmvc.web.JsonManager;


@Controller
@RequestMapping("/berletek")
public class MembershipController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(WebShopController.class);

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralOrdersService gos;

	@Autowired
	private GeneralUsersService gus;
	
	@RequestMapping("")
	public String defaultPage(HttpServletResponse response, HttpServletRequest request) {
		addBasketToSessionIfExists(request, new ObjectMapper());
		return "berletek";
	}
	
	@RequestMapping("/ujberlet")
	public String newMembership(@RequestParam("datepicker") String datepicker, @RequestParam("membershipType") String type, HttpServletResponse response, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Integer> map = readBasketToMapFromCookies(request, mapper);
		
		return "redirect:/berletek";
	}
	
	private void addMembershipToMap(long id, int quantity, Map<String, Integer> map) {
		if (!map.containsKey(Long.toString(id))) {
			map.put(Long.toString(id), quantity);
		} else {
			Integer value = map.get(Long.toString(id));
			value = new Integer(value + quantity);
		}
	}
	
	
	private Map<String, Integer> readBasketToMapFromCookies(
			HttpServletRequest request, ObjectMapper mapper) {
		String cookieValue = null;

		CookieManager cookieManager = new CookieManager();
		cookieValue = cookieManager.readFromCookies(request, "membershipInBasket");

		JsonManager jsonManager = new JsonManager(mapper);
		Map<String, Integer> map = jsonManager
				.unwrapFromJsonString(cookieValue);

		return map;
	}
	
	private Basket loadBasket(Map<String, Integer> map) {
		Basket basket = gos.newBasket(new User());
		for (String s : map.keySet()) {
			addProductToBasketByProductId(basket, Long.parseLong(s), map.get(s));
		}
		return basket;
	}
	
	private Map<String, Integer> addBasketToSessionIfExists(
			HttpServletRequest request, ObjectMapper mapper) {
		Map<String, Integer> map = readBasketToMapFromCookies(request, mapper);
		if (map.size() > 0) {
			request.getSession().setAttribute("basket", loadBasket(map));
		}
		return map;
	}
	
	private void addProductToBasketByProductId(Basket basket, long id,
			int quantity) {
		Product product = null;
		try {
			product = gps.getProductById(id);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		if (product != null) {
			OrderItem oi = gos.newOrderItem(product, quantity);
			gos.addOrderItemToBasket(basket, oi);
		}
	}
}
