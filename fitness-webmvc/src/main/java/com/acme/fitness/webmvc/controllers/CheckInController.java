package com.acme.fitness.webmvc.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.webmvc.dto.UserWithMembershipsAndBaskets;

@Controller
@RequestMapping("/beleptetes")
public class CheckInController {
	private static Logger logger = LoggerFactory.getLogger(CheckInController.class);
	private GeneralUsersService gus;
	private GeneralProductsService gps;
	private GeneralOrdersService gos;

	@Autowired
	public CheckInController(GeneralUsersService gus, GeneralProductsService gps, GeneralOrdersService gos) {
		this.gus = gus;
		this.gps = gps;
		this.gos = gos;
	}

	@RequestMapping("")
	public String defaultPage(HttpServletRequest request, HttpServletResponse response) {
		return "beleptetes";
	}

	@RequestMapping(value = "/kereses", method = RequestMethod.POST)
	public String search(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam("searchType") String searchType, @RequestParam("searchInput") String searchInput) {

		if (!searchInput.equals("") && !searchType.equals("")) {
			logger.info("Search with type: " + searchType + " for param: " + searchInput);
			List<User> users = getUsersWithDetails(searchType, searchInput);
			if (users != null) {
				redirectAttributes.addFlashAttribute("searchResult", createAllUserWithMembershipsAndBaskets(users));
			}
		}

		return "redirect:/beleptetes";
	}

	@RequestMapping(value = "/leptet", method = RequestMethod.POST)
	public String checkIn(@RequestParam("membershipIdHiddenField") long membershipId, @RequestParam("userFullNameHiddenField") String fullname,
			RedirectAttributes redirectAttributes) {
		try {
			Membership membership = gps.getMembershipById(membershipId);
			gps.increaseClientEntries(membership);
			redirectAttributes.addFlashAttribute("checkedInUserName", fullname);
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}

		return "redirect:/beleptetes";
	}

	List<UserWithMembershipsAndBaskets> createAllUserWithMembershipsAndBaskets(List<User> users) {
		List<UserWithMembershipsAndBaskets> result = new ArrayList<UserWithMembershipsAndBaskets>();

		for (User u : users) {
			result.add(createUserWithMembershipsAndBaskets(u));
		}

		return result;
	}

	UserWithMembershipsAndBaskets createUserWithMembershipsAndBaskets(User user) {
		UserWithMembershipsAndBaskets uwm = new UserWithMembershipsAndBaskets();

		List<Membership> memberships = gps.getValidMembershipsByUser(user, new Date());
		List<Basket> baskets=getBasketsWithMembershipsAndTrainings(user);
		uwm.setUser(user);
		uwm.setMemberships(memberships);
		uwm.setBaskets(baskets);

		return uwm;
	}

	private List<Basket> getBasketsWithMembershipsAndTrainings(User user) {
		List<Basket> result=gos.getBasketsByUserAndDeliveredStatus(user, false);
		
		for(Basket basket : result){
			basket.setMemberships(gps.getMembershipByBasket(basket));
			basket.setTrainings(gps.getTrainingsByBasket(basket));
		}
		
		return result;
	}

	private List<User> getUsersWithDetails(String searchType, String searchInput) {

		return gus.getUsersByStringParamter(searchType, searchInput);
	}
}
