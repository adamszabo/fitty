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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
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
		if (isBasketNotExist(request)) {
			addBasketToSessionFromCookie(request);
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

		if (isBasketNotExist(request)) {
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
		deleteAllCookies(response, request.getCookies());
		logger.info("Basket is deleted from cookies and session.");
		return "redirect:/aruhaz/" + page;
	}

	@RequestMapping(value = "/{page}/confirmBasket", method = RequestMethod.GET)
	public String confirmOrder(@PathVariable String page, Model model,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName();
		if (name.equals("anonymousUser")) {
			redirectAttributes.addFlashAttribute("message", "Termék rendeléséhez be kell jelentkezni!");
			return "redirect:/aruhaz/" + page; 
		} else {
			System.out.println(name);
			Basket basket = (Basket) request.getSession()
					.getAttribute("basket");
			User user = null;
			try {
				user = gus.getUserByUsername(name);
			} catch (FitnessDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			basket.setUser(user);
			try {
				gos.checkOutBasket(basket);
			} catch (StoreQuantityException e) {
				request.getSession().setAttribute("missingProduct", e.getProduct());
				redirectAttributes.addFlashAttribute("message", "Egyes termékekből nincsen elegendő mennyiség. További információk a hiányzó termékek linken!");
				request.setAttribute("message", "Egyes termékekből nincsen elegendő mennyiség. További információk a hiányzó termékek linken!");
			}
		}

		return deleteBasket(page, model, request, response);
	}

	private void addBasketToSessionFromCookie(HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Integer> map = readFromCookies(request, mapper);
		if (map.size() > 0) {
			addNewBasketToSession(request, map);
		}
	}

	private boolean isBasketNotExist(HttpServletRequest request) {
		return request.getSession().getAttribute("basket") == null;
	}

	private void deleteAllCookies(HttpServletResponse response, Cookie[] cookies) {
		for (Cookie c : cookies) {
			c.setMaxAge(0);
			response.addCookie(c);
		}
	}

	private void addProductToBasketAtSession(long id, int quantity,
			HttpServletRequest request) {
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		addProductToBasketByProductId(id, quantity, basket);
		request.getSession().setAttribute("basket", basket);
		logger.info("Product : " + id + " with quantity : " + quantity
				+ " added to " + RequestContextHolder.currentRequestAttributes().getSessionId() + " session's id");
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
		if (!map.containsKey(Long.toString(id))) {
			map.put(Long.toString(id), quantity);
		}
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
		if(basket.getOrderItems().contains(product)) {
			System.out.println("Hát haaaahóóóóóó");
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