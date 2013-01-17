package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
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
@RequestMapping("/aruhaz")
public class WebShopController {

	private static final Logger logger = LoggerFactory
			.getLogger(WebShopController.class);

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralOrdersService gos;

	@Autowired
	private GeneralUsersService gus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String aruhaz() {
		return "redirect:/aruhaz/1";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String aruhazWithSlash() {
		return "redirect:/aruhaz/1";

	}

	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String setPage(Model model, @PathVariable String page,
			HttpServletResponse response, HttpServletRequest request) {

		addBasketToSessionIfExists(request, new ObjectMapper());

		int pageNumber = validatePageNumber(parsePageNumber(page), gps
				.getAllProduct().size());
		model.addAttribute("products", getProductsOnPage(pageNumber));
		model.addAttribute("pageNumber", pageNumber);
		return "aruhaz";
	}

	@RequestMapping(value = "/{page}/addToCart", method = RequestMethod.POST)
	public String addProductToCart(@ModelAttribute("productId") long id,
			@ModelAttribute("quantity") int quantity,
			@PathVariable String page, HttpServletResponse response,
			HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Integer> map = readBasketToMapFromCookies(request, mapper);

		addOrderItemToMap(id, quantity, map);

		writeMapToCookies(response, mapper, map);

		return "redirect:/aruhaz/" + page;
	}

	@RequestMapping(value = "/{page}/deleteBasket", method = RequestMethod.GET)
	public String deleteBasket(@PathVariable String page,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("basket");
		deleteAllCookies(response, request.getCookies());
		logger.info("Basket is deleted from cookies and session.");
		return "redirect:/aruhaz/" + page;
	}

	@RequestMapping(value = "/{page}/confirmBasket", method = RequestMethod.GET)
	public String confirmOrder(@PathVariable String page,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		if (getUserName().equals("anonymousUser")) {
			return failToCheckOut(page, redirectAttributes);
		} else {
			checkOutBasket(redirectAttributes, getBasketFromSession(request));
		}

		return deleteBasket(page, request, response);
	}

	private Basket getBasketFromSession(HttpServletRequest request) {
		return (Basket) request.getSession().getAttribute("basket");
	}

	private String failToCheckOut(String page,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message",
				"Termék rendeléséhez be kell jelentkezni!");
		return "redirect:/aruhaz/" + page;
	}

	private void checkOutBasket(RedirectAttributes redirectAttributes,
			Basket basket) {
		setBasketToUser(basket);
		try {
			gos.checkOutBasket(basket);
			logger.info("Basket with id: " + basket.getId() + " has confirmed!");
		} catch (StoreQuantityException e) {
			addMissingProductsMessages(redirectAttributes, e.getProduct());
		}
	}

	private void addMissingProductsMessages(
			RedirectAttributes redirectAttributes, List<Product> list) {
		redirectAttributes.addFlashAttribute("missingProduct", list);
		redirectAttributes
				.addFlashAttribute(
						"message",
						"Egyes termékekből nincsen elegendő mennyiség. További információk a hiányzó termékek linken!");
	}

	private void setBasketToUser(Basket basket) {
		try {
			User user = gus.getUserByUsername(getUserName());
			basket.setUser(user);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
	}

	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return auth.getName();
	}

	private void addOrderItemToMap(long id, int quantity,
			Map<String, Integer> map) {
		if (!map.containsKey(Long.toString(id))) {
			map.put(Long.toString(id), quantity);
		} else {
			Integer value = map.get(Long.toString(id));
			value = new Integer(value + quantity);
		}
	}

	private Map<String, Integer> addBasketToSessionIfExists(
			HttpServletRequest request, ObjectMapper mapper) {
		Map<String, Integer> map = readBasketToMapFromCookies(request, mapper);
		if (map.size() > 0) {
			request.getSession().setAttribute("basket", loadBasket(map));
		}
		return map;
	}

	private Basket loadBasket(Map<String, Integer> map) {
		Basket basket = gos.newBasket(new User());
		for (String s : map.keySet()) {
			addProductToBasketByProductId(basket, Long.parseLong(s), map.get(s));
		}
		return basket;
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

	private void deleteAllCookies(HttpServletResponse response, Cookie[] cookies) {
		for (Cookie c : cookies) {
			c.setMaxAge(0);
			response.addCookie(c);
		}
	}

	private void writeMapToCookies(HttpServletResponse response,
			ObjectMapper mapper, Map<String, Integer> map) {

		JsonManager jsonManager = new JsonManager(mapper);
		String json = jsonManager.wrapToJsonString(map);

		CookieManager cookieManager = new CookieManager();
		cookieManager.writeToCookies(response, "basket", json);
	}

	private Map<String, Integer> readBasketToMapFromCookies(
			HttpServletRequest request, ObjectMapper mapper) {
		String cookieValue = null;

		CookieManager cookieManager = new CookieManager();
		cookieValue = cookieManager.readFromCookies(request, "basket");

		JsonManager jsonManager = new JsonManager(mapper);
		Map<String, Integer> map = jsonManager
				.unwrapFromJsonString(cookieValue);

		return map;
	}

	private List<Product> getProductsOnPage(int pageNumber) {
		int size = gps.getAllProduct().size();
		List<Product> productsOnPage = new ArrayList<Product>();
		if (size > 0)
			productsOnPage = gps.getAllProduct().subList((pageNumber - 1) * 9,
					Math.min(pageNumber * 9, size));
		return productsOnPage;
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