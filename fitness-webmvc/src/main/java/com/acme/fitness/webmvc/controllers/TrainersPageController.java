package com.acme.fitness.webmvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.webmvc.basket.BasketManager;

@Controller
@RequestMapping("/edzok")
public class TrainersPageController {
	
	private GeneralProductsService generalProductsService;
	private BasketManager basketManager;
	
	@Autowired
	public TrainersPageController(GeneralProductsService generalProductsService, BasketManager basketManager){
		this.generalProductsService=generalProductsService;
		this.basketManager=basketManager;
	}
	
	@RequestMapping(value="")
	public String init(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("trainingTypes", generalProductsService.getAllTrainingTypes());
		basketManager.addBasketToSessionIfExists(request, response, new ObjectMapper());
		basketManager.isAnonymousBasketIfUserLoggedIn(request, response, new ObjectMapper());
		return "edzok";
	}
}
