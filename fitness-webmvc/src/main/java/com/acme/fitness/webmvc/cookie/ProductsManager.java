package com.acme.fitness.webmvc.cookie;

import java.util.HashMap;
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

@Service
public class ProductsManager extends ItemManager{

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
		if(userName.equals("anonymousUser")) {
			
		} else {
			Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, new ObjectMapper());
			Map<String, Map<String, String>> basket = new HashMap<String, Map<String, String>>();
			Map<String, String> products = new HashMap<String, String>();
			if (users.containsKey(userName)) {
				basket = users.get(userName);
				if (basket.containsKey("productsInBasket")) {
					products = basket.get("productsInBasket");
					if(products.containsKey(Long.toString(id))) {
						products.remove(Long.toString(id));
					}
				}
			}
			if(products.size() == 0) {
				basket.remove("productsInBasket");
			} else {
				basket.put("productsInBasket", products);
			}
			if(basket.size() == 0) {
				users.remove(userName);
			} else {
				users.put(userName, basket);
			}
			writeMapToCookie(response, new ObjectMapper(), "userNames", users);
		}
	}
	
	private void addProductToUserCookie(long id, int quantity, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper, String userName) {
		Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, mapper);
		Map<String, Map<String, String>> basket = new HashMap<String, Map<String, String>>();
		Map<String, String> products = new HashMap<String, String>();
		if (users.containsKey(userName)) {
			basket = users.get(userName);
			if (basket.containsKey("productsInBasket")) {
				products = basket.get("productsInBasket");
			}
		}
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
			getLogger().info("Product cannot added to Basket with id: " + id);
			throw new FitnessDaoException();
		}
	}
}
