package com.acme.fitness.webmvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acme.fitness.domain.exceptions.BasketCheckOutException;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.webmvc.basket.BasketManager;

@Controller
@RequestMapping("/berletek")
public class MembershipController {

	@Autowired
	private GeneralProductsService gps;
	
	@Autowired
	private BasketManager bm;

	@RequestMapping("")
	public String defaultPage(HttpServletResponse response, HttpServletRequest request, Model model) {
		bm.addBasketToSessionIfExists(request, response, new ObjectMapper());
		bm.isAnonymousBasketIfUserLoggedIn(request, response, new ObjectMapper());
		addMembershipTypesToModel(model);
		return "berletek";
	}

	@RequestMapping("/ujberlet")
	public String newMembership(@RequestParam("membershipId") long membershipId, HttpServletResponse response, HttpServletRequest request) {
		bm.addNewMembership(membershipId, response, request, new ObjectMapper());
		return "redirect:/berletek";
	}

	@RequestMapping("/megrendel")
	public String checkOut(HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes) throws FitnessDaoException {
		try {
			bm.checkOutBasket(response, request);
		} catch (StoreQuantityException e) {
			e.printStackTrace();
		} catch (BasketCheckOutException e) {
			return failToCheckOut(redirectAttributes);
		}
		return "redirect:/berletek";
	}

	@RequestMapping("/kosartorles")
	public String deleteBasket(HttpServletRequest request, HttpServletResponse response, Model model) {
		bm.deleteBasket(request, response);
		addMembershipTypesToModel(model);
		return "berletek";
	}
	
	@RequestMapping("/torles/{membershipId}")
	public String deleteMembership(@PathVariable long membershipId, HttpServletRequest request, HttpServletResponse response) {
		bm.removeMembershipFromBasket(request, response);
		return "redirect:/berletek";
	}
	
	@RequestMapping(value = "/torles/anonymous/{productId}")
	public String removeAnonymousProduct(@PathVariable long productId, HttpServletRequest request, HttpServletResponse response) {
		bm.removeAnonymousMembership(request, response);
		return "redirect:/aruhaz/";
	}
	
	@RequestMapping("/anonymKosar/hozzaad")
	public String addAnonymousBasketToUser(HttpServletRequest request, HttpServletResponse response) {
		String redirectTo = "";
		bm.addAnonymousMembershipsBasketLoggedInUser(response, request, new ObjectMapper());
		if(bm.isAnonymousBasketContainsProducts(request, response, new ObjectMapper())) {
			redirectTo = "redirect:/aruhaz/anonymKosar/hozzaad";
		} else if(bm.isAnonymousBasketContainsTrainings(request, response, new ObjectMapper())) {
			redirectTo = "redirect:/edzesek/anonymKosar/hozzaad";
		}else {
			request.getSession().removeAttribute("anonymousBasket");
			redirectTo = "redirect:/berletek";
		}
		return redirectTo;
	}

	private String failToCheckOut(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Termék rendeléséhez be kell jelentkezni!");
		return "redirect:/berletek";
	}

	private void addMembershipTypesToModel(Model model) {
		model.addAttribute("membershipTypes", gps.getAllMembershipTypes());
	}
}