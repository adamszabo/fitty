package com.acme.fitness.webmvc.basket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.webmvc.user.UserManager;

@Service
public class ProductsManager extends ItemManager {

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralOrdersService gos;

	@Override
	public void loadBasketWithItem(Map<String, String> item, Basket basket) {
		for (String s : item.keySet()) {
			try {
				addProductToBasketByProductId(basket, Long.parseLong(s), Integer.parseInt(item.get(s)));
			} catch (FitnessDaoException e) {
				item.remove(s);
			}
		}
	}

	public void addNewOrderItem(long id, int quantity, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		String userName = new UserManager().getLoggedInUserName();
		if (userName.equals("anonymousUser")) {
			addProductToAnonymousCookie(id, quantity, response, request, mapper);
		} else {
			addProductToUserCookie(id, quantity, response, request, mapper, userName);
		}
	}

	public void removeProduct(long id, HttpServletRequest request, HttpServletResponse response) {
		String userName = new UserManager().getLoggedInUserName();
		if (userName.equals("anonymousUser")) {
			removeProductFromTheAnonymousMap(id, request, response);
		} else {
			removeProductFromTheUserSpecificMap(id, request, response, userName);
		}
	}

	private void removeProductFromTheUserSpecificMap(long id, HttpServletRequest request, HttpServletResponse response, String userName) {
		Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, new ObjectMapper());
		Map<String, Map<String, String>> basket = loadBasketByUserName(users, userName);
		Map<String, String> products = loadProductsByProductType(basket, "productsInBasket");
		removeProductById(id, products);
		removeMapIfEmptyOtherwiseUpdate(products, basket, "productsInBasket");
		removeMapIfEmptyOtherwiseUpdate(basket, users, userName);
		writeMapToCookie(response, new ObjectMapper(), "userNames", users);
	}

	private void removeProductFromTheAnonymousMap(long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> items = readFromCookies(request, new ObjectMapper(), "productsInBasket");
		removeProductById(id, items);
		writeMapToCookie(response, new ObjectMapper(), "productsInBasket", items);
	}

	private <T> void removeMapIfEmptyOtherwiseUpdate(Map<String, T> embededMap, Map<String, Map<String, T>> map, String string) {
		if (embededMap.size() == 0) {
			map.remove(string);
		} else {
			map.put(string, embededMap);
		}
	}

	private void addProductToUserCookie(long id, int quantity, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper, String userName) {
		Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, mapper);
		Map<String, Map<String, String>> basket = loadBasketByUserName(users, userName);
		Map<String, String> products = loadProductsByProductType(basket, "productsInBasket");

		addOrderItem(id, quantity, products);
		basket.put("productsInBasket", products);
		users.put(userName, basket);
		writeMapToCookie(response, mapper, "userNames", users);
	}

	private void addProductToAnonymousCookie(long id, int quantity, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		Map<String, String> map = readFromCookies(request, mapper, "productsInBasket");
		addOrderItem(id, quantity, map);
		writeMapToCookie(response, mapper, "productsInBasket", map);
	}

	private void addOrderItem(long id, int quantity, Map<String, String> map) {
		if (!map.containsKey(Long.toString(id))) {
			map.put(Long.toString(id), Integer.toString(quantity));
		} else {
			Integer value = Integer.parseInt(map.get(Long.toString(id)));
			value = new Integer(value + quantity);
			map.put(Long.toString(id), value.toString());
		}
	}

	private void addProductToBasketByProductId(Basket basket, long id, Integer quantity) throws FitnessDaoException {
		Product product = null;
		try {
			product = gps.getProductById(id);
			OrderItem oi = gos.newOrderItem(product, quantity);
			gos.addOrderItemToBasket(basket, oi);
		} catch (FitnessDaoException e) {
			throw new FitnessDaoException();
		}
	}
	
	private void removeProductById(long id, Map<String, String> products) {
		if (products.containsKey(Long.toString(id))) {
			products.remove(Long.toString(id));
		}
	}
}
