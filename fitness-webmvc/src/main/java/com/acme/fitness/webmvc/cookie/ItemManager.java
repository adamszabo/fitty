package com.acme.fitness.webmvc.cookie;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.fitness.domain.orders.Basket;

public abstract class ItemManager {

	private Logger logger = LoggerFactory.getLogger(ItemManager.class);

	public boolean isItemExists(HttpServletRequest request, ObjectMapper mapper, Basket basket, String itemType) {
		boolean isBasketExists = false;
		String userName = new UserManager().getLoggedInUserName();
		if (userName.equals("anonymousUser")) {
			isBasketExists = loadBasketToAnonymousUser(request, mapper, basket, itemType, isBasketExists);
		} else {
			isBasketExists = loadBasketToSpecificUser(request, mapper, basket, itemType, isBasketExists, userName);
		}
		return isBasketExists;
	}

	public abstract void loadBasketWithItem(Map<String, String> item, Basket basket);


	protected <T> Map<String, T> readFromCookies(HttpServletRequest request, ObjectMapper mapper, String cookieName) {
		String cookieValue = null;

		CookieManager cookieManager = new CookieManager();
		cookieValue = cookieManager.readFromCookies(request, cookieName);

		JsonManager<T> jsonManager = new JsonManager<T>(mapper);
		Map<String, T> map = jsonManager.unwrapFromJsonString(cookieValue);

		return map;
	}
	
	protected <T> void writeMapToCookie(HttpServletResponse response, ObjectMapper mapper, String cookieName, Map<String, T> map) {
		JsonManager<T> jsonManager = new JsonManager<T>(mapper);
		String json = jsonManager.wrapJsonToString(map);

		CookieManager cookieManager = new CookieManager();
		cookieManager.writeToCookies(response, cookieName, json);
	}

	protected Map<String, Map<String, Map<String, String>>> loadUserNamesCookieValue(HttpServletRequest request, ObjectMapper mapper) {
		Map<String, Map<String, Map<String, String>>> userNames = readFromCookies(request, mapper, "userNames");
		return userNames;
	}

	private boolean loadBasketToAnonymousUser(HttpServletRequest request, ObjectMapper mapper, Basket basket, String itemType, boolean isBasketExists) {
		Map<String, String> items;
		items = readFromCookies(request, mapper, itemType);
		if (items.size() > 0) {
			loadBasketWithItem(items, basket);
			isBasketExists = true;
		}
		return isBasketExists;
	}
	
	private boolean loadBasketToSpecificUser(HttpServletRequest request, ObjectMapper mapper, Basket basket, String itemType, boolean isBasketExists, String userName) {
		Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, mapper);
		if (users.containsKey(userName)) {
			Map<String, Map<String, String>> basketOfUser = users.get(userName);
			if (basketOfUser.containsKey(itemType)) {
				Map<String, String> items = basketOfUser.get(itemType);
				loadBasketWithItem(items, basket);
				isBasketExists = true;
			}
		}
		return isBasketExists;
	}

	protected Logger getLogger() {
		return logger;
	}

	protected void setLogger(Logger logger) {
		this.logger = logger;
	}
}
