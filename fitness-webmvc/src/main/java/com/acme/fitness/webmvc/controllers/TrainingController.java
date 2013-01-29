package com.acme.fitness.webmvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.users.GeneralUsersService;

@Controller
@RequestMapping("/edzesek")
public class TrainingController {

	@Autowired
	private GeneralProductsService gps;

	@Autowired
	private GeneralUsersService gus;
	
	@Autowired
	private GeneralOrdersService gos;
	
	@RequestMapping(value = "")
	public String training(HttpServletRequest request) {
		request.setAttribute("trainers", gus.getAllTrainers());
		return "edzesek";
	}
}
