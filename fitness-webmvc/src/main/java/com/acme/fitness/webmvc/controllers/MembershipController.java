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
		return "redirect:" + redirectedFrom(request);
	}

	@RequestMapping("/torles/{membershipId}")
	public String deleteMembership(@PathVariable long membershipId, HttpServletRequest request, HttpServletResponse response) {
		bm.removeMembershipFromBasket(request, response);
		return "redirect:" + redirectedFrom(request);
	}

	@RequestMapping(value = "/torles/anonymous/{productId}")
	public String removeAnonymousMembership(@PathVariable long productId, HttpServletRequest request, HttpServletResponse response) {
		bm.removeAnonymousMembership(request, response);
		return "redirect:" + redirectedFrom(request);
	}

	private String redirectedFrom(HttpServletRequest request) {
		String split[] = request.getHeader("referer").split(request.getContextPath());
		return split[1];
	}

	private void addMembershipTypesToModel(Model model) {
		model.addAttribute("membershipTypes", gps.getAllMembershipTypes());
	}
}