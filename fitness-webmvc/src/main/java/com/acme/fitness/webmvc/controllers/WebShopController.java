package com.acme.fitness.webmvc.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;

@Controller
@RequestMapping("/aruhaz")
public class WebShopController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralOrdersService gos;

	@SuppressWarnings("unused")
	@Autowired
	private GeneralUsersService gus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String aruhaz(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		return setPage(model, "1", response, request);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String aruhazWithSlash(Locale locale, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		return setPage(model, "1", response, request);

	}

	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String setPage(Model model, @PathVariable String page,
			HttpServletResponse response, HttpServletRequest request) {
		if (request.getSession().getAttribute("basket") == null) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Integer> map = readFromCookies(request, mapper);
			if (map.size() > 0) {
				addNewBasketToSession(request, map);
			}
		}

		int pageNumber = parsePageNumber(page);
		int productSize = gps.getAllProduct().size();
		pageNumber = validatePageNumber(pageNumber, productSize);
		List<Product> productsOnPage = getProductsSubListOnPage(pageNumber,
				productSize);
		model.addAttribute("products", productsOnPage);
		model.addAttribute("pageNumber", pageNumber);
		return "aruhaz";
	}

	@RequestMapping(value = "/{page}/addToCart", method = RequestMethod.POST)
	public String addProductToCart(@ModelAttribute("productId") long id,
			@ModelAttribute("quantity") int quantity,
			@PathVariable String page, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Integer> map = readFromCookies(request, mapper);

		if (request.getSession().getAttribute("basket") == null) {
			addNewBasketToSession(request, map);
		}
		addProductToBasketAtSession(id, quantity, request);

		writeToCookies(id, quantity, response, request, mapper, map);

		return setPage(model, page, response, request);
	}

	@RequestMapping(value = "/{page}/deleteBasket", method = RequestMethod.GET)
	public String deleteBasket(@PathVariable String page, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("basket");
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			c.setMaxAge(0);
			response.addCookie(c);
		}
		logger.info("Basket is deleted from cookies and session.");
		return "redirect:/aruhaz/" + page;
	}

	private void addProductToBasketAtSession(long id, int quantity,
			HttpServletRequest request) {
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		addProductToBasketByProductId(id, quantity, basket);
		request.getSession().setAttribute("basket", basket);
		logger.info("Product : " + id + " with quantity : " + quantity + " added to " + basket.getUser().getUsername() + "'s user");
	}

	private void addNewBasketToSession(HttpServletRequest request,
			Map<String, Integer> map) {
		Basket basket = gos.newBasket(new User());
		for (String s : map.keySet()) {
			addProductToBasketByProductId(Long.parseLong(s), map.get(s), basket);
		}
		request.getSession().setAttribute("basket", basket);
	}

	private void writeToCookies(long id, int quantity,
			HttpServletResponse response, HttpServletRequest request,
			ObjectMapper mapper, Map<String, Integer> map) {
		map.put(Long.toString(id), quantity);

		String json = null;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Cookie cookie = new Cookie("basket", json);
		cookie.setMaxAge(99999);
		response.addCookie(cookie);
	}

	private List<Product> getProductsSubListOnPage(int pageNumber,
			int productSize) {
		List<Product> productsOnPage = new ArrayList<Product>();
		if (productSize > 0)
			productsOnPage = gps.getAllProduct().subList((pageNumber - 1) * 9,
					Math.min(pageNumber * 9, productSize));
		return productsOnPage;
	}

	private Map<String, Integer> readFromCookies(HttpServletRequest request,
			ObjectMapper mapper) {
		String cookieValue = null;

		if (request.getCookies() != null) {
			for (Cookie c : request.getCookies()) {
				if (c.getName().equals("basket")) {
					cookieValue = c.getValue();
				}
			}
		}

		Map<String, Integer> map = new HashMap<String, Integer>();

		if (cookieValue != null) {
			try {
				map = mapper.readValue(cookieValue,
						new TypeReference<Map<String, Object>>() {
						});
			} catch (JsonParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return map;
	}

	private void addProductToBasketByProductId(long id, int quantity,
			Basket basket) {
		Product product = null;
		try {
			product = gps.getProductById(id);
		} catch (FitnessDaoException e) {

		}
		OrderItem oi = gos.newOrderItem(product, quantity);
		gos.addOrderItemToBasket(basket, oi);
	}

	private int validatePageNumber(int pageNumber, int productSize) {
		if (pageNumber < 1) {
			pageNumber = 1;
		} else if (pageNumber > (Math.ceil(productSize / 9.0))) {
			pageNumber = (int) Math.ceil(productSize / 9.0);
		}
		return pageNumber;
	}

	private int parsePageNumber(String page) {
		int pageNumber;
		try {
			pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			pageNumber = 1;
		}
		return pageNumber;
	}
}
