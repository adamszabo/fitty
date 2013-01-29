package com.acme.fitness.webmvc.cookie;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.exceptions.BasketCheckOutException;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.controllers.WebShopController;

@Service
public class BasketManager {

	private static final Logger logger = LoggerFactory.getLogger(WebShopController.class);

	@Autowired
	private GeneralOrdersService gos;

	@Autowired
	private GeneralUsersService gus;

	@Autowired
	private ProductsManager pm;

	@Autowired
	private MembershipManager mm;

	public void addBasketToSessionIfExists(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper) {
		Basket basket = gos.newBasket(new User());
		boolean isMembershipExists = mm.isItemExists(request, mapper, basket, "membershipsInBasket");
		boolean isProductExists = pm.isItemExists(request, mapper, basket, "productsInBasket");
		if (isMembershipExists || isProductExists) {
			request.getSession().setAttribute("basket", basket);
		} else {
			deleteBasket(request, response);
		}
	}

	public void addNewMembership(long membershipId, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		mm.addNewMembership(membershipId, response, request, mapper);
	}

	public void addNewOrderItem(long id, int quantity, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		pm.addNewOrderItem(id, quantity, response, request, mapper);
	}

	public void checkOutBasket(HttpServletResponse response, HttpServletRequest request) throws StoreQuantityException, BasketCheckOutException {
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		if (!noUserLoggedIn()) {
			try {
				basket.setUser(gus.getUserByUsername(loggedInUserName()));
				gos.checkOutBasket(basket);
				deleteUserBasket(request, response, loggedInUserName());
				logger.info("Basket with id: " + basket.getId() + " has confirmed!");
			} catch (FitnessDaoException e) {
				e.printStackTrace();
			}
		} else {
			throw new BasketCheckOutException("Termék rendeléséhez be kell jelentkezni");
		}
	}


	public void deleteBasket(HttpServletRequest request, HttpServletResponse response) {
		if (noUserLoggedIn()) {
			deleteAnonymousBasket(request, response);
		} else {
			deleteUserBasket(request, response, loggedInUserName());
		}
		request.getSession().removeAttribute("basket");
	}

	private boolean noUserLoggedIn() {
		return loggedInUserName().equals("anonymousUser");
	}

	private void deleteAnonymousBasket(HttpServletRequest request, HttpServletResponse response) {
		removeFromCookiesAndSession(request, response, "membershipsInBasket");
		removeFromCookiesAndSession(request, response, "productsInBasket");
	}

	private void deleteUserBasket(HttpServletRequest request, HttpServletResponse response, String userName) {
		Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, new ObjectMapper());
		if (users.containsKey(userName)) {
			users.remove(userName);
		}
		writeMapToCookie(response, new ObjectMapper(), "userNames", users);
		request.getSession().removeAttribute("basket");
	}

	private String loggedInUserName() {
		return new UserManager().getLoggedInUserName();
	}

	private void removeFromCookiesAndSession(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		request.getSession().removeAttribute(cookieName);
		Cookie[] cookies = request.getCookies();
		if ((cookies != null) && cookies.length > 0) {
			for (Cookie c : request.getCookies()) {
				if (c.getName().equals(cookieName)) {
					c.setPath("/");
					c.setMaxAge(0);
					response.addCookie(c);
				}
			}
		}
	}

	private Map<String, Map<String, Map<String, String>>> loadUserNamesCookieValue(HttpServletRequest request, ObjectMapper mapper) {
		Map<String, Map<String, Map<String, String>>> userNames = readFromCookies(request, mapper, "userNames");
		return userNames;
	}

	private <T> Map<String, T> readFromCookies(HttpServletRequest request, ObjectMapper mapper, String cookieName) {
		String cookieValue = null;

		CookieManager cookieManager = new CookieManager();
		cookieValue = cookieManager.readFromCookies(request, cookieName);

		JsonManager<T> jsonManager = new JsonManager<T>(mapper);
		Map<String, T> map = jsonManager.unwrapFromJsonString(cookieValue);

		return map;
	}

	private <T> void writeMapToCookie(HttpServletResponse response, ObjectMapper mapper, String cookieName, Map<String, T> map) {
		JsonManager<T> jsonManager = new JsonManager<T>(mapper);
		String json = jsonManager.wrapJsonToString(map);

		CookieManager cookieManager = new CookieManager();
		cookieManager.writeToCookies(response, cookieName, json);
	}

}