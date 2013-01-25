package com.acme.fitness.webmvc.web;

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
import org.springframework.stereotype.Service;

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
import com.acme.fitness.webmvc.controllers.WebShopController;

@Service
public class ProductsManager {

	private static final Logger logger = LoggerFactory.getLogger(WebShopController.class);

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralOrdersService gos;

	@Autowired
	private GeneralUsersService gus;

	public void addBasketToSessionIfExists(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper) {
		Basket basket = gos.newBasket(new User());
		boolean isBasketExists = false;
		if (isMembershipExists(request, mapper, basket)) {
			isBasketExists = true;
		}
		if (isProductsExists(request, mapper, basket)) {
			isBasketExists = true;
		}
		if (isBasketExists) {
			request.getSession().setAttribute("basket", basket);
		} else {
			removeFromCookiesAndSession(request, response, "membershipsInBasket");
			removeFromCookiesAndSession(request, response, "productsInBasket");
		}
	}

	public void addNewMembership(long membershipId, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		Map<String, String> map = new HashMap<String, String>();
		if (isMembershipTypeInterval(membershipId)) {
			map.put(Long.toString(membershipId), request.getParameter("datepicker"));
		} else {
			map.put(Long.toString(membershipId), null);
		}
		writeMapToCookie(response, mapper, "membershipsInBasket", map);
	}

	public void addNewOrderItem(long id, int quantity, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		Map<String, Integer> map = readBasketFromCookies(request, mapper, "productsInBasket");
		addOrderItem(id, quantity, map);
		writeMapToCookie(response, mapper, "productsInBasket", map);
	}

	private void addOrderItem(long id, int quantity, Map<String, Integer> map) {
		if (!map.containsKey(Long.toString(id))) {
			map.put(Long.toString(id), quantity);
		} else {
			Integer value = map.get(Long.toString(id));
			value = new Integer(value + quantity);
			map.put(Long.toString(id), value);
		}
	}

	public void checkOutBasket(HttpServletResponse response, HttpServletRequest request) throws StoreQuantityException {
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		if (!getLoggedInUserName().equals("anonymousUser")) {
			try {
				basket.setUser(gus.getUserByUsername(getLoggedInUserName()));
				logger.info("Basket with id: " + basket.getId() + " has confirmed!");
			} catch (FitnessDaoException e) {
				e.printStackTrace();
			}
			gos.checkOutBasket(basket);
			deleteBasket(request, response);
		}
	}

	public void deleteBasket(HttpServletRequest request, HttpServletResponse response) {
		removeFromCookiesAndSession(request, response, "membershipsInBasket");
		removeFromCookiesAndSession(request, response, "productsInBasket");
		request.getSession().removeAttribute("basket");
	}

	private String getLoggedInUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	private boolean isMembershipTypeInterval(long membershipId) {
		boolean isInterval = false;
		try {
			MembershipType type = gps.getMembershipTypeById(membershipId);
			if (type.getMaxNumberOfEntries() == 0) {
				isInterval = true;
			}
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return isInterval;
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

	private void loadBasketWithProducts(Map<String, Integer> map, Basket basket) {
		for (String s : map.keySet()) {
			try {
				addProductToBasketByProductId(basket, Long.parseLong(s), map.get(s));
			} catch (FitnessDaoException e) {
				map.remove(s);
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
		Long membershipTypeId = getMembershipId(memberships);
		MembershipType membershipType = gps.getMembershipTypeById(membershipTypeId);
		Membership membership = gps.newMemberShip(membershipType.getIsIntervally(), membershipType.getDetail(), 0, null, null, membershipType.getPrice());

		if (membershipType.getIsIntervally()) {
			setMembershipWithIntervallySpecificDatas(getStartDate(memberships, membershipType), membership, membershipType);
		} else {
			membership.setMaxNumberOfEntries(membershipType.getMaxNumberOfEntries());
		}
		return membership;
	}

	private Date getStartDate(Map<String, String> memberships, MembershipType membershipType) {
		return parseStringIntoDate(memberships.get(new Long(membershipType.getId()).toString()));
	}

	private void setMembershipWithIntervallySpecificDatas(Date startDate, Membership membership, MembershipType membershipType) {
		membership.setStartDate(startDate);
		membership.setExpireDate(new Date(startDate.getTime() + (long) membershipType.getExpireDateInDays() * 1000 * 60 * 60 * 24));
	}

	private Long getMembershipId(Map<String, String> memberships) {
		Long membershipId = null;
		for (String s : memberships.keySet()) {
			membershipId = Long.parseLong(s);
		}
		return membershipId;
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

	private void addProductToBasketByProductId(Basket basket, long id, Integer quantity) throws FitnessDaoException {
		Product product = null;
		try {
			product = gps.getProductById(id);
			OrderItem oi = gos.newOrderItem(product, quantity);
			gos.addOrderItemToBasket(basket, oi);
		} catch (FitnessDaoException e) {
			logger.info("Product cannot added to Basket with id: " + id);
			throw new FitnessDaoException();
		}
	}

	private boolean isMembershipExists(HttpServletRequest request, ObjectMapper mapper, Basket basket) {
		boolean isBasketExists = false;
		Map<String, String> memberships = readBasketFromCookies(request, mapper, "membershipsInBasket");
		if (memberships.size() > 0) {
			try {
				loadBasketWithMembership(memberships, basket);
				isBasketExists = true;
			} catch (FitnessDaoException e) {
			}
		}
		return isBasketExists;
	}

	private boolean isProductsExists(HttpServletRequest request, ObjectMapper mapper, Basket basket) {
		boolean isBasketExists = false;
		Map<String, Integer> products = readBasketFromCookies(request, mapper, "productsInBasket");
		if (products.size() > 0) {
			loadBasketWithProducts(products, basket);
			isBasketExists = true;
		}
		return isBasketExists;
	}

	private <T> Map<String, T> readBasketFromCookies(HttpServletRequest request, ObjectMapper mapper, String cookieName) {
		String cookieValue = null;

		CookieManager cookieManager = new CookieManager();
		cookieValue = cookieManager.readFromCookies(request, cookieName);

		JsonManager<T> jsonManager = new JsonManager<T>(mapper);
		Map<String, T> map = jsonManager.unwrapFromJsonString(cookieValue);

		return map;
	}

	private <T> void writeMapToCookie(HttpServletResponse response, ObjectMapper mapper, String cookieName, Map<String, T> map) {
		JsonManager<T> jsonManager = new JsonManager<T>(mapper);
		String json = jsonManager.wrapToJsonString(map);

		CookieManager cookieManager = new CookieManager();
		cookieManager.writeToCookies(response, cookieName, json);
	}

}
