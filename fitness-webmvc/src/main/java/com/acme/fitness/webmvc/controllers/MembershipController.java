package com.acme.fitness.webmvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acme.fitness.domain.exceptions.BasketCheckOutException;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.webmvc.web.ProductsManager;

@Controller
@RequestMapping("/berletek")
public class MembershipController {

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private ProductsManager productsManager;

	@RequestMapping("")
	public String defaultPage(HttpServletResponse response, HttpServletRequest request, Model model) {
		productsManager.addBasketToSessionIfExists(request, response, new ObjectMapper());
		addMembershipTypesToModel(model);
		return "berletek";
	}

	@RequestMapping("/ujberlet")
	public String newMembership(@RequestParam("membershipId") long membershipId, HttpServletResponse response, HttpServletRequest request) {
		productsManager.addNewMembership(membershipId, response, request, new ObjectMapper());
		return "redirect:/berletek";
	}

	@RequestMapping("/megrendel")
	public String checkOut(HttpServletResponse response, HttpServletRequest request, RedirectAttributes redirectAttributes) throws FitnessDaoException {
		try {
			productsManager.checkOutBasket(response, request);
		} catch (StoreQuantityException e) {
			e.printStackTrace();
		} catch (BasketCheckOutException e) {
			return failToCheckOut(redirectAttributes);
		}
		return "redirect:/berletek";
	}

	@RequestMapping("/kosartorles")
	public String deleteBasket(HttpServletRequest request, HttpServletResponse response, Model model) {
		productsManager.deleteBasket(request, response);
		addMembershipTypesToModel(model);
		return "berletek";
	}

	private String failToCheckOut(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Termék rendeléséhez be kell jelentkezni!");
		return "redirect:/berletek";
	}

	private void addMembershipTypesToModel(Model model) {
		model.addAttribute("membershipTypes", gps.getAllMembershipTypes());
	}
}