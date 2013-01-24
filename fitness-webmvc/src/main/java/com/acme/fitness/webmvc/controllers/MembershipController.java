package com.acme.fitness.webmvc.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.MembershipType;
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

	private static final Logger logger = LoggerFactory.getLogger(WebShopController.class);

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralOrdersService gos;

	@Autowired
	private GeneralUsersService gus;

	@RequestMapping("")
	public String defaultPage(HttpServletResponse response, HttpServletRequest request, Model model) {
		addBasketToSessionIfExists(request, response, new ObjectMapper());
		model.addAttribute("membershipTypes", gps.getAllMembershipTypes());
		return "berletek";
	}

	@RequestMapping("/ujberlet")
	public String newMembership(@RequestParam("membershipId") long membershipId, HttpServletResponse response, HttpServletRequest request) {

		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> map = readMembershipsBasketToMapFromCookies(request, mapper);

		if (isMembershipTypeInterval(membershipId)) {
			map.put(Long.toString(membershipId), request.getParameter("datepicker"));
		} else {
			map.put(Long.toString(membershipId), null);
		}

		writeMapToCookies(response, mapper, map);

		return "redirect:/berletek";
	}

	@RequestMapping("/megrendel")
	public String checkOut(HttpServletResponse response, HttpServletRequest request) throws FitnessDaoException {

		Basket basket = (Basket) request.getSession().getAttribute("basket");
		if (!getUserName().equals("anonymousUser")) {
			basket.setUser(gus.getUserByUsername(getUserName()));
			try {
				gos.checkOutBasket(basket);
				deleteMembershipFromCookie(request, response);
				request.getSession().removeAttribute("basket");
			} catch (StoreQuantityException e) {
				e.printStackTrace();
			}
		}

		return "redirect:/berletek";
	}
	
	@RequestMapping("/torol")
	public String delete(HttpServletResponse response, HttpServletRequest request) {
		System.out.println("Töröljééé");
		deleteMembershipFromCookie(request, response);
		return "redirect:/berletek";
		
	}

	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	private void addBasketToSessionIfExists(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper) {
		Basket basket = gos.newBasket(new User());
		boolean isBasketExists = false;
		Map<String, Integer> products = readProductsBasketToMapFromCookies(request, mapper);
		if (products.size() > 0) {
			loadBasketWithProducts(products, basket);
			isBasketExists = true;
		}
		Map<String, String> memberships = readMembershipsBasketToMapFromCookies(request, mapper);
		if (memberships.size() > 0) {
			try {
				loadBasketWithMembership(memberships, basket);
				isBasketExists = true;
			} catch (FitnessDaoException e) {
				deleteMembershipFromCookie(request, response);
			}
		}
		if (isBasketExists) {
			request.getSession().setAttribute("basket", basket);
		} else {
			deleteMembershipFromCookie(request, response);
			request.getSession().removeAttribute("basket");
		}
	}

	private void deleteMembershipFromCookie(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("memberhipsInBasket");
		for(Cookie c :request.getCookies()) {
			if(c.getName().equals("memberhipsInBasket")) {
				c.setMaxAge(0);
				response.addCookie(c);
			}
		}
	}

	private void loadBasketWithMembership(Map<String, String> memberships, Basket basket) throws FitnessDaoException {
		Membership membership = parseMembership(memberships);
		if (membership != null) {
			basket.addMembership(membership);
			membership.setBasket(basket);
		} else {
			memberships = new HashMap<String, String>();

		}
	}

	private Membership parseMembership(Map<String, String> memberships) throws FitnessDaoException {
		Long membershipId = null;
		Membership membership = null;
		MembershipType membershipType = null;

		for (String s : memberships.keySet()) {
			membershipId = Long.parseLong(s);
		}
		membershipType = gps.getMembershipTypeById(membershipId);
		if (membershipType.getIsIntervally()) {
			Date startDate = parseStringIntoDate(memberships.get(membershipId.toString()));
			membership = gps.newMemberShip(membershipType.getIsIntervally(), membershipType.getDetail(), membershipType.getMaxNumberOfEntries(), startDate,
					new Date(startDate.getTime() + (long) membershipType.getExpireDateInDays() * 1000 * 60 * 60 * 24), membershipType.getPrice());
		}
		membership = gps.newMemberShip(membershipType.getIsIntervally(), membershipType.getDetail(), membershipType.getMaxNumberOfEntries(), null, null, membershipType.getPrice());
		return membership;
	}

	private void loadBasketWithProducts(Map<String, Integer> map, Basket basket) {
		for (String s : map.keySet()) {
			addProductToBasketByProductId(basket, Long.parseLong(s), map.get(s));
		}
	}

	private void addProductToBasketByProductId(Basket basket, long id, Integer quantity) {
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

	private void writeMapToCookies(HttpServletResponse response, ObjectMapper mapper, Map<String, String> map) {

		JsonManager<String> jsonManager = new JsonManager<String>(mapper);
		String json = jsonManager.wrapToJsonString(map);

		CookieManager cookieManager = new CookieManager();
		cookieManager.writeToCookies(response, "memberhipsInBasket", json);
	}

	private Date parseStringIntoDate(String string) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = (Date) formatter.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private boolean isMembershipTypeInterval(long membershipId) {
		MembershipType type = null;
		boolean isInterval = false;
		try {
			type = gps.getMembershipTypeById(membershipId);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}

		if (type.getMaxNumberOfEntries() == 0) {
			isInterval = true;
		}
		return isInterval;
	}

	private Map<String, String> readMembershipsBasketToMapFromCookies(HttpServletRequest request, ObjectMapper mapper) {
		String cookieValue = null;

		CookieManager cookieManager = new CookieManager();
		cookieValue = cookieManager.readFromCookies(request, "memberhipsInBasket");
		
		JsonManager<String> jsonManager = new JsonManager<String>(mapper);
		Map<String, String> map = jsonManager.unwrapFromJsonString(cookieValue);

		return map;
	}

	private Map<String, Integer> readProductsBasketToMapFromCookies(HttpServletRequest request, ObjectMapper mapper) {
		String cookieValue = null;

		CookieManager cookieManager = new CookieManager();
		cookieValue = cookieManager.readFromCookies(request, "productsInBasket");

		JsonManager<Integer> jsonManager = new JsonManager<Integer>(mapper);
		Map<String, Integer> map = jsonManager.unwrapFromJsonString(cookieValue);

		return map;
	}

}
