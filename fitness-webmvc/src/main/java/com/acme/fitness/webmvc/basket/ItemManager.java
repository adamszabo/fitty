package com.acme.fitness.webmvc.basket;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.webmvc.cookie.CookieManager;
import com.acme.fitness.webmvc.user.UserManager;

public abstract class ItemManager {

	private CookieManager cookieManager;

	@Autowired
	private UserManager userManager;

	public ItemManager() {
		this.cookieManager = new CookieManager();
	}

	public boolean isItemExists(HttpServletRequest request, ObjectMapper mapper, Basket basket, String itemType) {
		String userName = userManager.getLoggedInUserName();
		return userName.equals("anonymousUser") ? loadBasketToAnonymousUser(request, mapper, basket, itemType) : loadBasketToSpecificUser(request, mapper, basket, itemType, userName);
	}

	public abstract void loadBasketWithItem(Map<String, String> item, Basket basket);

	protected <T> Map<String, T> readFromCookies(HttpServletRequest request, ObjectMapper mapper, String cookieName) {
		return cookieManager.loadValueToMap(request, mapper, cookieName);
	}

	protected <T> void writeMapToCookie(HttpServletResponse response, ObjectMapper mapper, String cookieName, Map<String, T> map) {
		cookieManager.writeMapToCookie(response, mapper, cookieName, map);
	}
	
	protected Map<String, Map<String, Map<String, String>>> loadUserNamesCookieValue(HttpServletRequest request, ObjectMapper mapper) {
		return userManager.loadUserNamesCookieValue(request, mapper, cookieManager);
	}

	protected Map<String, Map<String, String>> loadBasketByUserName(Map<String, Map<String, Map<String, String>>> users, String userName) {
		return users.containsKey(userName) ? users.get(userName) : new HashMap<String, Map<String, String>>();
	}

	protected Map<String, String> loadProductsByProductType(Map<String, Map<String, String>> basket, String itemType) {
		return basket.containsKey(itemType) ? basket.get(itemType) : new HashMap<String, String>();
	}

	protected boolean loadBasketToAnonymousUser(HttpServletRequest request, ObjectMapper mapper, Basket basket, String itemType) {
		Map<String, String> items = cookieManager.loadValueToMap(request, mapper, itemType);
		return loadBasketAndReturnTrueIfItemNotEmpty(basket,items);
	}

	private boolean loadBasketToSpecificUser(HttpServletRequest request, ObjectMapper mapper, Basket basket, String itemType, String userName) {
		Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, mapper);
		Map<String, Map<String, String>> basketOf = loadBasketByUserName(users, userName);
		Map<String, String> items = loadProductsByProductType(basketOf, itemType);
		return loadBasketAndReturnTrueIfItemNotEmpty(basket, items);
	}

	private boolean loadBasketAndReturnTrueIfItemNotEmpty(Basket basket, Map<String, String> items) {
		boolean isBasketExists = false;
		if (items.size() > 0) {
			loadBasketWithItem(items, basket);
			isBasketExists  = true;
		}
		return isBasketExists;
	}
}
